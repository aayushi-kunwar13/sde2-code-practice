package com.practice.phonepe.questions.bookmyshow.repository;

import com.practice.phonepe.questions.bookmyshow.entities.Show;
import com.practice.phonepe.questions.bookmyshow.entities.Theatre;
import com.practice.phonepe.questions.bookmyshow.repository.TheatreRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryShowRepository implements ShowRepository {

    private final Map<String, Show> showsById = new LinkedHashMap<>();
    private final TheatreRepository theatreRepository;

    public InMemoryShowRepository(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    @Override
    public void save(Show show) {
        showsById.put(show.getId(), show);
    }

    @Override
    public Optional<Show> findById(String showId) {
        return Optional.ofNullable(showsById.get(showId));
    }

    @Override
    public List<Show> findByMovieAndCity(String movieId, String city) {
        return showsById.values().stream()
                .filter(show -> show.getMovieId().equals(movieId))
                .filter(show -> theatreRepository.findById(show.getTheatreId())
                        .map(Theatre::getCity)
                        .map(theatreCity -> theatreCity.equalsIgnoreCase(city))
                        .orElse(false))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String showId) {
        showsById.remove(showId);
    }
}
