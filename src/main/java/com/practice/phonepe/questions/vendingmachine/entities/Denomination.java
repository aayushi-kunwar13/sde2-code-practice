package com.practice.phonepe.questions.vendingmachine.entities;

/** Covers coins and notes alike - the machine only needs each one's value, not its physical form. */
public enum Denomination {
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50),
    HUNDRED(100);

    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
