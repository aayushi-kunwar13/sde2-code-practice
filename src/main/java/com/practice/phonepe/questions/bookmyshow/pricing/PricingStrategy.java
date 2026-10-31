package com.practice.phonepe.questions.bookmyshow.pricing;

import com.practice.phonepe.questions.bookmyshow.entities.SeatType;
import com.practice.phonepe.questions.bookmyshow.entities.Show;

public interface PricingStrategy {
    double getPrice(Show show, SeatType seatType);
}
