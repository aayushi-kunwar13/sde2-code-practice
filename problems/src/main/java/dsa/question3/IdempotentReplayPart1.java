/**
 * Problem: Idempotent Request Replay
 * Difficulty: Medium
 * Topic: Hashing / Caching
 * Question: Replay stored responses for repeated idempotency keys, then with key expiry (TTL), then rejecting conflicting retries. Full statement in README.md.
 */
package dsa.question3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IdempotentReplayPart1 {

    static class Request {
        String key;
        int value;
        public Request(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public static List<Integer> inputRequest(List<Request> input) {
        HashMap<String , Integer> requestedKey = new HashMap<>();
        List<Integer> output = new ArrayList<>();
        for(int i=0; i<input.size(); i++) {
            String key = input.get(i).key;
            int value = input.get(i).value;
            if(key == null || key.equals("")) {
                output.add(value);
            }else if(requestedKey.containsKey(key)) {
                output.add(requestedKey.get(key));
            }else{
                output.add(value);
                requestedKey.put(key, value);
            }
        }
        return output;
    }
}
