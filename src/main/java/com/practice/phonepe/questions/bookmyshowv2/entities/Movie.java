package com.practice.phonepe.questions.bookmyshowv2.entities;

public class Movie {

    private final String movieID;
    private final String title;
    private final String description;

    public Movie(String movieID, String title, String description) {
        this.movieID = movieID;
        this.title = title;
        this.description = description;
    }

    public String getMovieID() {
        return movieID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
