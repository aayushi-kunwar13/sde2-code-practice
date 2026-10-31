package dsa.question5;

import java.util.HashMap;

public class Demo {

  public static void main (String[] args) {
      // part1
//      String input = "UK:US:FedEx:4,UK:FR:Jet1:2,US:UK:RyanAir:8,CA:UK:CanadaAir:8";
//      String source = "UK";
//      String destination = "US";
//
//      int cost = FlightCostCalculationPart1.flightCostCalculation(input, source, destination);
//      System.out.println(cost);

//      String input_str = "UK:FR:Jet1:2,FR:US:AmericanAir:6";
//      String source = "UK";
//      String destination = "US";
//      int cost = FlightCostCalculationPart2.flightCostCalculation(input_str, source, destination);
//      System.out.println(cost);

      String input_str = "UK:FR:Jet1:2,FR:DE:Lufthansa:3,DE:US:Delta:5";
      String source = "UK";
      String destination = "US" ;
      FlightCostCalculationPart3 flightCostCalculationPart3 = new FlightCostCalculationPart3();
      HashMap<String, Object> res = flightCostCalculationPart3.flightCostCalculation(input_str, source, destination);
      System.out.println(res);
  }
}

