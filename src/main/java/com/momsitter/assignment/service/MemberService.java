package com.momsitter.assignment.service;

import com.momsitter.assignment.controller.request.CreateSitterRequest;
import com.momsitter.assignment.controller.response.MemberResponse;
import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.domain.Sitter;
import com.momsitter.assignment.repository.MemberRepository;
import com.momsitter.assignment.repository.SitterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final SitterRepository sitterRepository;

    public MemberService(
        MemberRepository memberRepository,
        SitterRepository sitterRepository
    ) {
        this.memberRepository = memberRepository;
        this.sitterRepository = sitterRepository;
    }

    @Transactional
    public MemberResponse createSitter(CreateSitterRequest request) {
        Member member = memberRepository.save(request.toMember());
        Sitter sitter = sitterRepository.save(request.toSitter());
        sitter.join(member);

        return MemberResponse.from(member, sitter);
    }
}
