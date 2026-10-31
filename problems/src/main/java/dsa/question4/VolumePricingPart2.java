package dsa.question4;

public class VolumePricingPart2 {

    public static long calculatePrice(int[][] tiers, int quantity) {
        int n = tiers.length , ed=0 , remainingQty = quantity;
        long ans=0;
        while(remainingQty > 0 && ed < n) {
            int upto = tiers[ed][0], cost = tiers[ed][1];

            if(upto == -1) {
                ans += (long) remainingQty * cost;
                remainingQty = 0;
            }else {
                int excluded = quantity-remainingQty ;
                int include = Math.min(quantity, upto) - excluded;
                ans += (long) include * cost;
                remainingQty = remainingQty - include;
            }

            ed++;
        }
        return ans;
    }
}
