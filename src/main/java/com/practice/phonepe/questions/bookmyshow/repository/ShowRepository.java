package com.practice.phonepe.questions.bookmyshow.repository;

import com.practice.phonepe.questions.bookmyshow.entities.Show;

import java.util.List;
import java.util.Optional;

public interface ShowRepository {
    void save(Show show);
    Optional<Show> findById(String showId);
    List<Show> findByMovieAndCity(String movieId, String city);
    void deleteById(String showId);
}
