package com.sellics.assignment.models;

import lombok.Getter;

@Getter
public class SuccessResponse extends BaseResponse {

    private int score;

    public SuccessResponse(String keyword, int score) {
        super(keyword);
        this.score = score;
    }
}
