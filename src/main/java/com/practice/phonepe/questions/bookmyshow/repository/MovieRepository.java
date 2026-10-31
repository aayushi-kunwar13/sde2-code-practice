package com.practice.phonepe.questions.bookmyshow.repository;

import com.practice.phonepe.questions.bookmyshow.entities.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    void save(Movie movie);
    Optional<Movie> findById(String movieId);
    List<Movie> findAll();
    void deleteById(String movieId);
}
