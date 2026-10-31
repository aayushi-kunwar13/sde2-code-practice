package com.practice.phonepe.interview.entities;

import java.time.Instant;

public class Submission {

    private final String contestantId;
    private final String problemId;
    private final int timeTakenSeconds;
    private final int scoreAwarded;
    private final Instant solvedAt;

    // scoreAwarded is captured at solve time rather than recomputed later, so a contestant's
    // total score stays correct even if the scoring strategy is swapped mid-contest.
    public Submission(String contestantId, String problemId, int timeTakenSeconds, int scoreAwarded) {
        this.contestantId = contestantId;
        this.problemId = problemId;
        this.timeTakenSeconds = timeTakenSeconds;
        this.scoreAwarded = scoreAwarded;
        this.solvedAt = Instant.now();
    }

    public String getContestantId() {
        return contestantId;
    }

    public String getProblemId() {
        return problemId;
    }

    public int getTimeTakenSeconds() {
        return timeTakenSeconds;
    }

    public int getScoreAwarded() {
        return scoreAwarded;
    }

    public Instant getSolvedAt() {
        return solvedAt;
    }
}
