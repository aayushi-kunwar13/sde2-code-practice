package com.practice.phonepe.questions.bookmyshow.payment;

public class UpiPaymentStrategy implements PaymentStrategy {

    @Override
    public boolean pay(String userId, double amount) {
        System.out.printf("Charging UPI for user %s: amount=%.2f%n", userId, amount);
        return true;
    }
}
