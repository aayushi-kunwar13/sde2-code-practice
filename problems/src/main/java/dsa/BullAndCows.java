/**
 * Problem: Bulls and Cows
 * Link: https://leetcode.com/problems/bulls-and-cows/
 * Difficulty: Medium
 * Topic: Hashing
 * Question: Given a secret number and a guess of the same length, return the hint "xAyB": x = digits right in value and position (bulls), y = digits right in value but in the wrong position (cows).
 * Insight: Count bulls position by position, count every digit match with a frequency map, then cows = matches - bulls.
 */
package dsa;

import java.util.HashMap;

public class BullAndCows {
    public static String getHint(String secret, String guess) {
        int n = secret.length(), i=0;
        int numberOfCows = 0, numberOfBulls =0 ;
        HashMap<Character , Integer> numbers = new HashMap<>();
        for(i=0;i<n;i++) {
            char c = secret.charAt(i);
            numbers.put(c , numbers.getOrDefault(c, 0)+1);
            char guesspos = guess.charAt(i);
            if(c == guesspos) {
                numberOfBulls++;
            }
        }

        for(i=0;i<n;i++) {
            char c = guess.charAt(i);
            if(numbers.containsKey(c) && numbers.get(c) > 0) {
                numbers.put(c , numbers.getOrDefault(c, 0)-1);

                numberOfCows++;
            }
        }
        numberOfCows = numberOfCows-numberOfBulls;

        StringBuilder ans = new StringBuilder();
        ans.append(numberOfBulls).append("A").append(numberOfCows).append("B");
        return ans.toString();
    }
}
