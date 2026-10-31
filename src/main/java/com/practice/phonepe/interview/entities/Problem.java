package com.practice.phonepe.interview.entities;

import java.util.Set;
import java.util.UUID;

public class Problem {

    private final String id;
    private final String description;
    private final Set<String> tags;
    private final Difficulty difficulty;
    private final int baseScore;

    public Problem(String description, Set<String> tags, Difficulty difficulty, int baseScore) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.tags = tags;
        this.difficulty = difficulty;
        this.baseScore = baseScore;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getTags() {
        return tags;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getBaseScore() {
        return baseScore;
    }
}
