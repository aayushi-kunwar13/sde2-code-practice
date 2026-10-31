/**
 * Problem: Rank Teams by Votes
 * Link: https://leetcode.com/problems/rank-teams-by-votes/
 * Difficulty: Medium
 * Topic: Sorting
 * Question: Each voter ranks all teams (a string like "ACB"). Order teams by the most 1st-place votes; break ties by 2nd-place votes, and so on; if still tied, alphabetically.
 * Insight: Build a 26 x n table of votes per position, then sort teams with a comparator that walks the positions.
 */
package dsa;

import java.util.Arrays;

public class RankTeamByVotes {

    public static String rankTeams(String[] votes) {
        int len = votes.length , n = votes[0].length(), i=0;
        int[][] ranking = new int[26][n];
        for(i=0;i<n;i++) {
            Arrays.fill(ranking[i] , 0);
        }
        for(i=0;i<len;i++) {
            char[] word = votes[i].toCharArray();
            for(int j=0; j<word.length ;j++) {
                ranking[word[j]-'A'][j]++;
            }
        }
        Integer[] teams = new Integer[n];
        for(i=0;i<n;i++) {
            teams[i] = votes[0].charAt(i) - 'A';
        }

        Arrays.sort(teams , (a, b) -> {
            for(int j=0;j<n;j++) {
                if(ranking[a][j] != ranking[b][j]) {
                    return Integer.compare(ranking[b][j] , ranking[a][j]);
                }
            }
            return Integer.compare(a, b);
        });
        StringBuilder ans = new StringBuilder();
        for(i=0;i<n;i++) {
            ans.append((char) (teams[i]+'A'));
        }
        return ans.toString();

    }
}
