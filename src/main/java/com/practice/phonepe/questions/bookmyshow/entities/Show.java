package com.practice.phonepe.questions.bookmyshow.entities;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class Show {

    private final String id;
    private final String movieId;
    private final String theatreId;
    private final String screenId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Map<String, ShowSeat> showSeatsBySeatId = new LinkedHashMap<>();

    public Show(String movieId, String theatreId, String screenId, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = UUID.randomUUID().toString();
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.screenId = screenId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTheatreId() {
        return theatreId;
    }

    public String getScreenId() {
        return screenId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Map<String, ShowSeat> getShowSeatsBySeatId() {
        return showSeatsBySeatId;
    }

    public void addShowSeat(ShowSeat showSeat) {
        showSeatsBySeatId.put(showSeat.getSeat().getId(), showSeat);
    }
}
