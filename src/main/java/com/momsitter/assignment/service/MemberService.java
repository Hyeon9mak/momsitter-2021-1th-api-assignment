package com.momsitter.assignment.service;

import com.momsitter.assignment.authorization.AuthMemberDto;
import com.momsitter.assignment.controller.request.CreateParentRequest;
import com.momsitter.assignment.controller.request.CreateSitterRequest;
import com.momsitter.assignment.controller.response.MemberResponse;
import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.domain.Parent;
import com.momsitter.assignment.domain.Sitter;
import com.momsitter.assignment.exception.MemberNotFoundException;
import com.momsitter.assignment.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long createSitter(CreateSitterRequest request) {
        Member member = request.toMember();
        Sitter sitter = request.toSitter();

        member.getJob(sitter);

        return memberRepository.save(member).getNumber();
    }

    @Transactional
    public Long createParent(CreateParentRequest request) {
        Member member = request.toMember();
        Parent parent = request.toParent();

        member.registration(parent);

        return memberRepository.save(member).getNumber();
    }

    public MemberResponse findMemberInfo(AuthMemberDto authMember) {
        return MemberResponse.from(findByNumber(authMember.getNumber()));
    }

    private Member findByNumber(Long number) {
        return memberRepository.findByNumber(number)
            .orElseThrow(() -> new MemberNotFoundException(String.format("%s 에 해당하는 회원이 존재하지 않습니다.", number)));
    }
}
