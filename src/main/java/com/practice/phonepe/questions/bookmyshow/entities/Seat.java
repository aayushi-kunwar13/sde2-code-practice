package com.practice.phonepe.questions.bookmyshow.entities;

import java.util.UUID;

public class Seat {

    private final String id;
    private final String row;
    private final int seatNumber;
    private final SeatType seatType;

    public Seat(String row, int seatNumber, SeatType seatType) {
        this.id = UUID.randomUUID().toString();
        this.row = row;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }

    public String getId() {
        return id;
    }

    public String getRow() {
        return row;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public String getLabel() {
        return row + seatNumber;
    }
}
