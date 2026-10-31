package com.practice.phonepe.interview.strategy;

import com.practice.phonepe.interview.entities.Problem;

import java.util.List;

public interface RecommendationStrategy {

    // candidates is expected to already exclude justSolved and anything the contestant has
    // solved, so the strategy only has to rank, not filter.
    List<Problem> recommend(Problem justSolved, List<Problem> candidates, int count);
}
