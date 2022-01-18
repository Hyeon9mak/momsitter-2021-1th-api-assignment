package com.momsitter.assignment.controller;

import com.momsitter.assignment.authorization.AuthMember;
import com.momsitter.assignment.authorization.AuthMemberDto;
import com.momsitter.assignment.controller.request.CreateParentRequest;
import com.momsitter.assignment.controller.request.CreateSitterRequest;
import com.momsitter.assignment.controller.request.ParentInfoRequest;
import com.momsitter.assignment.controller.request.SitterInfoRequest;
import com.momsitter.assignment.controller.response.MemberResponse;
import com.momsitter.assignment.controller.response.ParentResponse;
import com.momsitter.assignment.controller.response.SitterResponse;
import com.momsitter.assignment.service.MemberService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/create-sitter")
    public ResponseEntity<Void> createSitter(@Valid @RequestBody CreateSitterRequest request) {
        SitterResponse response = memberService.createMemberAndAddSitterRole(request);

        return ResponseEntity.created(URI.create("/members/" + response.getNumber())).build();
    }

    @PutMapping("/add-sitter")
    public ResponseEntity<SitterResponse> addSitterRole(
        @AuthMember AuthMemberDto authMember,
        @Valid @RequestBody SitterInfoRequest request
    ) {
        SitterResponse response = memberService.createAndAddSitterRole(authMember.getNumber(), request);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/create-parent")
    public ResponseEntity<Void> createParent(@Valid @RequestBody CreateParentRequest request) {
        ParentResponse response = memberService.createMemberAndAddParentRole(request);

        return ResponseEntity.created(URI.create("/members/" + response.getNumber())).build();
    }

    @PutMapping("/add-parent")
    public ResponseEntity<ParentResponse> addParentRole(
        @AuthMember AuthMemberDto authMember,
        @Valid @RequestBody ParentInfoRequest request
    ) {
        ParentResponse response = memberService.createAndAddParentRole(authMember.getNumber(), request);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> findInfoOfMine(@AuthMember AuthMemberDto authMember) {
        MemberResponse response = memberService.findMemberInfo(authMember.getNumber());

        return ResponseEntity.ok().body(response);
    }
}
