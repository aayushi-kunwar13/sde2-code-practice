package dsa.question5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FlightCostCalculationPart3 {

    private HashMap<String , List<FlightInfo>> flightsToCost;
    private List<String> isvisited;
    private List<String> path;
    private List<String> flightdetail;
    private int finalCost;

    public class FlightInfo {
        String source;
        String destination;
        int cost;
        String flightname;
        public FlightInfo(String source, String destination, int cost, String flight) {
            this.source = source;
            this.destination = destination;
            this.cost = cost;
            this.flightname = flight;
        }
    }

    private Boolean dfs(String source, String destination, String flightname, int cost) {

        isvisited.add(source);
        path.add(source);
        finalCost += cost;
        if(flightname != null) {
            flightdetail.add(flightname);
        }

        if(source.equalsIgnoreCase(destination)) {
            return true;
        }

        List<FlightInfo> differentFlights = flightsToCost.get(source);

        for(FlightInfo flights : differentFlights) {
            String d1 = flights.destination , n1 = flights.flightname ;
            int cost1 = flights.cost;
            if(!isvisited.contains(d1)) {
                if(dfs(d1, destination, n1, cost1)) {
                    return true;
                }
            }
        }
        isvisited.remove(isvisited.size()-1);
        path.remove(path.size()-1);
        if(!flightdetail.isEmpty()) {
            flightdetail.remove(flightdetail.size()-1);
        }
        finalCost -= cost;

        return false;
    }

    public HashMap<String, Object> flightCostCalculation(String inputString , String source, String destination) {

       flightsToCost = new HashMap<>();
        isvisited = new ArrayList<>();
        path = new ArrayList<>();
        flightdetail = new ArrayList<>();
        finalCost = 0;

        source = source.trim().toLowerCase(); destination = destination.trim().toLowerCase();
        String[] flights = inputString.split(",");

        for(int i=0 ;i<flights.length; i++) {

            String[] details = flights[i].split(":");
            if(details.length < 4) {
                continue;
            }
            String s1 = details[0].trim().toLowerCase() ; String d1 = details[1].trim().toLowerCase(); String name = details[2];

            int cost;
            try {
                cost = Integer.valueOf(details[3].trim());
                flightsToCost.computeIfAbsent(s1, k -> new ArrayList<>()).add(new FlightInfo(s1, d1, cost, name));
            } catch (NumberFormatException exception) {
                continue;
            }
        }

        HashMap<String, Object> result = new HashMap<>();

        if(!dfs(source, destination, null, 0)) {
            result.put("route" , null);
            result.put("method" , null);
            result.put("cost" , 0);
        }else{
            result.put("route" , String.join("->" , path));
            result.put("method" , String.join("->" , flightdetail));
            result.put("cost" , finalCost);
        }

        return result;
    }

}
