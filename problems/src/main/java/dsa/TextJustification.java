/**
 * Problem: Text Justification
 * Link: https://leetcode.com/problems/text-justification/
 * Difficulty: Hard
 * Topic: Strings / Simulation
 * Question: Pack words greedily into lines of exactly maxWidth characters. Spread extra spaces as evenly as possible (extra ones go to the left gaps); the last line and one-word lines are left-justified.
 * Insight: Greedy: take as many words as fit, then distribute spaces = (maxWidth - letters) across the gaps. (Work in progress.)
 */
//package dsa;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TextJustification {
//
//    public static List<String> fullJustify(String[] words, int maxWidth) {
//        int n = words.length , i=0;
//        List<String> ans = new ArrayList<>();
//
//        while(i < n) {
//            int j = i, lineLen = 0;
//            while(j<n && lineLen + words[j].length() + (j-i) < maxWidth) {
//                lineLen += words[j].length();
//                j++;
//            }
//            int gap = j-i+1;
//            StringBuilder temp = new StringBuilder();
//            if(j == n || gap == 0) {
//                // left justify
//
//                for(int k=i; k<j;k++) {
//                    temp.append(words[k]);
//                    if(k < j-1) {
//                        temp.append(' ');
//                    }
//                }
//                while(temp.length() < maxWidth) {
//                    temp.append(' ');
//                }
//            }
//        }
//    }
//}
