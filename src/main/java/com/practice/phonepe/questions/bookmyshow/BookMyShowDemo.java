package com.practice.phonepe.questions.bookmyshow;

import com.practice.phonepe.questions.bookmyshow.entities.Booking;
import com.practice.phonepe.questions.bookmyshow.entities.Movie;
import com.practice.phonepe.questions.bookmyshow.entities.Screen;
import com.practice.phonepe.questions.bookmyshow.entities.Seat;
import com.practice.phonepe.questions.bookmyshow.entities.SeatType;
import com.practice.phonepe.questions.bookmyshow.entities.Show;
import com.practice.phonepe.questions.bookmyshow.entities.Theatre;
import com.practice.phonepe.questions.bookmyshow.exception.SeatUnavailableException;
import com.practice.phonepe.questions.bookmyshow.lock.SeatLockManager;
import com.practice.phonepe.questions.bookmyshow.payment.PaymentMethod;
import com.practice.phonepe.questions.bookmyshow.payment.PaymentStrategyFactory;
import com.practice.phonepe.questions.bookmyshow.pricing.DefaultPricingStrategy;
import com.practice.phonepe.questions.bookmyshow.repository.BookingRepository;
import com.practice.phonepe.questions.bookmyshow.repository.InMemoryBookingRepository;
import com.practice.phonepe.questions.bookmyshow.repository.InMemoryMovieRepository;
import com.practice.phonepe.questions.bookmyshow.repository.InMemoryShowRepository;
import com.practice.phonepe.questions.bookmyshow.repository.InMemoryTheatreRepository;
import com.practice.phonepe.questions.bookmyshow.repository.MovieRepository;
import com.practice.phonepe.questions.bookmyshow.repository.ShowRepository;
import com.practice.phonepe.questions.bookmyshow.repository.TheatreRepository;
import com.practice.phonepe.questions.bookmyshow.service.BookingService;
import com.practice.phonepe.questions.bookmyshow.service.MovieService;
import com.practice.phonepe.questions.bookmyshow.service.ShowService;
import com.practice.phonepe.questions.bookmyshow.service.TheatreService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BookMyShowDemo {

    public static void main(String[] args) throws InterruptedException {
        // --- wiring ---
        MovieRepository movieRepository = new InMemoryMovieRepository();
        TheatreRepository theatreRepository = new InMemoryTheatreRepository();
        ShowRepository showRepository = new InMemoryShowRepository(theatreRepository);
        BookingRepository bookingRepository = new InMemoryBookingRepository();

        MovieService movieService = new MovieService(movieRepository);
        TheatreService theatreService = new TheatreService(theatreRepository);
        ShowService showService = new ShowService(showRepository, theatreService, new DefaultPricingStrategy());
        SeatLockManager seatLockManager = new SeatLockManager(TimeUnit.SECONDS.toMillis(30));
        PaymentStrategyFactory paymentStrategyFactory = new PaymentStrategyFactory();
        BookingService bookingService = new BookingService(bookingRepository, showService, seatLockManager, paymentStrategyFactory);

        // --- admin sets up catalog, theatre and a show ---
        Movie movie = movieService.addMovie("Inception", "English", "Sci-Fi", 148);
        Theatre theatre = theatreService.addTheatre("PVR Forum", "Bengaluru");

        Map<String, SeatType> seatLayout = new LinkedHashMap<>();
        seatLayout.put("A", SeatType.NORMAL);
        seatLayout.put("B", SeatType.PREMIUM);
        seatLayout.put("C", SeatType.RECLINER);
        Screen screen = theatreService.addScreen(theatre.getId(), "Screen 1", seatLayout, 10);

        Show show = showService.createShow(movie.getId(), theatre.getId(), screen.getId(),
                LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(5));

        System.out.println("Show created: " + movie + " at " + theatre.getName() + " (" + show.getId() + ")");

        // --- a user browses shows and books a couple of seats end-to-end ---
        List<Show> shows = showService.searchShows(movie.getId(), "Bengaluru");
        System.out.println("Shows found in Bengaluru: " + shows.size());

        Seat targetSeat = screen.getSeats().get(0);
        Booking booking = bookingService.initiateBooking("user-1", show.getId(), Collections.singletonList(targetSeat.getId()));
        System.out.printf("Booking %s created, amount due: %.2f%n", booking.getId(), booking.getTotalAmount());

        Booking confirmed = bookingService.confirmBooking(booking.getId(), PaymentMethod.CARD);
        System.out.println("Booking status after payment: " + confirmed.getStatus());

        // --- concurrency demo: many users race for the same seat ---
        Seat contestedSeat = screen.getSeats().get(1);
        int contenders = 10;
        ExecutorService executor = Executors.newFixedThreadPool(contenders);
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch doneGate = new CountDownLatch(contenders);
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger rejectedCount = new AtomicInteger();

        for (int i = 0; i < contenders; i++) {
            String userId = "contender-" + i;
            executor.submit(() -> {
                try {
                    startGate.await();
                    bookingService.initiateBooking(userId, show.getId(), Collections.singletonList(contestedSeat.getId()));
                    successCount.incrementAndGet();
                } catch (SeatUnavailableException e) {
                    rejectedCount.incrementAndGet();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneGate.countDown();
                }
            });
        }

        startGate.countDown();
        doneGate.await();
        executor.shutdown();

        System.out.printf("Seat %s contested by %d users -> %d locked it, %d were rejected%n",
                contestedSeat.getLabel(), contenders, successCount.get(), rejectedCount.get());

        seatLockManager.shutdown();
    }
}
