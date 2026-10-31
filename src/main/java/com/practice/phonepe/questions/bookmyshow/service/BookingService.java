package com.practice.phonepe.questions.bookmyshow.service;

import com.practice.phonepe.questions.bookmyshow.entities.Booking;
import com.practice.phonepe.questions.bookmyshow.entities.BookingStatus;
import com.practice.phonepe.questions.bookmyshow.entities.Payment;
import com.practice.phonepe.questions.bookmyshow.entities.PaymentStatus;
import com.practice.phonepe.questions.bookmyshow.entities.Show;
import com.practice.phonepe.questions.bookmyshow.entities.ShowSeat;
import com.practice.phonepe.questions.bookmyshow.exception.EntityNotFoundException;
import com.practice.phonepe.questions.bookmyshow.exception.InvalidSeatLockException;
import com.practice.phonepe.questions.bookmyshow.lock.SeatLockManager;
import com.practice.phonepe.questions.bookmyshow.payment.PaymentMethod;
import com.practice.phonepe.questions.bookmyshow.payment.PaymentStrategy;
import com.practice.phonepe.questions.bookmyshow.payment.PaymentStrategyFactory;
import com.practice.phonepe.questions.bookmyshow.repository.BookingRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Orchestrates the booking flow: hold seats -> collect payment -> confirm or roll back.
 * Seat-level concurrency is delegated entirely to {@link SeatLockManager}; this class just
 * sequences the steps and keeps {@link Booking} state consistent with seat state.
 */
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowService showService;
    private final SeatLockManager seatLockManager;
    private final PaymentStrategyFactory paymentStrategyFactory;

    public BookingService(BookingRepository bookingRepository, ShowService showService, SeatLockManager seatLockManager,
                           PaymentStrategyFactory paymentStrategyFactory) {
        this.bookingRepository = bookingRepository;
        this.showService = showService;
        this.seatLockManager = seatLockManager;
        this.paymentStrategyFactory = paymentStrategyFactory;
    }

    /** Step 1: hold the requested seats for this user and create a pending booking. */
    public Booking initiateBooking(String userId, String showId, List<String> seatIds) {
        Show show = showService.getShow(showId);
        List<ShowSeat> requestedSeats = seatIds.stream()
                .map(seatId -> {
                    ShowSeat showSeat = show.getShowSeatsBySeatId().get(seatId);
                    if (showSeat == null) {
                        throw new EntityNotFoundException("Seat not found on show: " + seatId);
                    }
                    return showSeat;
                })
                .collect(Collectors.toList());

        List<ShowSeat> lockedSeats = seatLockManager.lockSeats(userId, requestedSeats);
        double totalAmount = lockedSeats.stream().mapToDouble(ShowSeat::getPrice).sum();

        Booking booking = new Booking(userId, showId, seatIds, totalAmount);
        bookingRepository.save(booking);
        return booking;
    }

    /** Step 2: charge the user and, on success, permanently claim the seats. */
    public Booking confirmBooking(String bookingId, PaymentMethod paymentMethod) {
        Booking booking = getBooking(bookingId);
        if (booking.getStatus() != BookingStatus.CREATED) {
            throw new InvalidSeatLockException("Booking " + bookingId + " is not awaiting payment");
        }

        Show show = showService.getShow(booking.getShowId());
        List<ShowSeat> seats = booking.getSeatIds().stream()
                .map(seatId -> show.getShowSeatsBySeatId().get(seatId))
                .collect(Collectors.toList());

        PaymentStrategy paymentStrategy = paymentStrategyFactory.getStrategy(paymentMethod);
        boolean paymentSucceeded = paymentStrategy.pay(booking.getUserId(), booking.getTotalAmount());
        if (!paymentSucceeded) {
            seatLockManager.releaseSeats(booking.getUserId(), seats);
            booking.setStatus(BookingStatus.CANCELLED);
            booking.setPayment(new Payment(bookingId, booking.getTotalAmount(), paymentMethod.name(), PaymentStatus.FAILED));
            bookingRepository.save(booking);
            return booking;
        }

        boolean allConfirmed = seats.stream()
                .allMatch(seat -> seat.confirmBooking(booking.getUserId(), System.currentTimeMillis()));
        if (!allConfirmed) {
            // Hold expired before payment finished; refund path would go here in a real system.
            seatLockManager.releaseSeats(booking.getUserId(), seats);
            booking.setStatus(BookingStatus.EXPIRED);
            bookingRepository.save(booking);
            throw new InvalidSeatLockException("Seat lock expired before payment could be confirmed for booking " + bookingId);
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setPayment(new Payment(bookingId, booking.getTotalAmount(), paymentMethod.name(), PaymentStatus.SUCCESS));
        bookingRepository.save(booking);
        return booking;
    }

    public void cancelBooking(String bookingId) {
        Booking booking = getBooking(bookingId);
        Show show = showService.getShow(booking.getShowId());
        List<ShowSeat> seats = booking.getSeatIds().stream()
                .map(seatId -> show.getShowSeatsBySeatId().get(seatId))
                .collect(Collectors.toList());
        seatLockManager.releaseSeats(booking.getUserId(), seats);
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    public Booking getBooking(String bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found: " + bookingId));
    }
}
