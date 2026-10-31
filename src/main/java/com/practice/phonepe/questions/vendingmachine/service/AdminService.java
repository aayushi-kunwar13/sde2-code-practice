package com.practice.phonepe.questions.vendingmachine.service;

import com.practice.phonepe.questions.vendingmachine.VendingMachine;
import com.practice.phonepe.questions.vendingmachine.entities.Denomination;
import com.practice.phonepe.questions.vendingmachine.entities.Product;

import java.util.Map;

/** The operator-facing surface: load stock, seed the change float, and collect cash out of it. */
public class AdminService {

    private final VendingMachine machine;

    public AdminService(VendingMachine machine) {
        this.machine = machine;
    }

    public void restock(String productCode, String name, int price, int quantity) {
        machine.getInventory().addOrRestock(new Product(productCode, name, price), quantity);
    }

    public void loadChangeFloat(Denomination denomination, int count) {
        machine.getCashBox().deposit(denomination, count);
    }

    public Map<Denomination, Integer> collectCash() {
        return machine.getCashBox().collectAll();
    }

    public int getCashOnHand() {
        return machine.getCashBox().getTotalValue();
    }
}
