package com.practice.phonepe.interview.service;

import com.practice.phonepe.interview.entities.Contestant;
import com.practice.phonepe.interview.entities.Difficulty;
import com.practice.phonepe.interview.entities.Problem;
import com.practice.phonepe.interview.exception.AlreadySolvedException;
import com.practice.phonepe.interview.exception.EntityNotFoundException;
import com.practice.phonepe.interview.repository.ContestantRepository;
import com.practice.phonepe.interview.repository.InMemoryContestantRepository;
import com.practice.phonepe.interview.repository.InMemoryProblemRepository;
import com.practice.phonepe.interview.repository.InMemorySubmissionRepository;
import com.practice.phonepe.interview.repository.ProblemRepository;
import com.practice.phonepe.interview.repository.SubmissionRepository;
import com.practice.phonepe.interview.strategy.FixedScoreStrategy;
import com.practice.phonepe.interview.strategy.SimilarTagRecommendationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContestServiceTest {

    private SubmissionRepository submissionRepository;
    private ContestantRepository contestantRepository;
    private ContestService contestService;

    private Problem twoSum;
    private Problem mergeIntervals;
    private Contestant aayushi;
    private Contestant riya;

    @BeforeEach
    void setUp() {
        ProblemRepository problemRepository = new InMemoryProblemRepository();
        contestantRepository = new InMemoryContestantRepository();
        submissionRepository = new InMemorySubmissionRepository();
        contestService = new ContestService(
                contestantRepository, problemRepository, submissionRepository,
                new FixedScoreStrategy(), new SimilarTagRecommendationStrategy());

        twoSum = new Problem("Two Sum", Set.of("array", "hashing"), Difficulty.EASY, 100);
        mergeIntervals = new Problem("Merge Intervals", Set.of("array", "sorting"), Difficulty.MEDIUM, 200);
        problemRepository.save(twoSum);
        problemRepository.save(mergeIntervals);

        aayushi = new Contestant("Aayushi", "Backend"); // registered first, matters for the tie-break test
        riya = new Contestant("Riya", "Frontend");
        contestantRepository.save(aayushi);
        contestantRepository.save(riya);
    }

    @Test
    void shouldAwardScoreAndRecordSubmissionWhenSolving() {
        SolveResult result = contestService.solve(aayushi.getId(), twoSum.getId(), 200);

        assertEquals(100, result.getScoreAwarded());
        assertEquals(1, submissionRepository.findByContestant(aayushi.getId()).size());
    }

    @Test
    void shouldRecommendOnlyUnsolvedProblemsAfterSolving() {
        SolveResult result = contestService.solve(aayushi.getId(), twoSum.getId(), 200);

        assertEquals(List.of(mergeIntervals), result.getRecommendedProblems());
    }

    @Test
    void shouldThrowWhenContestantDoesNotExist() {
        assertThrows(EntityNotFoundException.class,
                () -> contestService.solve("unknown-contestant", twoSum.getId(), 100));
    }

    @Test
    void shouldThrowWhenProblemDoesNotExist() {
        assertThrows(EntityNotFoundException.class,
                () -> contestService.solve(aayushi.getId(), "unknown-problem", 100));
    }

    @Test
    void shouldThrowWhenContestantSolvesTheSameProblemTwice() {
        contestService.solve(aayushi.getId(), twoSum.getId(), 200);

        assertThrows(AlreadySolvedException.class,
                () -> contestService.solve(aayushi.getId(), twoSum.getId(), 150));
    }

    @Test
    void shouldReturnEmptyLeaderWhenNoSubmissionsExist() {
        assertTrue(contestService.getLeader().isEmpty());
    }

    @Test
    void shouldReturnContestantWithHighestTotalScoreAsLeader() {
        contestService.solve(aayushi.getId(), twoSum.getId(), 200);
        contestService.solve(aayushi.getId(), mergeIntervals.getId(), 200); // aayushi: 300 total
        contestService.solve(riya.getId(), twoSum.getId(), 200);           // riya: 100 total

        Optional<Contestant> leader = contestService.getLeader();

        assertTrue(leader.isPresent());
        assertEquals(aayushi.getId(), leader.get().getId());
    }

    @Test
    void shouldBreakTiedScoresByEarlierRegistration() {
        contestService.solve(aayushi.getId(), twoSum.getId(), 200);
        contestService.solve(riya.getId(), twoSum.getId(), 200); // same score, aayushi registered first

        Optional<Contestant> leader = contestService.getLeader();

        assertEquals(aayushi.getId(), leader.get().getId());
    }
}
