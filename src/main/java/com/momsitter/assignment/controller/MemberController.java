package com.momsitter.assignment.controller;

import com.momsitter.assignment.authorization.AuthMember;
import com.momsitter.assignment.authorization.AuthMemberDto;
import com.momsitter.assignment.controller.request.CreateSitterRequest;
import com.momsitter.assignment.controller.response.MemberResponse;
import com.momsitter.assignment.service.MemberService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/sitter")
    public ResponseEntity<Void> createSitter(@Valid @RequestBody CreateSitterRequest request) {
        MemberResponse response = memberService.createSitter(request);

        return ResponseEntity.created(URI.create("/members/" + response.getNumber())).build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> findInfoOfMine(@AuthMember AuthMemberDto authMember) {
        MemberResponse response = memberService.findMemberInfo(authMember);

        return ResponseEntity.ok().body(response);
    }
}
