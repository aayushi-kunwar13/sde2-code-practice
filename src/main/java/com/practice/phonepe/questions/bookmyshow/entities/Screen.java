package com.practice.phonepe.questions.bookmyshow.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Screen {

    private final String id;
    private final String name;
    private final List<Seat> seats = new ArrayList<>();

    public Screen(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
    }
}
