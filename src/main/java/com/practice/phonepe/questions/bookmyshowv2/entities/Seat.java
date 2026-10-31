package com.practice.phonepe.questions.bookmyshowv2.entities;

public class Seat {
    private final String id;
    private final int row;
    private final int seatNumber;
    private final SeatType seatType;

    public Seat(String id, int row, int seatNumber, SeatType seatType) {
        this.id = id;
        this.row = row;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }

    public String getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }
}
