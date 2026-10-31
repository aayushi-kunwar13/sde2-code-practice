package dsa.question4;

public class Demo {

    public static void main(String[] args) {
        //Input: tiers = [[10, 50], [20, 40], [-1, 30]], quantity = 8

//        int[][] tiers = {{10, 50} , {20, 40} , {-1, 30}};
//        int quantity = 25;
//        long output = VolumePricingPart1.calculatePrice(tiers , quantity);
//        System.out.println(output);

//        int[][] tiers = {{10, 50} , {20, 40} , {-1, 30}};
//        int quantity = 15;
//        long output = VolumePricingPart2.calculatePrice(tiers , quantity);
//        System.out.println(output);

        int[][] tiers = {{10, 50} , {20, 40} , {-1, 30}};
        int budget = 745;
        long output = VolumePricingPart3.calculatePrice(tiers , budget);
        System.out.println(output);

    }

}
