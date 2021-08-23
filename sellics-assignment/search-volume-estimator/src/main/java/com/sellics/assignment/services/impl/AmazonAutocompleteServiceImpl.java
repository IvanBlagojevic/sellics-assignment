package com.sellics.assignment.services.impl;

import com.sellics.assignment.models.AutocompleteResponse;
import com.sellics.assignment.services.AmazonAutocompleteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.sellics.assignment.utils.AutocompleteUtil.getAutocompleteErrorMessage;
import static com.sellics.assignment.utils.AutocompleteUtil.isAutocompleteSuccess;

@Service
@Slf4j
public class AmazonAutocompleteServiceImpl implements AmazonAutocompleteService {

    private final RestTemplate restTemplate;

    @Value("${amazon.autocomplete.url}")
    private String autocompleteUrl;

    @Autowired
    public AmazonAutocompleteServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Executes Amazon autocomplete API call for given subWord
     *
     * @param subWord
     * @return List of autocomplete results
     */
    @Override
    public List<String> executeAutocompleteRequest(String subWord) {
        String url = String.format(autocompleteUrl, subWord);
        ResponseEntity<AutocompleteResponse> response = restTemplate.getForEntity(url, AutocompleteResponse.class);
        List<String> values = new ArrayList<>();
        if (isAutocompleteSuccess(response)) {
            response.getBody().getSuggestions().forEach(s -> values.add(s.getValue()));
            log.info("Autocomplete results for sub word {}: {}", subWord, values);
            return values;
        } else {
            throw new RuntimeException(getAutocompleteErrorMessage(subWord, response.getStatusCode()));
        }
    }
}
