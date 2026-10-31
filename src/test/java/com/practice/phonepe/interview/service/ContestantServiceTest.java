package com.practice.phonepe.interview.service;

import com.practice.phonepe.interview.entities.Contestant;
import com.practice.phonepe.interview.entities.Difficulty;
import com.practice.phonepe.interview.entities.Problem;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContestantServiceTest {

    private ContestantService contestantService;
    private ContestService contestService;

    private Problem twoSum;

    @BeforeEach
    void setUp() {
        ContestantRepository contestantRepository = new InMemoryContestantRepository();
        ProblemRepository problemRepository = new InMemoryProblemRepository();
        SubmissionRepository submissionRepository = new InMemorySubmissionRepository();
        contestantService = new ContestantService(contestantRepository, submissionRepository, problemRepository);
        contestService = new ContestService(
                contestantRepository, problemRepository, submissionRepository,
                new FixedScoreStrategy(), new SimilarTagRecommendationStrategy());

        twoSum = new Problem("Two Sum", Set.of("array"), Difficulty.EASY, 100);
        problemRepository.save(twoSum);
    }

    @Test
    void shouldRegisterContestantWithNameAndDepartment() {
        Contestant contestant = contestantService.addContestant("Aayushi", "Backend");

        assertEquals("Aayushi", contestant.getName());
        assertEquals("Backend", contestant.getDepartment());
    }

    @Test
    void shouldReturnProblemsSolvedByContestant() {
        Contestant aayushi = contestantService.addContestant("Aayushi", "Backend");
        contestService.solve(aayushi.getId(), twoSum.getId(), 200);

        List<Problem> solved = contestantService.fetchSolvedProblems(aayushi.getId());

        assertEquals(List.of(twoSum), solved);
    }

    @Test
    void shouldThrowWhenFetchingSolvedProblemsForUnknownContestant() {
        assertThrows(EntityNotFoundException.class, () -> contestantService.fetchSolvedProblems("unknown"));
    }
}
