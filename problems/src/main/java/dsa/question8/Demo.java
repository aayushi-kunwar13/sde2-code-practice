package dsa.question8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Demo {

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> input = new ArrayList<>();
        String line;
        while( (line = br.readLine()) != null) {
            if(line.isEmpty()) {
                continue;
            }
            input.add(line);
        }

        List<String> result = BuildTaskAndSubTask.printTaskHierarchy(input.toArray(new String[0]));
        StringBuilder sb = new StringBuilder();
        for(String s : result) {
            sb.append(s);
            sb.append('\n');
        }

        System.out.println(sb);

    }
}
