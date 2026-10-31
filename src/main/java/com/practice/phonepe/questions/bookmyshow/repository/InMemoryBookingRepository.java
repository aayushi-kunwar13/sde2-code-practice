package com.practice.phonepe.questions.bookmyshow.repository;

import com.practice.phonepe.questions.bookmyshow.entities.Booking;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryBookingRepository implements BookingRepository {

    private final Map<String, Booking> bookingsById = new LinkedHashMap<>();

    @Override
    public void save(Booking booking) {
        bookingsById.put(booking.getId(), booking);
    }

    @Override
    public Optional<Booking> findById(String bookingId) {
        return Optional.ofNullable(bookingsById.get(bookingId));
    }

    @Override
    public List<Booking> findByUser(String userId) {
        return bookingsById.values().stream()
                .filter(booking -> booking.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
