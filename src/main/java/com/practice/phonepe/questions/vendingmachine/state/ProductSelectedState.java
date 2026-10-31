package com.practice.phonepe.questions.vendingmachine.state;

import com.practice.phonepe.questions.vendingmachine.VendingMachine;
import com.practice.phonepe.questions.vendingmachine.entities.DispenseResult;
import com.practice.phonepe.questions.vendingmachine.exception.InsufficientMoneyException;

/**
 * A product is chosen and payment is accumulating. Coins/notes arrive one {@link #insertMoney}
 * call at a time.
 */
public class ProductSelectedState implements VendingMachineState {

    @Override
    public void selectProduct(VendingMachine machine, String productCode) {
        throw new IllegalStateException(
                "Already selected " + machine.getSelectedProductCode() + " - cancel first to pick another");
    }

    @Override
    public void insertMoney(VendingMachine machine, int amount) {
        machine.addAmountPaid(amount);
    }

    @Override
    public DispenseResult dispense(VendingMachine machine) {
        int price = machine.getInventory().getProduct(machine.getSelectedProductCode()).getPrice();
        if (machine.getAmountPaid() < price) {
            throw new InsufficientMoneyException(
                    "Need " + (price - machine.getAmountPaid()) + " more to dispense " + machine.getSelectedProductCode());
        }
        machine.setState(new DispensingState());
        return machine.dispense();
    }

    @Override
    public void cancel(VendingMachine machine) {
        machine.refundCurrentPayment();
        machine.resetPayment();
        machine.setState(new IdleState());
    }
}
