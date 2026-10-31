package com.practice.phonepe.questions.bookmyshow.service;

import com.practice.phonepe.questions.bookmyshow.entities.Movie;
import com.practice.phonepe.questions.bookmyshow.exception.EntityNotFoundException;
import com.practice.phonepe.questions.bookmyshow.repository.MovieRepository;

import java.util.List;

/** Admin-facing CRUD for the movie catalog. */
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(String title, String language, String genre, int durationMinutes) {
        Movie movie = new Movie(title, language, genre, durationMinutes);
        movieRepository.save(movie);
        return movie;
    }

    public Movie getMovie(String movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found: " + movieId));
    }

    public List<Movie> listMovies() {
        return movieRepository.findAll();
    }

    public void removeMovie(String movieId) {
        movieRepository.deleteById(movieId);
    }
}
