package dsa.question4;

public class VolumePricingPart3 {

    public static long calculatePrice(int[][] tiers, long budget) {
        int n = tiers.length , ed=0;
        long remainingbudget = budget;
        long quantity = 0;
        while(remainingbudget > 0 && ed < n) {
            int upto = tiers[ed][0], cost = tiers[ed][1];
            long number = remainingbudget/cost;
            if(upto == -1) {
                return quantity + number;
            }else if (number + quantity < upto) {
                return quantity + number;
            } else {
                long take = upto - quantity;
                remainingbudget -= (long) cost * take;
                quantity = upto;
            }
            ed++;
        }
        return quantity;
    }
}
