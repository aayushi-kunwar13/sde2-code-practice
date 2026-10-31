package com.practice.phonepe.questions.bookmyshow.entities;

import java.util.UUID;

public class Movie {

    private final String id;
    private String title;
    private String language;
    private String genre;
    private int durationMinutes;

    public Movie(String title, String language, String genre, int durationMinutes) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.language = language;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    @Override
    public String toString() {
        return title + " (" + language + ", " + genre + ")";
    }
}
