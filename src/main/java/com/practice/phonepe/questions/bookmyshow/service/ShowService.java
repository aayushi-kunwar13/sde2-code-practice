package com.practice.phonepe.questions.bookmyshow.service;

import com.practice.phonepe.questions.bookmyshow.entities.Screen;
import com.practice.phonepe.questions.bookmyshow.entities.Show;
import com.practice.phonepe.questions.bookmyshow.entities.ShowSeat;
import com.practice.phonepe.questions.bookmyshow.exception.EntityNotFoundException;
import com.practice.phonepe.questions.bookmyshow.pricing.PricingStrategy;
import com.practice.phonepe.questions.bookmyshow.repository.ShowRepository;

import java.time.LocalDateTime;
import java.util.List;
 //https://claude.ai/code/artifact/e675ffac-9443-4147-9f6d-c0db6ad5b6ea?via=auto_preview
/** Admin-facing show scheduling plus the show search users query when browsing. */
public class ShowService {

    private final ShowRepository showRepository;
    private final TheatreService theatreService;
    private final PricingStrategy pricingStrategy;

    public ShowService(ShowRepository showRepository, TheatreService theatreService, PricingStrategy pricingStrategy) {
        this.showRepository = showRepository;
        this.theatreService = theatreService;
        this.pricingStrategy = pricingStrategy;
    }

    public Show createShow(String movieId, String theatreId, String screenId, LocalDateTime startTime, LocalDateTime endTime) {
        Screen screen = theatreService.getScreen(theatreId, screenId);
        Show show = new Show(movieId, theatreId, screenId, startTime, endTime);
        screen.getSeats().forEach(seat -> {
            double price = pricingStrategy.getPrice(show, seat.getSeatType());
            show.addShowSeat(new ShowSeat(show.getId(), seat, price));
        });
        showRepository.save(show);
        return show;
    }

    public Show getShow(String showId) {
        return showRepository.findById(showId)
                .orElseThrow(() -> new EntityNotFoundException("Show not found: " + showId));
    }

    public List<Show> searchShows(String movieId, String city) {
        return showRepository.findByMovieAndCity(movieId, city);
    }

    public void cancelShow(String showId) {
        showRepository.deleteById(showId);
    }
}
