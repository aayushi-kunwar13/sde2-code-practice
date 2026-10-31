package com.practice.phonepe.interview;

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
import com.practice.phonepe.interview.service.ContestService;
import com.practice.phonepe.interview.service.ContestantService;
import com.practice.phonepe.interview.service.ProblemService;
import com.practice.phonepe.interview.service.ProblemStats;
import com.practice.phonepe.interview.service.SolveResult;
import com.practice.phonepe.interview.strategy.FixedScoreStrategy;
import com.practice.phonepe.interview.strategy.RecommendationStrategy;
import com.practice.phonepe.interview.strategy.ScoringStrategy;
import com.practice.phonepe.interview.strategy.SimilarTagRecommendationStrategy;
import com.practice.phonepe.interview.strategy.TimeAdjustedScoreStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

// Each section below is labelled with the exact requirement it answers, in the same order
// the PDF lists them, so the two can be read side by side.
public class HackathonPlatformDemo {

    public static void main(String[] args) {
        ProblemRepository problemRepository = new InMemoryProblemRepository();
        ContestantRepository contestantRepository = new InMemoryContestantRepository();
        SubmissionRepository submissionRepository = new InMemorySubmissionRepository();

        ScoringStrategy scoringStrategy = new TimeAdjustedScoreStrategy(300); // 5 min fast-solve threshold
        RecommendationStrategy recommendationStrategy = new SimilarTagRecommendationStrategy();

        ProblemService problemService = new ProblemService(problemRepository, submissionRepository);
        ContestantService contestantService = new ContestantService(contestantRepository, submissionRepository, problemRepository);
        ContestService contestService = new ContestService(
                contestantRepository, problemRepository, submissionRepository, scoringStrategy, recommendationStrategy);

        requirement(1, "add problems to the Questions Library");
        Problem twoSum = new Problem("Two Sum", Set.of("array", "hashing"), Difficulty.EASY, 100);
        Problem mergeIntervals = new Problem("Merge Intervals", Set.of("array", "sorting"), Difficulty.MEDIUM, 200);
        Problem lruCache = new Problem("LRU Cache", Set.of("design", "hashing"), Difficulty.MEDIUM, 250);
        Problem wordLadder = new Problem("Word Ladder", Set.of("graph", "bfs"), Difficulty.HARD, 400);
        Problem medianOfArrays = new Problem("Median of Two Sorted Arrays", Set.of("array", "binary-search"), Difficulty.HARD, 450);
        Problem validParentheses = new Problem("Valid Parentheses", Set.of("stack"), Difficulty.EASY, 80);
        List.of(twoSum, mergeIntervals, lruCache, wordLadder, medianOfArrays, validParentheses)
                .forEach(problemService::addProblem);
        System.out.println(problemRepository.findAll().size() + " problems added");

        requirement(2, "contestants register with their name and department");
        Contestant aayushi = contestantService.addContestant("Aayushi", "Backend");
        Contestant riya = contestantService.addContestant("Riya", "Frontend");
        Contestant karan = contestantService.addContestant("Karan", "Backend");
        System.out.println(aayushi.getName() + " (" + aayushi.getDepartment() + ") registered");
        System.out.println(riya.getName() + " (" + riya.getDepartment() + ") registered");
        System.out.println(karan.getName() + " (" + karan.getDepartment() + ") registered");

        requirement(3, "a problem carries description, tag(s), difficulty, score");
        printProblems(List.of(twoSum));

        requirement(4, "filter by difficulty or tag, sort by score (extensible via Predicate/Comparator)");
        System.out.println("filter difficulty=EASY, sort by score ascending:");
        printProblems(problemService.fetchProblems(
                problem -> problem.getDifficulty() == Difficulty.EASY,
                Comparator.comparingInt(Problem::getBaseScore)));
        System.out.println("filter tag=array, sort by score descending:");
        printProblems(problemService.fetchProblems(
                problem -> problem.getTags().contains("array"),
                Comparator.comparingInt(Problem::getBaseScore).reversed()));

        requirement(5, "solve a problem, then list problems solved by that contestant");
        List<SolveResult> solves = new ArrayList<>();
        solves.add(solveAndPrint(contestService, aayushi, twoSum, 200));
        solves.add(solveAndPrint(contestService, aayushi, mergeIntervals, 900));
        solves.add(solveAndPrint(contestService, riya, twoSum, 700));
        solves.add(solveAndPrint(contestService, karan, lruCache, 300));
        solves.add(solveAndPrint(contestService, aayushi, validParentheses, 100));
        System.out.println("problems solved by Aayushi:");
        printProblems(contestantService.fetchSolvedProblems(aayushi.getId()));

        requirement(6, "number of users who solved a problem + average time taken");
        ProblemStats twoSumStats = problemService.getProblemStats(twoSum.getId());
        System.out.println("Two Sum -> solvedBy=" + twoSumStats.getSolveCount()
                + " users, avgTime=" + twoSumStats.getAverageTimeTakenSeconds() + "s");

        requirement(7, "scoring strategy is pluggable: fixed score, or score adjusted by time");
        ScoringStrategy fixed = new FixedScoreStrategy();
        int slowSolveTime = 900;
        System.out.println("Merge Intervals solved in " + slowSolveTime + "s:");
        System.out.println("  FixedScoreStrategy       -> " + fixed.calculateScore(mergeIntervals, slowSolveTime));
        System.out.println("  TimeAdjustedScoreStrategy -> " + scoringStrategy.calculateScore(mergeIntervals, slowSolveTime));

        requirement(8, "return the current leader of the contest");
        Optional<Contestant> leader = contestService.getLeader();
        leader.ifPresent(c -> System.out.println(c.getName() + " (" + c.getDepartment() + ") is leading"));

        requirement(9, "top N liked problems of a given tag (\"liked\" = solve count)");
        System.out.println("top 10 tagged 'array' (only 3 exist, so all 3 come back):");
        printProblems(problemService.getTopNProblems("array", 10));

        extension("on solving a problem, return top 5 recommended problems by similar tags");
        for (SolveResult solve : solves) {
            if (!solve.getRecommendedProblems().isEmpty()) {
                System.out.println("  recommended: " + describe(solve.getRecommendedProblems()));
            }
        }

        note("exceptions handled gracefully, errors printed to the console");
        try {
            contestService.solve("no-such-contestant", twoSum.getId(), 100);
        } catch (EntityNotFoundException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        try {
            contestService.solve(aayushi.getId(), "no-such-problem", 100);
        } catch (EntityNotFoundException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        try {
            contestService.solve(aayushi.getId(), twoSum.getId(), 150); // already solved above
        } catch (AlreadySolvedException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    private static SolveResult solveAndPrint(ContestService contestService, Contestant contestant, Problem problem, int timeTakenSeconds) {
        SolveResult result = contestService.solve(contestant.getId(), problem.getId(), timeTakenSeconds);
        System.out.println(contestant.getName() + " solved '" + problem.getDescription()
                + "' in " + timeTakenSeconds + "s -> scored " + result.getScoreAwarded());
        return result;
    }

    private static void requirement(int number, String description) {
        System.out.println();
        System.out.println("=== [Mandatory " + number + "] " + description + " ===");
    }

    private static void extension(String description) {
        System.out.println();
        System.out.println("=== [Extension] " + description + " ===");
    }

    private static void note(String description) {
        System.out.println();
        System.out.println("=== [" + description + "] ===");
    }

    private static void printProblems(List<Problem> problems) {
        if (problems.isEmpty()) {
            System.out.println("  (none)");
            return;
        }
        for (Problem problem : problems) {
            System.out.printf("  - %-28s tags=%-24s difficulty=%-6s score=%d%n",
                    problem.getDescription(), problem.getTags(), problem.getDifficulty(), problem.getBaseScore());
        }
    }

    private static String describe(List<Problem> problems) {
        StringBuilder builder = new StringBuilder();
        for (Problem problem : problems) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(problem.getDescription());
        }
        return builder.toString();
    }
}
