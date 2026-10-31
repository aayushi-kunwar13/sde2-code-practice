# Vending Machine

**Design a vending machine.**

- Admin restocks products and loads coins for change, and can collect the cash.
- A user selects a product, inserts coins/notes, and gets the product plus change broken into denominations.
- Errors: out of stock, not enough money, no change available, unknown product.
- The user can cancel and get a refund.

**Design:** State pattern (Idle → ProductSelected → Dispensing), `CashBox` for change-making by denomination,
`Inventory`, custom exceptions, `AdminService`. Run `VendingMachineDemo`.
