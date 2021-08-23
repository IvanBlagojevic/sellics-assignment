package com.sellics.assignment.utils;

import com.sellics.assignment.models.AutocompleteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import static java.lang.Double.valueOf;

public class AutocompleteUtil {

    /**
     * Calculates max score that some keyword can have.
     * Number 10  in for loop, represents number of results returned by autocomplete API.
     *
     * @param scoreIndex Length of given keyword without any space or blank character.
     * @return maxScore
     */
    public static double getMaxScore(int scoreIndex) {
        double maxScore = 0;
        for (int i = scoreIndex; i >= 0; i--) {
            maxScore = maxScore + (i * 10);
        }
        return maxScore;
    }


    public static int calculateScore(AtomicInteger autocompleteScore, int scoreIndex) {
        return valueOf(autocompleteScore.get() / getMaxScore(scoreIndex) * 100).intValue();
    }

    public static String getAutocompleteErrorMessage(String keyword, HttpStatus status) {
        return "Error on autocomplete request for keyword " + keyword + ". Status code: " + status.value();
    }

    public static boolean isAutocompleteSuccess(ResponseEntity<AutocompleteResponse> response) {
        return !Objects.isNull(response.getBody()) && response.getStatusCode().is2xxSuccessful();
    }

    public static int getScoreIndex(String keyword) {
        return keyword.replaceAll("\\s+", "").length();
    }

    /**
     * @param response
     * @param keyword
     * @return TRUE if result of Amazon autocomplete API call contains exact keyword.
     */
    public static boolean containsKeyword(String response, String keyword) {
        String pattern = "\\b" + keyword + "\\b";
        return Pattern.compile(pattern).matcher(response).find();
    }
}
