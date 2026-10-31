package dsa.question3;

import java.util.ArrayList;
import java.util.List;

public class Demo {

    public static void main(String args[]) {
//        IdempotentReplayPart1.Request request1 = new IdempotentReplayPart1.Request("abc" , 200);
//        IdempotentReplayPart1.Request request2 = new IdempotentReplayPart1.Request("adc" , 200);
//        IdempotentReplayPart1.Request request3 = new IdempotentReplayPart1.Request("" , 500);
//        IdempotentReplayPart1.Request request4 = new IdempotentReplayPart1.Request("" , 800);
//        IdempotentReplayPart1.Request request5 = new IdempotentReplayPart1.Request("abc" , 800);
//
//        List<IdempotentReplayPart1.Request> input = new ArrayList<>();
//        input.add(request1);
//        input.add(request2);
//        input.add(request3);
//        input.add(request4);
//        input.add(request5);
//
//        List<Integer> output = IdempotentReplayPart1.inputRequest(input);
//        System.out.println(output);

        IdempotentReplayPart2.Request request1 = new IdempotentReplayPart2.Request("key_a" , 1000, 1000);
        IdempotentReplayPart2.Request request2 = new IdempotentReplayPart2.Request("key_a" , 2000, 2000);
        IdempotentReplayPart2.Request request3 = new IdempotentReplayPart2.Request("key_a" , 3000, 5000);
        IdempotentReplayPart2.Request request4 = new IdempotentReplayPart2.Request("key_a" , 4000, 6000);
        IdempotentReplayPart2.Request request5 = new IdempotentReplayPart2.Request("" , 8000, 4000);

        List<IdempotentReplayPart2.Request> input = new ArrayList<>();
        input.add(request1);
        input.add(request2);
        input.add(request3);
        input.add(request4);
        input.add(request5);

        List<Integer> output = IdempotentReplayPart2.inputRequest(input, 3600);
        System.out.println(output);

    }
}
