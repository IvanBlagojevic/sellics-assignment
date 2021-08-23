package com.sellics.assignment.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Suggestion {

    private String suggType;
    private String type;
    private String value;
    private String refTag;
    private String candidateSources;
    private String strategyId;
    private int prior;
    private boolean ghost;
    private boolean help;
    private boolean fallback;
    private boolean blackListed;
    private boolean spellCorrected;
    private boolean xcatOnly;

}




