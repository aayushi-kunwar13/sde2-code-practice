/**
 * Problem: Spotting an Error Spike
 * Difficulty: Hard
 * Topic: Sliding window
 * Question: Alert when a merchant has 5+ errors in 30s, then relative to successes, then with TRIGGER/RESOLVE events. Full statement in README.md.
 */
package dsa.question6;

import java.util.*;

public class SpottingAnErrorSpike {

    HashMap<String, Alert> alertGenerated;
    HashMap<String, List<Pair>> timestampOfAlert;
    int alertThreshold = 5;

    public void processAlert(long timeStamp, String merchantId, int statusCode, int count) {
        if(statusCode == 200) {
            return;
        }

        String key = merchantId + "-" + statusCode;
        if(alertGenerated.containsKey(key)) {
            return;
        }

        //add
        timestampOfAlert.computeIfAbsent(key, k -> new ArrayList<>()).add(new Pair(timeStamp, count));
        List<Pair> alerts = timestampOfAlert.get(key);
        int threshold = 0;
        long timelimit = timeStamp - 30;
        for(int i = alerts.size() - 1; i>=0 ;i-- ) {
            if(alerts.get(i).timestamp > timelimit) {
                threshold += alerts.get(i).count;
            }else{
                break;
            }
        }
        if(alertThreshold <= threshold) {
            Alert alert = new Alert(timeStamp, merchantId, statusCode, "TRIGGER");
            alertGenerated.put(key, alert);
        }

        return;

    }

    public List<Alert> spotSpikeAlert(String[] alertLog) {

        alertGenerated = new LinkedHashMap<>();
        timestampOfAlert = new HashMap<>();

        for(int i=0 ;i<alertLog.length; i++) {
            String[] log = alertLog[i].split(",");
            long timestamp = Long.valueOf(log[0].trim());
            String merchant = log[1].trim();
            int statusCode = Integer.valueOf(log[2].trim());
            int count = Integer.valueOf(log[3].trim());

            processAlert(timestamp, merchant, statusCode, count);
        }

        List<Alert> ans = new ArrayList<>();
        alertGenerated.forEach((key, value) -> {
            ans.add(value);
        });

        ans.sort(
                Comparator.comparingLong((Alert a) -> a.timestamp)
                        .thenComparing(a -> a.merchant)
                        .thenComparingInt(a -> a.statusCode)
        );

        return ans;
    }

    public class Pair{
        long timestamp;
        int count;
        public Pair(long timestamp, int count) {
            this.timestamp = timestamp;
            this.count = count;
        }
    }

    public class Alert {

        long timestamp;
        String event;
        String merchant;
        int statusCode;

        public Alert(long timestamp, String merchant, int statusCode, String event) {
            this.timestamp = timestamp;
            this.merchant = merchant;
            this.statusCode = statusCode;
            this.event = event;
        }
    }
}
