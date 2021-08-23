package com.sellics.assignment.models;

import lombok.Getter;

@Getter
public class ErrorResponse extends BaseResponse{

    private String errorMessage;

    public ErrorResponse(String keyword, String errorMessage) {
        super(keyword);
        this.errorMessage = errorMessage;
    }

}
