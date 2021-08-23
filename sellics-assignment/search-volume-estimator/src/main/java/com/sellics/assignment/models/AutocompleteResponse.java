package com.sellics.assignment.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AutocompleteResponse {

    private String alias;
    private String prefix;
    private String suffix;
    private List<Suggestion> suggestions;
    private String suggestionTitleId;
    private String responseId;
    private boolean shuffled;
}
