package com.practice.phonepe.questions.vendingmachine;

import com.practice.phonepe.questions.vendingmachine.entities.CashBox;
import com.practice.phonepe.questions.vendingmachine.entities.Denomination;
import com.practice.phonepe.questions.vendingmachine.entities.DispenseResult;
import com.practice.phonepe.questions.vendingmachine.entities.Inventory;
import com.practice.phonepe.questions.vendingmachine.state.IdleState;
import com.practice.phonepe.questions.vendingmachine.state.VendingMachineState;

import java.util.Collections;
import java.util.List;

/** State-pattern context. Every user action is forwarded to whichever state is currently active. */
public class VendingMachine {

    private final Inventory inventory = new Inventory();
    private final CashBox cashBox = new CashBox();

    private VendingMachineState currentState = new IdleState();
    private String selectedProductCode;
    private int amountPaid;

    // --- user-facing actions ---

    public void selectProduct(String productCode) {
        currentState.selectProduct(this, productCode);
    }

    public void insertCoin(Denomination denomination) {
        currentState.insertMoney(this, denomination.getValue());
        cashBox.deposit(denomination);
    }

    public DispenseResult dispense() {
        return currentState.dispense(this);
    }

    public void cancel() {
        currentState.cancel(this);
    }

    // --- exposed for AdminService ---

    public Inventory getInventory() {
        return inventory;
    }

    public CashBox getCashBox() {
        return cashBox;
    }

    // --- accessors the states operate on ---

    public void setState(VendingMachineState state) {
        this.currentState = state;
    }

    public VendingMachineState getState() {
        return currentState;
    }

    public String getSelectedProductCode() {
        return selectedProductCode;
    }

    public void setSelectedProductCode(String selectedProductCode) {
        this.selectedProductCode = selectedProductCode;
    }

    public int getAmountPaid() {
        return amountPaid;
    }

    public void addAmountPaid(int amount) {
        this.amountPaid += amount;
    }

    public void resetPayment() {
        this.amountPaid = 0;
        this.selectedProductCode = null;
    }

    /** Converts change owed into physical denominations pulled from the cash float. */
    public List<Denomination> settleChange(int changeDue) {
        if (changeDue <= 0) {
            return Collections.emptyList();
        }
        return cashBox.makeChange(changeDue);
    }

    /** Refunds whatever cash has been inserted so far when a transaction is cancelled mid-payment. */
    public void refundCurrentPayment() {
        if (amountPaid <= 0) {
            return;
        }
        List<Denomination> refunded = cashBox.makeChange(amountPaid);
        System.out.println("Refunded cash: " + refunded);
    }
}
