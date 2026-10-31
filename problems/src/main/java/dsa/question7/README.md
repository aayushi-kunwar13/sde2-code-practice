# Payment ↔ Invoice Matching

**Topic:** String parsing / Sorting · **Difficulty:** Easy → Medium

A payment is `paymentId,amount,memo` where the memo may say `Paying off: <invoiceId>` or `Paying for: <invoiceId>`.
Invoices are `invoiceId,amount,dueDate`.

## Part 1: match by memo
Read the invoice id from the memo (case-insensitive marker) and return
`"<paymentId> pays off <amount> for <invoiceId> due on <dueDate>"`,
or `"<paymentId> could not be matched to any invoice"`.

## Part 2: fall back to amount
If the memo doesn't name a known invoice, match an invoice with the **same amount**.
If several match, pick the earliest due date (then the smallest id).

## Part 3: forgiveness
If nothing matches exactly, allow a short payment within `forgiveness`
(`amount < invoiceAmount ≤ amount + forgiveness`) and append `", forgiving <forgiveness>"`.

**Approach:** parse once, then try the matchers in order: memo, exact amount, forgiveness. Break ties by sorting on `(dueDate, id)`.
