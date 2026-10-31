package com.practice.phonepe.interview.strategy;

import com.practice.phonepe.interview.entities.Difficulty;
import com.practice.phonepe.interview.entities.Problem;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimilarTagRecommendationStrategyTest {

    private final SimilarTagRecommendationStrategy strategy = new SimilarTagRecommendationStrategy();

    @Test
    void shouldRankCandidatesByNumberOfSharedTagsDescending() {
        Problem justSolved = new Problem("Two Sum", Set.of("array", "hashing"), Difficulty.EASY, 100);
        Problem sharesTwoTags = new Problem("Similar", Set.of("array", "hashing"), Difficulty.EASY, 100);
        Problem sharesOneTag = new Problem("Somewhat similar", Set.of("array", "sorting"), Difficulty.MEDIUM, 200);
        Problem sharesNoTags = new Problem("Unrelated", Set.of("graph"), Difficulty.HARD, 400);

        List<Problem> result = strategy.recommend(justSolved, List.of(sharesNoTags, sharesOneTag, sharesTwoTags), 5);

        assertEquals(List.of(sharesTwoTags, sharesOneTag, sharesNoTags), result);
    }

    @Test
    void shouldRespectTheRequestedCount() {
        Problem justSolved = new Problem("Two Sum", Set.of("array"), Difficulty.EASY, 100);
        List<Problem> candidates = List.of(
                new Problem("A", Set.of("array"), Difficulty.EASY, 100),
                new Problem("B", Set.of("array"), Difficulty.EASY, 100),
                new Problem("C", Set.of("array"), Difficulty.EASY, 100));

        List<Problem> result = strategy.recommend(justSolved, candidates, 2);

        assertEquals(2, result.size());
    }
}
