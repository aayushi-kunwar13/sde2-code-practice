/**
 * Problem: Merchant Payout Scheduler
 * Difficulty: Easy
 * Topic: Sorted map
 * Question: Group transactions into the next payout day for a payout interval and print the totals per day. Full statement in README.md.
 */
package dsa.question9;

import dsa.PaymentPArser;

import java.util.*;

public class MerchantPayoutScheduler {

    public static class Payout {
        long day;
        long amount;
        String transaction;
        public Payout(long day, long amount, String transaction) {
            this.amount = amount ; this.day = day ; this.transaction = transaction;
        }
    }
    public static String[] getPayout(String[] transactions, int payoutInterval) {

        TreeMap<Long, List<Payout>> payoutList = new TreeMap<>();

        for(int i=0;i<transactions.length;i++) {
            String[] transactionDetail = transactions[i].split(",");
            String transactionID = transactionDetail[0];
            long day , amount;
            try {
                amount = Long.parseLong(transactionDetail[1]);
                day = Long.parseLong(transactionDetail[2]);
            }catch (NumberFormatException e) {
                continue;
            }

            long payoutDay = (day / payoutInterval + 1) * payoutInterval;

            Payout payout = new Payout(payoutDay, amount, transactionID);
            payoutList.computeIfAbsent(payoutDay, k-> new ArrayList<>()).add(payout);

        }

        List<String> ans = new ArrayList<>();

        for(Map.Entry<Long, List<Payout>> payments : payoutList.entrySet()) {

            StringBuilder temp = new StringBuilder();
            Long key = payments.getKey();
            List<Payout> transactionsList = payments.getValue();
            long sum = 0;
            for(int j=0 ; j< transactionsList.size(); j++) {
                sum+= transactionsList.get(j).amount;
                temp.append(transactionsList.get(j).transaction);
                if(j != transactionsList.size()-1){
                    temp.append(", ");
                }
            }
            ans.add("Day " + key.toString() + ": $" + sum/100 + "." + String.format("%02d" , sum%100) + " [" + temp + "]");
        }

        return ans.toArray(new String[0]);

    }
}
