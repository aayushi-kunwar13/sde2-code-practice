package dsa.question5;

import java.util.HashMap;
import java.util.Map;

public class FlightCostCalculationPart2 {

    public static int flightCostCalculation(String inputString , String source, String destination) {
        HashMap<String , Integer> flightsToCost = new HashMap<>();

        source = source.trim().toLowerCase(); destination = destination.trim().toLowerCase();
        String[] flights = inputString.split(",");
        for(int i=0 ;i<flights.length; i++) {

            String[] details = flights[i].split(":");
            if(details.length < 4) {
                continue;
            }
            String s1 = details[0].trim().toLowerCase() ; String d1 = details[1].trim().toLowerCase() ;
            String key = s1 + "-" + d1;

            int cost;
            try {
                cost = Integer.valueOf(details[3].trim());
                if(flightsToCost.containsKey(key) && flightsToCost.get(key) < cost) {
                    // do nothing
                } else{
                    flightsToCost.put(key, cost);
                }
            } catch (NumberFormatException exception) {
                continue;
            }
        }

        String sourceToDestination = source + "-" + destination;
        if(flightsToCost.containsKey(sourceToDestination)) {
            return flightsToCost.get(sourceToDestination);
        }
        int mincost = Integer.MAX_VALUE;

        for(Map.Entry<String, Integer> entry : flightsToCost.entrySet()) {
            String key = entry.getKey();
            int cost = entry.getValue();

            String[] splitSrcAndDest = key.split("-");
            String s1 = splitSrcAndDest[0] , d1 = splitSrcAndDest[1];
            if(s1.equalsIgnoreCase(source)) {
                // find path from d1 to destination
                String path = d1 + "-" + destination;
                if(flightsToCost.containsKey(path)) {
                    mincost = Math.min(mincost, cost + flightsToCost.get(path));
                }
            }

        }


        return mincost == Integer.MAX_VALUE ? -1 : mincost;
    }
}
