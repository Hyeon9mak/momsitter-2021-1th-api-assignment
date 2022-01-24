package com.momsitter.assignment.service;

import com.momsitter.assignment.authorization.AuthMemberDto;
import com.momsitter.assignment.authorization.AuthService;
import com.momsitter.assignment.authorization.AuthorizationException;
import com.momsitter.assignment.authorization.JwtTokenProvider;
import com.momsitter.assignment.domain.Id;
import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.domain.Password;
import com.momsitter.assignment.exception.LoginFailedException;
import com.momsitter.assignment.repository.MemberRepository;
import com.momsitter.assignment.request.LoginRequest;
import com.momsitter.assignment.response.LoginResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
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

    public AuthMemberDto findAuthMemberByToken(String token) {
        Id id = new Id(jwtTokenProvider.getPayload(token));

        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new AuthorizationException("로그인 정보가 유효하지 않습니다."));

        return new AuthMemberDto(member.getNumber());
    }
}
