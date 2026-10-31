package com.practice.phonepe.questions.vendingmachine.state;

import com.practice.phonepe.questions.vendingmachine.VendingMachine;
import com.practice.phonepe.questions.vendingmachine.entities.Denomination;
import com.practice.phonepe.questions.vendingmachine.entities.DispenseResult;
import com.practice.phonepe.questions.vendingmachine.exception.ChangeNotAvailableException;

import java.util.List;

/** Momentary state: payment has cleared, the machine is committed to releasing the product. */
public class DispensingState implements VendingMachineState {

    @Override
    public void selectProduct(VendingMachine machine, String productCode) {
        throw new IllegalStateException("Machine is busy dispensing");
    }

    @Override
    public void insertMoney(VendingMachine machine, int amount) {
        throw new IllegalStateException("Machine is busy dispensing");
    }

    @Override
    public DispenseResult dispense(VendingMachine machine) {
        String productCode = machine.getSelectedProductCode();
        int price = machine.getInventory().getProduct(productCode).getPrice();
        int changeDue = machine.getAmountPaid() - price;

        List<Denomination> change;
        try {
            change = machine.settleChange(changeDue);
        } catch (ChangeNotAvailableException e) {
            // Nothing has been committed yet - fall back to ProductSelectedState instead of
            // stranding the machine here, where every action would otherwise be illegal.
            machine.setState(new ProductSelectedState());
            throw e;
        }

        machine.getInventory().decrement(productCode);
        machine.resetPayment();
        machine.setState(new IdleState());
        return new DispenseResult(productCode, changeDue, change);
    }

    @Override
    public void cancel(VendingMachine machine) {
        throw new IllegalStateException("Cannot cancel while dispensing");
    }
}
