package com.practice.phonepe.questions.bookmyshow.service;

import com.practice.phonepe.questions.bookmyshow.entities.Screen;
import com.practice.phonepe.questions.bookmyshow.entities.Seat;
import com.practice.phonepe.questions.bookmyshow.entities.SeatType;
import com.practice.phonepe.questions.bookmyshow.entities.Theatre;
import com.practice.phonepe.questions.bookmyshow.exception.EntityNotFoundException;
import com.practice.phonepe.questions.bookmyshow.repository.TheatreRepository;

import java.util.List;
import java.util.Map;

/** Admin-facing CRUD for theatres, screens and their seating arrangements. */
public class TheatreService {

    private final TheatreRepository theatreRepository;

    public TheatreService(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    public Theatre addTheatre(String name, String city) {
        Theatre theatre = new Theatre(name, city);
        theatreRepository.save(theatre);
        return theatre;
    }

    /**
     * Adds a screen to a theatre and lays out its seats.
     *
     * @param seatsPerRowByType e.g. {"A": NORMAL, "B": PREMIUM} with rowSize seats generated per row
     */
    public Screen addScreen(String theatreId, String screenName, Map<String, SeatType> seatsPerRowByType, int rowSize) {
        Theatre theatre = getTheatre(theatreId);
        Screen screen = new Screen(screenName);
        seatsPerRowByType.forEach((row, seatType) -> {
            for (int seatNumber = 1; seatNumber <= rowSize; seatNumber++) {
                screen.addSeat(new Seat(row, seatNumber, seatType));
            }
        });
        theatre.addScreen(screen);
        return screen;
    }

    public Theatre getTheatre(String theatreId) {
        return theatreRepository.findById(theatreId)
                .orElseThrow(() -> new EntityNotFoundException("Theatre not found: " + theatreId));
    }

    public List<Theatre> findTheatresInCity(String city) {
        return theatreRepository.findByCity(city);
    }

    public Screen getScreen(String theatreId, String screenId) {
        return getTheatre(theatreId).getScreens().stream()
                .filter(screen -> screen.getId().equals(screenId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Screen not found: " + screenId));
    }
}
