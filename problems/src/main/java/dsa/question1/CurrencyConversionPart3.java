package dsa.question1;

import java.util.*;

public class CurrencyConversionPart3 {
  
    private HashMap<String , List<Pair>> ratePerCurrency;
    private List<String> path;
    private Double finalRate;

    public Map<String, Object> convertCurrency(String inputstring, String fromcurreny, String tocurrency, Double amount) {

        String[] conversion = inputstring.split(",");
        ratePerCurrency = new HashMap<>();
        path = new ArrayList<>();
        finalRate = 1.0;

        for(int i=0; i<conversion.length; i++) {

            String[] parts = conversion[i].split(":");
            String currency1 = parts[0].trim();
            String currency2 = parts[1].trim();
            Double rate = Double.parseDouble(parts[2].trim());

            ratePerCurrency.computeIfAbsent(currency1 ,k -> new ArrayList<>()).add(new Pair(currency2, rate));
            ratePerCurrency.computeIfAbsent(currency2 ,k -> new ArrayList<>()).add(new Pair(currency1, 1.0/rate));

        }

        HashMap<String , Pair> parentCurrency = new HashMap<>();

        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(fromcurreny , 1.0));
        parentCurrency.put(fromcurreny, new Pair(null, 1.0));

        while(!queue.isEmpty()) {
            Pair p = queue.poll();
            String currentcurrency = p.currency; Double rate = p.rate;

            if(currentcurrency.equals(tocurrency)) {
                break;
            }

            if(ratePerCurrency.containsKey(currentcurrency)) {
                for(Pair nextPair : ratePerCurrency.get(currentcurrency)) {
                    String nextcurrency = nextPair.currency; Double nextrate = nextPair.rate;
                    if(!parentCurrency.containsKey(nextcurrency)) {
                        parentCurrency.put(nextcurrency , new Pair(currentcurrency , nextrate));
                        queue.offer(new Pair(nextcurrency , nextrate));
                    }
                }
            }

        }
        Map<String, Object> result = new HashMap<>();

        if(parentCurrency.containsKey(tocurrency)) {
            String currency = tocurrency;
            while(currency != null) {
                path.add(currency);
                finalRate = finalRate * parentCurrency.get(currency).rate;
                currency = parentCurrency.get(currency).currency;
            }

            Collections.reverse(path);

            result.put("route" , String.join(" -> " , path));
            result.put("rate" , Math.round(finalRate * 100.0)/100.0);
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
