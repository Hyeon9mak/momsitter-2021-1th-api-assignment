package com.momsitter.assignment.service;

import com.momsitter.assignment.authorization.JwtTokenProvider;
import com.momsitter.assignment.controller.request.LoginRequest;
import com.momsitter.assignment.controller.response.LoginResponse;
import com.momsitter.assignment.domain.Id;
import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.domain.Password;
import com.momsitter.assignment.exception.LoginFailedException;
import com.momsitter.assignment.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse login(LoginRequest request) {
        Id id = new Id(request.getId());
        Password password = new Password(request.getPassword());

        Member member = memberRepository.findByIdAndPassword(id, password)
            .orElseThrow(() -> new LoginFailedException("로그인에 실패했습니다."));

        return new LoginResponse(jwtTokenProvider.createToken(member.getId()));
    }
}
