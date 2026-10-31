/**
 * Problem: Flight Cost Calculation
 * Difficulty: Medium
 * Topic: Graphs
 * Question: Cheapest flight from SRC:DST:AIRLINE:COST entries: direct, one stop, then any stops with route and airlines. Full statement in README.md.
 */
package dsa.question5;

public class FlightCostCalculationPart1 {

    public static int flightCostCalculation(String inputString , String source, String destination) {
        String[] flights = inputString.split(",");
        int mincost = Integer.MAX_VALUE;
        source = source.trim(); destination = destination.trim();
        for(int i=0 ;i<flights.length; i++) {
            String[] details = flights[i].split(":");
            if(details.length < 4) {
                continue;
            }
            String s1 = details[0].trim() ; String d1 = details[1].trim() ;
            int cost;
            if(s1.equalsIgnoreCase(source) && d1.equalsIgnoreCase(destination)) {
                try {
                    cost = Integer.valueOf(details[3].trim());
                    mincost = Math.min(mincost, cost);
                } catch (NumberFormatException exception) {

                }
            }

        }
        return mincost == Integer.MAX_VALUE ? -1:mincost;
    }
}
