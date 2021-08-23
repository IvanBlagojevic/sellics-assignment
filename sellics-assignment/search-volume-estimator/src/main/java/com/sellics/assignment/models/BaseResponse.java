package com.sellics.assignment.models;

import lombok.Getter;

@Getter
public class BaseResponse {

    private String keyword;

    public BaseResponse(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
