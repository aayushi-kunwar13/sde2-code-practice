package dsa.question6;

import java.util.*;

public class SpottingErrorSpikePart3 {

    HashSet<String> active = new HashSet<>();
    List<Alert> events = new ArrayList<>();
    HashMap<String, List<Pair>> timestampOfAlert = new HashMap<>();
    HashMap<String, Set<Integer>> failureCodes = new HashMap<>();
    int alertThreshold = 5;

    public void processAlert(long timeStamp, String merchantId, int statusCode, int count) {
        String key = merchantId + "-" + statusCode;
        timestampOfAlert.computeIfAbsent(key, k -> new ArrayList<>()).add(new Pair(timeStamp, count));
        if (statusCode != 200) {
            failureCodes.computeIfAbsent(merchantId, k -> new HashSet<>()).add(statusCode);
        }
        for (int code : failureCodes.getOrDefault(merchantId, Collections.emptySet())) {
            check(timeStamp, merchantId, code);
        }
    }

    private void check(long timeStamp, String merchantId, int statusCode) {
        String key = merchantId + "-" + statusCode;

        List<Pair> alerts = timestampOfAlert.get(key);
        long fails = 0, success = 0;
        long timelimit = timeStamp - 30;
        for (int i = alerts.size() - 1; i >= 0; i--) {
            if (alerts.get(i).timestamp > timelimit) {
                fails += alerts.get(i).count;
            } else {
                break;
            }
        }

        String successkey = merchantId + "-" + 200;
        if (timestampOfAlert.containsKey(successkey)) {
            List<Pair> successLog = timestampOfAlert.get(successkey);
            for (int i = successLog.size() - 1; i >= 0; i--) {
                if (successLog.get(i).timestamp > timelimit) {
                    success += successLog.get(i).count;
                } else {
                    break;
                }
            }
        }

        boolean meets = alertThreshold <= fails && fails * 100 > success;
        if (meets && !active.contains(key)) {
            active.add(key);
            events.add(new Alert(timeStamp, merchantId, statusCode, "TRIGGER"));
        } else if (!meets && active.contains(key)) {
            active.remove(key);
            events.add(new Alert(timeStamp, merchantId, statusCode, "RESOLVE"));
        }
    }

    public List<Alert> spotSpikeAlert(String[] alertLog) {

        active = new HashSet<>();
        events = new ArrayList<>();
        timestampOfAlert = new HashMap<>();

        for(int i=0 ;i<alertLog.length; i++) {
            String[] log = alertLog[i].split(",");
            long timestamp = Long.valueOf(log[0].trim());
            String merchant = log[1].trim();
            int statusCode = Integer.valueOf(log[2].trim());
            int count = Integer.valueOf(log[3].trim());

            processAlert(timestamp, merchant, statusCode, count);
        }


        events.sort(
                Comparator.comparingLong((Alert a) -> a.timestamp)
                        .thenComparing(a -> a.merchant)
                        .thenComparingInt(a -> a.statusCode)
        );

        return events;
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
