package com.momsitter.assignment.request;

import com.momsitter.assignment.domain.Sitter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


public class SitterInfoRequest {

    @Min(value = 0, message = "케어 가능한 최소 연령은 음수일 수 없습니다.")
    private int minCareAge;

    @Min(value = 0, message = "케어 가능한 최대 연령은 음수일 수 없습니다.")
    private int maxCareAge;

    @NotBlank(message = "자기소개 정보는 Null 이거나 공백일 수 없습니다.")
    private String introduction;

    protected SitterInfoRequest() {
    }

    public SitterInfoRequest(int minCareAge, int maxCareAge, String introduction) {
        this.minCareAge = minCareAge;
        this.maxCareAge = maxCareAge;
        this.introduction = introduction;
    }

    public Sitter toSitter() {
        return new Sitter(minCareAge, maxCareAge, introduction);
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
