package com.practice.phonepe.questions.vendingmachine.state;

import com.practice.phonepe.questions.vendingmachine.VendingMachine;
import com.practice.phonepe.questions.vendingmachine.entities.DispenseResult;

public interface VendingMachineState {
    void selectProduct(VendingMachine machine, String productCode);
    void insertMoney(VendingMachine machine, int amount);
    DispenseResult dispense(VendingMachine machine);
    void cancel(VendingMachine machine);
}
