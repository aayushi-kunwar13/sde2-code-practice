/**
 * Problem: Payment Parser
 * Difficulty: Easy
 * Topic: String parsing
 * Question: A payment is "paymentId,amount,Paying off: invoiceId" and invoices are "invoiceId,dueDate,amount". Return "<paymentId> pays off <amount> for <invoiceId> due on <dueDate>", or NOT_FOUND. (First attempt; the full 3-part version is question7.)
 * Insight: Split on commas, pull the invoice id after "Paying off: ", then look it up.
 */
package dsa;

import java.util.List;

public class PaymentPArser {

    public static String paymentDetail(String payment, String[] invoices) {
        String[] paymentDetails = payment.split(",");
        String paymentID = paymentDetails[0];
        String invoiceID = paymentDetails[2].split(": ")[1];
        String amount = paymentDetails[1];
        String date = "";

        for(int i=0; i<invoices.length;i++) {
            String[] invoiceDetail = invoices[i].split(",");
            if(invoiceDetail[0].equals(invoiceID)) {
                date = invoiceDetail[1];
            }
        }

        if(date.equals("")) {
            return "NOT_FOUND";
        }

        return paymentID + " pays off " + amount + " for " + invoiceID + " due on " + date;

    }
}
