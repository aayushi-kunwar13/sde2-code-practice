/**
 * Problem: Accept-Language Parser
 * Difficulty: Medium
 * Topic: String parsing
 * Question: Return the server-supported languages in the order an Accept-Language header prefers them, then with q= weights. Full statement in README.md.
 */
package dsa.question2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LanguageParserPart1 {

    public static List<String> parseLanguage(String headers, String[] supportedLanguage) {

        HashSet<String> langauge = new HashSet<>();
        List<String> output = new ArrayList<>();
        String[] headerList = headers.split(",");

        for(int i=0; i<supportedLanguage.length;i++) {
            String lang = supportedLanguage[i].trim().toLowerCase();
            langauge.add(lang);
        }
        for(int i=0;i<headerList.length;i++) {
            String input = headerList[i].trim().toLowerCase();
            if(langauge.contains(input)) {
                langauge.remove(input);
                output.add(input);
            }
        }
        return output;
    }
}
