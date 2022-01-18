package com.momsitter.assignment.controller.request;

import static java.util.stream.Collectors.toList;

import com.momsitter.assignment.domain.Child;
import com.momsitter.assignment.domain.Parent;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ParentInfoRequest {

    @NotBlank(message = "신청정보는 Null 이거나 공백일 수 없습니다.")
    private String requestInfo;

    @NotNull(message = "아이 정보는 Null 일 수 없습니다.")
    private List<ChildInfoRequest> childInfos;

    protected ParentInfoRequest() {
    }

    public ParentInfoRequest(String requestInfo, List<ChildInfoRequest> childInfos) {
        this.requestInfo = requestInfo;
        this.childInfos = childInfos;
    }

    public Parent toParent() {
        return new Parent(requestInfo);
    }

    public List<Child> toChildren() {
        return childInfos.stream()
            .map(ChildInfoRequest::toChild)
            .collect(toList());
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public List<ChildInfoRequest> getChildInfos() {
        return childInfos;
    }
}
