package com.practice.phonepe.questions.parkinglot.strategy.fee;

import com.practice.phonepe.questions.parkinglot.entities.ParkingTicket;

public interface FeeStrategy {

    double calculateFee(ParkingTicket parkingTicket);
}
