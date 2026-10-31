package com.practice.phonepe.interview.strategy;

import com.practice.phonepe.interview.entities.Difficulty;
import com.practice.phonepe.interview.entities.Problem;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeAdjustedScoreStrategyTest {

    private final Problem problem = new Problem("Merge Intervals", Set.of("array"), Difficulty.MEDIUM, 200);
    private final TimeAdjustedScoreStrategy strategy = new TimeAdjustedScoreStrategy(300);

    @Test
    void shouldAwardFullScoreWhenSolvedAtOrBeforeThreshold() {
        assertEquals(200, strategy.calculateScore(problem, 300));
    }

    @Test
    void shouldApplyTenPercentPenaltyAfterOneFullIntervalPastThreshold() {
        assertEquals(180, strategy.calculateScore(problem, 600));
    }

    @Test
    void shouldApplyTwentyPercentPenaltyAfterTwoFullIntervalsPastThreshold() {
        assertEquals(160, strategy.calculateScore(problem, 900));
    }

    @Test
    void shouldFloorPenaltyAtFiftyPercentNoMatterHowLate() {
        assertEquals(100, strategy.calculateScore(problem, 3000));
    }
}
