package com.momsitter.assignment.response;

import com.momsitter.assignment.domain.Child;
import com.momsitter.assignment.domain.Parent;
import java.util.List;

public class ParentInfoResponse {

    private final Long number;
    private final String requestInfo;
    private final List<ChildInfoResponse> childInfos;

    public ParentInfoResponse(
        Long number,
        String requestInfo,
        List<ChildInfoResponse> childInfos
    ) {
        this.number = number;
        this.requestInfo = requestInfo;
        this.childInfos = childInfos;
    }

    public static ParentInfoResponse from(Parent parent, List<Child> children) {
        return new ParentInfoResponse(
            parent.getNumber(),
            parent.getRequestInfo(),
            ChildInfoResponse.of(children)
        );
    }

    public Long getNumber() {
        return number;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public List<ChildInfoResponse> getChildInfos() {
        return childInfos;
    }
}
