package com.practice.phonepe.questions.bookmyshow.pricing;

import com.practice.phonepe.questions.bookmyshow.entities.SeatType;
import com.practice.phonepe.questions.bookmyshow.entities.Show;

import java.util.EnumMap;
import java.util.Map;

public class DefaultPricingStrategy implements PricingStrategy {

    private final Map<SeatType, Double> basePriceBySeatType = new EnumMap<>(SeatType.class);

    public DefaultPricingStrategy() {
        basePriceBySeatType.put(SeatType.NORMAL, 150.0);
        basePriceBySeatType.put(SeatType.PREMIUM, 250.0);
        basePriceBySeatType.put(SeatType.RECLINER, 400.0);
    }

    @Override
    public double getPrice(Show show, SeatType seatType) {
        return basePriceBySeatType.getOrDefault(seatType, basePriceBySeatType.get(SeatType.NORMAL));
    }
}
