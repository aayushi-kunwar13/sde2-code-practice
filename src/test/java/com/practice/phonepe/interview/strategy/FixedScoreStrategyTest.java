package com.practice.phonepe.interview.strategy;

import com.practice.phonepe.interview.entities.Difficulty;
import com.practice.phonepe.interview.entities.Problem;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FixedScoreStrategyTest {

    private final FixedScoreStrategy strategy = new FixedScoreStrategy();

    @Test
    void shouldAlwaysReturnBaseScoreRegardlessOfTimeTaken() {
        Problem problem = new Problem("Two Sum", Set.of("array"), Difficulty.EASY, 100);

        assertEquals(100, strategy.calculateScore(problem, 10));
        assertEquals(100, strategy.calculateScore(problem, 10_000));
    }
}
