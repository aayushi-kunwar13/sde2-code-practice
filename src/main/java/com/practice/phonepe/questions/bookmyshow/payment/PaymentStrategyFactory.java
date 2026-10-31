package com.practice.phonepe.questions.bookmyshow.payment;

import java.util.EnumMap;
import java.util.Map;

/**
 * Resolves the {@link PaymentStrategy} for a {@link PaymentMethod} so callers depend only on the
 * method the user picked, never on a concrete strategy class.
 */
public class PaymentStrategyFactory {

    private final Map<PaymentMethod, PaymentStrategy> strategiesByMethod = new EnumMap<>(PaymentMethod.class);

    public PaymentStrategyFactory() {
        strategiesByMethod.put(PaymentMethod.CARD, new CardPaymentStrategy());
        strategiesByMethod.put(PaymentMethod.UPI, new UpiPaymentStrategy());
    }

    public PaymentStrategy getStrategy(PaymentMethod method) {
        PaymentStrategy strategy = strategiesByMethod.get(method);
        if (strategy == null) {
            throw new IllegalArgumentException("No payment strategy registered for method: " + method);
        }
        return strategy;
    }

    public void registerStrategy(PaymentMethod method, PaymentStrategy strategy) {
        strategiesByMethod.put(method, strategy);
    }
}
