package dsa.question3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IdempotentReplayPart3 {
    static class Request {
        String key;
        int value;
        long time;
        public Request(String key, int value, long time) {
            this.key = key;
            this.value = value;
            this.time = time;
        }
    }

    static class Pair{
        int value;
        long expireTime;
        public Pair(int value, long expireTime) {
            this.value = value;
            this.expireTime = expireTime;
        }
    }

    public static List<Integer> inputRequest(List<Request> processRequest, long ttl) {
        HashMap<String , Pair> store = new HashMap<>();
        List<Integer> output = new ArrayList<>();
        for(int i=0; i<processRequest.size(); i++) {

            String key = processRequest.get(i).key;
            int value = processRequest.get(i).value;
            long time = processRequest.get(i).time;

            if(key == null || key.isEmpty()){
                output.add(value);
            } else if(store.containsKey(key)) {
                Pair lastValue = store.get(key);
                if(time > lastValue.expireTime) {
                    output.add(value);
                    store.put(key , new Pair(value, time+ttl));
                }else if(lastValue.value != value){
                    output.add(-1);
                } else{
                    output.add(value);
                }

            }else {
                output.add(value);
                store.put(key , new Pair(value, time+ttl));
            }
        }
        return output;
    }
}
