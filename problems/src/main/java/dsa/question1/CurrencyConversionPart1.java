/**
 * Problem: Currency Conversion
 * Difficulty: Medium
 * Topic: Graphs (DFS / BFS)
 * Question: Convert an amount using FROM:TO:RATE pairs: direct rate, then through other currencies, then the shortest route. Full statement in README.md.
 */
package dsa.question1;

import java.util.HashMap;

public class CurrencyConversionPart1 {

    public static Double convertCurrency(String inputstring, String fromcurreny , String tocurrency , Double amount) {
//        Input:
//        input_str = "USD:CAD:1.3,EUR:USD:1.1,GBP:EUR:1.15"
//        from_currency = "USD"
//        to_currency = "CAD"
//        amount = 100.0
         // note 1  : handle extra space
         // note 2  : handle if not present

          String[] conversion = inputstring.split(",");
          HashMap<String , Double> ratePerCurrency = new HashMap<>();

          for(int i=0; i<conversion.length; i++) {
              String[] parts = conversion[i].split(":");
              String currency1 = parts[0].trim();
              String currency2 = parts[1].trim();
              Double rate = Double.parseDouble(parts[2].trim());
              ratePerCurrency.put(currency1+"-"+currency2 , rate);
              ratePerCurrency.put(currency2+"-"+currency1 , 1.0/rate);
          }

          String key = fromcurreny + "-" + tocurrency;
          if(!ratePerCurrency.containsKey(key)) {
              return -1.0;
          }
          double rate = Math.round(amount * ratePerCurrency.get(key) * 100 ) / 100.0;
          return rate;
    }
}
