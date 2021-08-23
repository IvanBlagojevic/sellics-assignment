package com.sellics.assignment.services.impl;

import com.sellics.assignment.services.AmazonAutocompleteService;
import com.sellics.assignment.services.ScoreCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.sellics.assignment.utils.AutocompleteUtil.*;

@Service
@Slf4j
public class ScoreCalculateServiceImpl implements ScoreCalculateService {

    private AmazonAutocompleteService amazonAutocompleteService;


    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Autowired
    public ScoreCalculateServiceImpl(AmazonAutocompleteService amazonAutocompleteService) {
        this.amazonAutocompleteService = amazonAutocompleteService;
    }

    @Override
    public int calculateKeywordScore(String keyword) throws InterruptedException {
        AtomicInteger autocompleteScore = new AtomicInteger(0);
        int scoreIndex = getScoreIndex(keyword);
        executorService.invokeAll(getAmazonAutocompleteTasks(keyword, autocompleteScore, scoreIndex), 10, TimeUnit.SECONDS);
        int score = calculateScore(autocompleteScore, scoreIndex);
        log.info("Estimated score for keyword {} is {}", keyword, score);
        return score;
    }

    /**
     * @param keyword
     * @param autocompleteScore
     * @param scoreIndex
     * @return Set of callable tasks for executing Amazon autocomplete API call and processing results
     */
    private Set<Callable<Boolean>> getAmazonAutocompleteTasks(String keyword, AtomicInteger autocompleteScore, int scoreIndex) {
        Set<Callable<Boolean>> tasks = new HashSet<>();
        for (int i = 0; i <= keyword.length() - 1; i++) {
            String subWord = keyword.substring(0, i + 1);
            if (subWord.endsWith(" ")) {
                continue;
            }
            tasks.add(getAutocompleteTask(subWord, autocompleteScore, scoreIndex, keyword));
            scoreIndex--;
        }
        return tasks;
    }

    private Callable<Boolean> getAutocompleteTask(String subWord, AtomicInteger autocompleteScore, int scoreIndex, String keyword) {
        return () -> {
            try {
                calculateAmazonAutocompleteScore(subWord, autocompleteScore, scoreIndex, keyword);
                return Boolean.TRUE;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Boolean.FALSE;
            }
        };
    }

    /**
     * Calculates score for given subWord based on results of Amazon autocomplete API call
     *
     * @param subWord
     * @param autocompleteScore
     * @param scoreIndex
     * @param keyword
     */
    private void calculateAmazonAutocompleteScore(String subWord, AtomicInteger autocompleteScore, int scoreIndex, String keyword) {
        List<String> autocompleteResponses = amazonAutocompleteService.executeAutocompleteRequest(subWord);
        autocompleteResponses.stream().filter(r -> containsKeyword(r, keyword)).forEach(r -> autocompleteScore.addAndGet(scoreIndex));
    }
}
