package com.practice.phonepe.questions.bookmyshow.repository;

import com.practice.phonepe.questions.bookmyshow.entities.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    void save(Booking booking);
    Optional<Booking> findById(String bookingId);
    List<Booking> findByUser(String userId);
}
