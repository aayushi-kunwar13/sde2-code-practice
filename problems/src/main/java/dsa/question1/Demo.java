package dsa.question1;

import java.util.Map;

public class Demo {
    public static void main(String[] args) {
        // part1
        String input = "USD:CAD:1.3,EUR:USD:1.1,GBP:EUR:1.15";
//        String currency1 = "EUR";
//        String currency2 = "GBP";
//        Double amount = CurrencyConversionPart1.convertCurrency(input, currency1, currency2, 100.0);
//        System.out.println(amount);
//
//        // part2 - space in currency or rate
//        String input2 = "USD :CAD:1.3,EUR:USD:1.1,GBP :EUR:1.15 ";
//        Double amount1 = CurrencyConversionPart1.convertCurrency(input2, "EUR", "GBP", 100.0);
//        System.out.println(amount1);
//
//
//        // part2 - if not prresnt
//        Double amount3 = CurrencyConversionPart1.convertCurrency(input2, "AUD", "GBP", 100.0);
//        System.out.println(amount3);

//        CurrencyConversionPart2 currencyConversionPart2 = new CurrencyConversionPart2();
//        // Format: FROM:TO:RATE
        String input1 = "AUD:USD:0.65,USD:GBP:0.80,GBP:INR:110";
//
//        Map<String, Object> result =
//                currencyConversionPart2.convertCurrency(input1, "AUD", "GBP", 100.0);
//
//        System.out.println("Route: " + result.get("route"));
//        System.out.println("Rate: " + result.get("rate"));
//        System.out.println("Converted Amount: " + result.get("convert_value"));

        String input3 = "USD:EUR:0.85,EUR:GBP:0.87,USD:CAD:1.3,CAD:AUD:1.1,AUD:NZD:1.05,NZD:GBP:0.5";

        CurrencyConversionPart3 currencyConversionPart3 = new CurrencyConversionPart3();
        Map<String, Object> result3 =
                currencyConversionPart3.convertCurrency(input3, "USD", "GBP", 100.0);

        System.out.println("Route: " + result3.get("route"));
        System.out.println("Rate: " + result3.get("rate"));
        System.out.println("Converted Amount: " + result3.get("convert_value"));

    }


}
