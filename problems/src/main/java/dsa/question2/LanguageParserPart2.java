package dsa.question2;

import java.util.*;

public class LanguageParserPart2 {

    public static List<String> parseLanguage(String headers, String[] supportedLanguage) {
        List<String> output = new ArrayList<>();
        if (headers == null) return output;

        Set<String> remaining = new HashSet<>();
        for (String s : supportedLanguage) remaining.add(s.trim().toLowerCase());

        List<Pair> entries = new ArrayList<>();
        for (String token : headers.split(",")) {
            String[] parts = token.split(";");
            String lang = parts[0].trim().toLowerCase();
            double q = 1.0;
            if (parts.length > 1) {
                String param = parts[1].trim();
                if (param.startsWith("q=")) {
                    try {
                        q = Double.parseDouble(param.substring(2).trim());
                    } catch (NumberFormatException e) {
                        q = 0.0;
                    }
                }
            }
            if (q > 0 && remaining.contains(lang)) {
                entries.add(new Pair(lang, q));
            }
        }

        entries.sort((a, b) -> Double.compare(b.q, a.q));

        for (Pair p : entries) {
            if (remaining.remove(p.lang)) output.add(p.lang);
        }
        return output;
    }

    private static class Pair {
        String lang;
        double q;
        Pair(String lang, double q) { this.lang = lang; this.q = q; }
    }
}