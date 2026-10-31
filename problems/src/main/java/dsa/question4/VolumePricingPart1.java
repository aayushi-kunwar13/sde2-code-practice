/**
 * Problem: Volume (Tiered) Pricing
 * Difficulty: Medium
 * Topic: Greedy / Math
 * Question: Price a quantity with all-units tiers, then graduated tiers, then find the most units a budget buys. Full statement in README.md.
 */
package dsa.question4;

public class VolumePricingPart1 {

    public static long calculatePrice(int[][] tiers, int quantity) {
        int n = tiers.length,i=0;
        if(quantity <= 0) {
            return 0;
        }
        for(i=0;i<n;i++) {
            if(tiers[i][0] == -1) {
                return (long)quantity*tiers[i][1];
            }else if(tiers[i][0] >= quantity) {
                return (long)quantity*tiers[i][1];
            }
        }
        return 0;
    }
}
