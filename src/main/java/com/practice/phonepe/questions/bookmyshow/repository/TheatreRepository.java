package com.practice.phonepe.questions.bookmyshow.repository;

import com.practice.phonepe.questions.bookmyshow.entities.Theatre;

import java.util.List;
import java.util.Optional;

public interface TheatreRepository {
    void save(Theatre theatre);
    Optional<Theatre> findById(String theatreId);
    List<Theatre> findByCity(String city);
    List<Theatre> findAll();
}
