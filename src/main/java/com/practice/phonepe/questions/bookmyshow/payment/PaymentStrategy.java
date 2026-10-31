package com.practice.phonepe.questions.bookmyshow.payment;

public interface PaymentStrategy {
    boolean pay(String userId, double amount);
}
