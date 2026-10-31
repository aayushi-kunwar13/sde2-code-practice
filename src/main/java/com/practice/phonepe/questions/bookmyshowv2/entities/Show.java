package com.practice.phonepe.questions.bookmyshowv2.entities;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class Show {
    private final String showID;
    private final String movieId;
    private final String theatreId;
    private final String screenId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private Map<String , ShowSeat> showSeatsBySeatId = new LinkedHashMap<>();

    public Show(String showID, String movieId, String theatreId, String screenId, LocalDateTime startTime, LocalDateTime endTime) {
        this.showID = showID;
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.screenId = screenId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getShowID() {
        return showID;
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
        showSeatsBySeatId.put(showSeat.getSeat().getId() , showSeat);
    }


}
