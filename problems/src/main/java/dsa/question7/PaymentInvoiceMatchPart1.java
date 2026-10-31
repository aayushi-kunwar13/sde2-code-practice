/**
 * Problem: Payment Invoice Matching
 * Difficulty: Medium
 * Topic: String parsing / Sorting
 * Question: Match a payment to an invoice by memo, then by equal amount (earliest due date), then within a forgiveness amount. Full statement in README.md.
 */
package dsa.question7;

import java.util.HashMap;

public class PaymentInvoiceMatchPart1 {

    public static class Inovice{
        String invoiceID;
        Long amount;
        String dueDate;
        public Inovice(String invoiceID, Long amount, String dueDate) {
            this.invoiceID = invoiceID ; this.amount = amount; this.dueDate = dueDate;
        }
    }

    public static String paymentMatch(String payment, String[] invoices) {
        HashMap<String, Inovice> invoiceDetail = new HashMap<>();

        for(int i=0; i< invoices.length; i++) {
            String[] invoice = invoices[i].split(",");
            String invoiceId = invoice[0] , dueDate = invoice[2];
            Long amount = Long.parseLong(invoice[1]);
            invoiceDetail.put(invoiceId, new Inovice(invoiceId, amount, dueDate));
        }

        String[] paymentDetail = payment.split("," , 3);
        String paymentId = paymentDetail[0];
        Long amount = Long.parseLong(paymentDetail[1]);
        String matchInvoice = paymentDetail[2];
        String lowered = matchInvoice.toLowerCase();
        String invoiceID = "";

        for(String marker : new String[] {"paying off:" , "paying for:"}) {
            int index = lowered.indexOf(marker);
            if(index != -1) {
                invoiceID = matchInvoice.substring(index + marker.length()).trim();
                break;
            }
        }

        if(!invoiceDetail.containsKey(invoiceID)) {
            return paymentId + " could not be matched to any invoice";
        }

        String duedate = invoiceDetail.get(invoiceID).dueDate;

        return paymentId + " pays off " + amount + " for " + invoiceID + " due on " + duedate;
    }

}
