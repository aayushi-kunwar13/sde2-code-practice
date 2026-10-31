package dsa.question7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PaymentInvoiceMatchPart3 {

    public static class Inovice{
        String invoiceID;
        Long amount;
        String dueDate;
        public Inovice(String invoiceID, Long amount, String dueDate) {
            this.invoiceID = invoiceID ; this.amount = amount; this.dueDate = dueDate;
        }
    }

    public static class Payment {
        String paymentId;
        Long amount;
        String invoiceId;
        public Payment(String paymentId, Long amount , String invoiceId) {
            this.paymentId = paymentId ; this.amount = amount ; this.invoiceId = invoiceId;
        }
    }


    public static String paymentMatch(String payment, String[] invoices, Long forgiveness) {

        List<Inovice> invoiceDetail = new ArrayList<>();
        for(int i=0; i< invoices.length; i++) {
            String[] invoice = invoices[i].split(",");
            String invoiceId = invoice[0] , dueDate = invoice[2];
            Long amount = Long.parseLong(invoice[1]);
            invoiceDetail.add(new Inovice(invoiceId, amount, dueDate));
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

        Payment paymentParse = new Payment(paymentId, amount, invoiceID);

        for(Inovice book : invoiceDetail) {
            if(book.invoiceID.equals(invoiceID)) {
                String duedate = book.dueDate;
                return paymentId + " pays off " + amount + " for " + invoiceID + " due on " + duedate;
            }
        }

        Inovice recocillerOnExactMatch = recocillerOnExactMatch(paymentParse, invoiceDetail);

        if(recocillerOnExactMatch != null) {
            return paymentId + " pays off " + amount + " for " + recocillerOnExactMatch.invoiceID + " due on " + recocillerOnExactMatch.dueDate;
        }

        Inovice reconcileOnForgiveness = reconcileOnForgiveness(paymentParse, invoiceDetail, forgiveness);

        if(reconcileOnForgiveness != null) {
            return paymentId + " pays off " + amount + " for " + reconcileOnForgiveness.invoiceID + " due on " + reconcileOnForgiveness.dueDate+ ", forgiving " + forgiveness;
        }


        return paymentId + " could not be matched to any invoice";
    }

    private static Inovice recocillerOnExactMatch(Payment payment, List<Inovice> invoiceDetail) {

        List<Inovice> amountMatched = new ArrayList<>();
        for(Inovice book : invoiceDetail) {
            Long amountDue = book.amount;
            Long amountPay = payment.amount;
            if(amountPay.equals(amountDue)) {
                amountMatched.add(book);
            }
        }

        if(amountMatched.isEmpty()) {
            return null;
        }

        // sort on due date and return first
        amountMatched.sort(Comparator.comparing( (Inovice a) -> a.dueDate )
                .thenComparing(a -> a.invoiceID));

        return amountMatched.get(0);
    }

    private static Inovice reconcileOnForgiveness(Payment payment, List<Inovice> invoiceDetail, Long forgiveness) {

        List<Inovice> amountMatched = new ArrayList<>();
        for(Inovice book : invoiceDetail) {
            Long amountDue = book.amount;
            Long amountPay = payment.amount;
            if(amountPay < amountDue  && amountDue <= amountPay + forgiveness) {
                amountMatched.add(book);
            }
        }

        if(amountMatched.isEmpty()) {
            return null;
        }

        // sort on due date and return first
        amountMatched.sort(Comparator.comparing( (Inovice a) -> a.dueDate )
                .thenComparing(a -> a.invoiceID));

        return amountMatched.get(0);
    }
}
