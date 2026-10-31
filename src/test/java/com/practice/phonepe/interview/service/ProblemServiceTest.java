package com.practice.phonepe.interview.service;

import com.practice.phonepe.interview.entities.Difficulty;
import com.practice.phonepe.interview.entities.Problem;
import com.practice.phonepe.interview.entities.Submission;
import com.practice.phonepe.interview.exception.EntityNotFoundException;
import com.practice.phonepe.interview.repository.InMemoryProblemRepository;
import com.practice.phonepe.interview.repository.InMemorySubmissionRepository;
import com.practice.phonepe.interview.repository.ProblemRepository;
import com.practice.phonepe.interview.repository.SubmissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProblemServiceTest {

    private SubmissionRepository submissionRepository;
    private ProblemService problemService;

    private Problem twoSum;
    private Problem mergeIntervals;
    private Problem lruCache;

    @BeforeEach
    void setUp() {
        ProblemRepository problemRepository = new InMemoryProblemRepository();
        submissionRepository = new InMemorySubmissionRepository();
        problemService = new ProblemService(problemRepository, submissionRepository);

        twoSum = new Problem("Two Sum", Set.of("array", "hashing"), Difficulty.EASY, 100);
        mergeIntervals = new Problem("Merge Intervals", Set.of("array", "sorting"), Difficulty.MEDIUM, 200);
        lruCache = new Problem("LRU Cache", Set.of("design", "hashing"), Difficulty.MEDIUM, 250);
        problemService.addProblem(twoSum);
        problemService.addProblem(mergeIntervals);
        problemService.addProblem(lruCache);
    }

    @Test
    void shouldFilterAndSortProblems() {
        List<Problem> result = problemService.fetchProblems(
                problem -> problem.getTags().contains("hashing"),
                Comparator.comparingInt(Problem::getBaseScore));

        assertEquals(List.of(twoSum, lruCache), result);
    }

    @Test
    void shouldReturnZeroStatsWhenProblemHasNoSubmissions() {
        ProblemStats stats = problemService.getProblemStats(twoSum.getId());

        assertEquals(0, stats.getSolveCount());
        assertEquals(0, stats.getAverageTimeTakenSeconds());
    }

    @Test
    void shouldComputeSolveCountAndAverageTime() {
        submissionRepository.save(new Submission("contestant-1", twoSum.getId(), 200, 100));
        submissionRepository.save(new Submission("contestant-2", twoSum.getId(), 400, 100));

        ProblemStats stats = problemService.getProblemStats(twoSum.getId());

        assertEquals(2, stats.getSolveCount());
        assertEquals(300, stats.getAverageTimeTakenSeconds());
    }

    @Test
    void shouldThrowWhenFetchingStatsForUnknownProblem() {
        assertThrows(EntityNotFoundException.class, () -> problemService.getProblemStats("unknown"));
    }

    @Test
    void shouldRankTopNProblemsBySolveCountDescending() {
        submissionRepository.save(new Submission("contestant-1", twoSum.getId(), 200, 100));
        submissionRepository.save(new Submission("contestant-2", twoSum.getId(), 200, 100));
        submissionRepository.save(new Submission("contestant-1", mergeIntervals.getId(), 200, 200));

        List<Problem> topArrayProblems = problemService.getTopNProblems("array", 10);

        assertEquals(List.of(twoSum, mergeIntervals), topArrayProblems);
    }
}
