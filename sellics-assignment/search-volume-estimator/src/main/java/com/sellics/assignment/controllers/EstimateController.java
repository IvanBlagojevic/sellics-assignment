package com.sellics.assignment.controllers;

import com.sellics.assignment.models.BaseResponse;
import com.sellics.assignment.models.ErrorResponse;
import com.sellics.assignment.models.SuccessResponse;
import com.sellics.assignment.services.ScoreCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EstimateController {

    private ScoreCalculateService scoreCalculateService;

    @Autowired
    public EstimateController(ScoreCalculateService scoreCalculateService) {
        this.scoreCalculateService = scoreCalculateService;
    }

    @GetMapping("/estimate")
    public BaseResponse estimate(@RequestParam String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return new ErrorResponse(keyword, "Can not process empty keyword");
        }
        try {
            log.info("Keyword: {}", keyword);
            int score = getScoreCalculateService().calculateKeywordScore(keyword);
            return new SuccessResponse(keyword, score);
        } catch (Exception e) {
            return new ErrorResponse(keyword, e.getMessage());
        }
    }

    public ScoreCalculateService getScoreCalculateService() {
        return scoreCalculateService;
    }
}
