package com.practice.phonepe.questions.bookmyshow.payment;

public class CardPaymentStrategy implements PaymentStrategy {

    @Override
    public boolean pay(String userId, double amount) {
        System.out.printf("Charging card for user %s: amount=%.2f%n", userId, amount);
        return true;
    }
}
