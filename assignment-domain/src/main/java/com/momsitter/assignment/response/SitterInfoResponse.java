package com.momsitter.assignment.response;

import com.momsitter.assignment.domain.Sitter;

public class SitterInfoResponse {

    private final Long number;
    private final int minCareAge;
    private final int maxCareAge;
    private final String introduction;

    public SitterInfoResponse(Long number, int minCareAge, int maxCareAge, String introduction) {
        this.number = number;
        this.minCareAge = minCareAge;
        this.maxCareAge = maxCareAge;
        this.introduction = introduction;
    }

    public static SitterInfoResponse from(Sitter sitter) {
        return new SitterInfoResponse(
            sitter.getNumber(),
            sitter.getMinCareAge(),
            sitter.getMaxCareAge(),
            sitter.getIntroduction()
        );
    }

    public Long getNumber() {
        return number;
    }

    public int getMinCareAge() {
        return minCareAge;
    }

    public int getMaxCareAge() {
        return maxCareAge;
    }

    public String getIntroduction() {
        return introduction;
    }
}
