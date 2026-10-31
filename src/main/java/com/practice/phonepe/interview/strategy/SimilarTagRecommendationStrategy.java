package com.practice.phonepe.interview.strategy;

import com.practice.phonepe.interview.entities.Problem;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SimilarTagRecommendationStrategy implements RecommendationStrategy {

    @Override
    public List<Problem> recommend(Problem justSolved, List<Problem> candidates, int count) {
        return candidates.stream()
                .sorted(Comparator.comparingInt((Problem candidate) -> sharedTagCount(justSolved, candidate)).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    private int sharedTagCount(Problem a, Problem b) {
        Set<String> shared = new HashSet<>(a.getTags());
        shared.retainAll(b.getTags());
        return shared.size();
    }
}
