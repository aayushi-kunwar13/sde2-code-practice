package com.practice.phonepe.questions.vendingmachine;

import com.practice.phonepe.questions.vendingmachine.entities.Denomination;
import com.practice.phonepe.questions.vendingmachine.entities.DispenseResult;
import com.practice.phonepe.questions.vendingmachine.exception.InsufficientMoneyException;
import com.practice.phonepe.questions.vendingmachine.exception.OutOfStockException;
import com.practice.phonepe.questions.vendingmachine.service.AdminService;

public class VendingMachineDemo {

    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine();
        AdminService admin = new AdminService(machine);

        // admin stocks products and seeds the change float
        admin.restock("COKE", "Coke 500ml", 25, 1);
        admin.restock("CHIPS", "Lays Chips", 20, 0);
        admin.loadChangeFloat(Denomination.FIVE, 10);
        admin.loadChangeFloat(Denomination.TEN, 10);

        // cash purchase, paid in three coins, change returned in denominations
        machine.selectProduct("COKE");
        try {
            machine.dispense();
        } catch (InsufficientMoneyException e) {
            System.out.println("Expected failure: " + e.getMessage());
        }
        machine.insertCoin(Denomination.TEN);
        machine.insertCoin(Denomination.TEN);
        machine.insertCoin(Denomination.TEN);
        DispenseResult result = machine.dispense();
        System.out.printf("Dispensed %s, change %d = %s%n",
                result.getProductCode(), result.getChangeAmount(), result.getChangeBreakdown());

        // depleted item, then an item that was never stocked with any quantity
        try {
            machine.selectProduct("COKE");
        } catch (OutOfStockException e) {
            System.out.println("Expected failure: " + e.getMessage());
        }

        // restock, then pay exact change - no coins owed back
        admin.restock("CHIPS", "Lays Chips", 20, 2);
        machine.selectProduct("CHIPS");
        machine.insertCoin(Denomination.TWENTY);
        DispenseResult exactResult = machine.dispense();
        System.out.printf("Dispensed %s, change %d%n", exactResult.getProductCode(), exactResult.getChangeAmount());

        // cancel mid-payment - cash already inserted is refunded, stock untouched
        machine.selectProduct("CHIPS");
        machine.insertCoin(Denomination.TEN);
        machine.cancel();
        System.out.println("State after cancel: " + machine.getState().getClass().getSimpleName());

        // admin collects the accumulated cash float
        System.out.println("Cash on hand before collection: " + admin.getCashOnHand());
        System.out.println("Collected: " + admin.collectCash());
        System.out.println("Cash on hand after collection: " + admin.getCashOnHand());
    }
}
