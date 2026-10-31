package com.practice.phonepe.questions.vendingmachine.entities;

import java.util.List;

public class DispenseResult {

    private final String productCode;
    private final int changeAmount;
    private final List<Denomination> changeBreakdown;

    public DispenseResult(String productCode, int changeAmount, List<Denomination> changeBreakdown) {
        this.productCode = productCode;
        this.changeAmount = changeAmount;
        this.changeBreakdown = changeBreakdown;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getChangeAmount() {
        return changeAmount;
    }

    public List<Denomination> getChangeBreakdown() {
        return changeBreakdown;
    }
}
