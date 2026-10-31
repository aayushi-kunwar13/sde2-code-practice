package dsa.question1;

import java.util.*;

public class CurrencyConversionPart2 {

    private HashMap<String , List<Pair>> ratePerCurrency;
    private List<String> path;
    private Set<String> isvisited;
    private Double finalRate;

    private Boolean dfs(String currency, String finalcurrency) {
       // base condition
        if(currency.equals(finalcurrency)) {
            return true;
        }

        isvisited.add(currency);

        if(ratePerCurrency.containsKey(currency)) {

            List<Pair> connectedcurrency = ratePerCurrency.get(currency);

            for(Pair p : connectedcurrency) {
                String nextcurrency = p.currency;
                Double nextrate = p.rate;
                if(!isvisited.contains(nextcurrency)) {
                    // add in path
                    path.add(nextcurrency);
                    finalRate = finalRate*nextrate;
                    if(dfs(nextcurrency, finalcurrency)) {
                        return true;
                    }
                    finalRate = finalRate/nextrate;
                    path.remove(path.size()-1);
                }
            }
        }
        return false;

    }
    public Map<String, Object> convertCurrency(String inputstring, String fromcurreny, String tocurrency, Double amount) {

        String[] conversion = inputstring.split(",");
        ratePerCurrency = new HashMap<>();
        path = new ArrayList<>();
        isvisited = new HashSet<>();
        finalRate = 1.0;

        for(int i=0; i<conversion.length; i++) {

            String[] parts = conversion[i].split(":");
            String currency1 = parts[0].trim();
            String currency2 = parts[1].trim();
            Double rate = Double.parseDouble(parts[2].trim());

            ratePerCurrency.computeIfAbsent(currency1 ,k -> new ArrayList<>()).add(new Pair(currency2, rate));
            ratePerCurrency.computeIfAbsent(currency2 ,k -> new ArrayList<>()).add(new Pair(currency1, 1.0/rate));

        }

        Map<String, Object> result = new HashMap<>();
        path.add(fromcurreny);
        if(dfs(fromcurreny, tocurrency)) {
            result.put("route" , String.join(" -> " , path));
            result.put("rate" , Math.round(finalRate*100.0) / 100.0);
            result.put("convert_value" , Math.round(amount * finalRate * 100.0) / 100.0);
        }else{
            result.put("route", "No available conversion");
            result.put("rate", -1);
            result.put("convert_value", -1);
        }

        return result;
    }

    private static class Pair {
        String currency;
        double rate;
        public Pair(String currency , double rate) {
            this.currency = currency;
            this.rate = rate;
        }
    }


}
