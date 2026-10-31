package com.practice.phonepe.questions.vendingmachine.state;

import com.practice.phonepe.questions.vendingmachine.VendingMachine;
import com.practice.phonepe.questions.vendingmachine.entities.DispenseResult;
import com.practice.phonepe.questions.vendingmachine.exception.OutOfStockException;
import com.practice.phonepe.questions.vendingmachine.exception.ProductNotFoundException;

/** Waiting for a selection. Nothing has been paid for yet. */
public class IdleState implements VendingMachineState {

    @Override
    public void selectProduct(VendingMachine machine, String productCode) {
        if (!machine.getInventory().exists(productCode)) {
            throw new ProductNotFoundException("No such product: " + productCode);
        }
        if (!machine.getInventory().hasStock(productCode)) {
            throw new OutOfStockException("Out of stock: " + productCode);
        }
        machine.setSelectedProductCode(productCode);
        machine.setState(new ProductSelectedState());
    }

    @Override
    public void insertMoney(VendingMachine machine, int amount) {
        throw new IllegalStateException("Select a product before inserting money");
    }

    @Override
    public DispenseResult dispense(VendingMachine machine) {
        throw new IllegalStateException("No product selected");
    }

    @Override
    public void cancel(VendingMachine machine) {
        // nothing in progress to cancel
    }
}
