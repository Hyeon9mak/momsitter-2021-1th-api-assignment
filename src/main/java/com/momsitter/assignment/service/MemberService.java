package com.momsitter.assignment.service;

import com.momsitter.assignment.controller.request.CreateParentRequest;
import com.momsitter.assignment.controller.request.CreateSitterRequest;
import com.momsitter.assignment.controller.request.ParentInfoRequest;
import com.momsitter.assignment.controller.request.SitterInfoRequest;
import com.momsitter.assignment.controller.response.MemberResponse;
import com.momsitter.assignment.controller.response.ParentResponse;
import com.momsitter.assignment.controller.response.SitterResponse;
import com.momsitter.assignment.domain.Email;
import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.domain.Parent;
import com.momsitter.assignment.exception.AlreadyExistEmailException;
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
    public SitterResponse createMemberAndAddSitterRole(CreateSitterRequest request) {
        Member member = request.toMember();
        validateAlreadyExistEmail(member.getEmail());

        member.addRole(request.toSitter());

        return SitterResponse.from(memberRepository.save(member));
    }

    @Transactional
    public SitterResponse createAndAddSitterRole(Long memberNumber, SitterInfoRequest request) {
        Member member = findByNumber(memberNumber);

        member.addRole(request.toSitter());

        return SitterResponse.from(member);
    }

    @Transactional
    public ParentResponse createMemberAndAddParentRole(CreateParentRequest request) {
        Member member = request.toMember();
        validateAlreadyExistEmail(member.getEmail());

        Parent parent = request.toParent();
        parent.addChildren(request.toChildren());
        member.addRole(parent);

        return ParentResponse.from(memberRepository.save(member));
    }

    @Transactional
    public ParentResponse createAndAddParentRole(Long memberNumber, ParentInfoRequest request) {
        Member member = findByNumber(memberNumber);

        Parent parent = request.toParent();
        parent.addChildren(request.toChildren());
        member.addRole(parent);

        return ParentResponse.from(member);
    }

    private void validateAlreadyExistEmail(Email email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new AlreadyExistEmailException(
                String.format("%s 이메일이 이미 존재합니다.", email.getValue())
            );
        }
    }

    // TODO: 테스트코드 작성
    public MemberResponse findMemberInfo(Long memberNumber) {
        Member member = findByNumber(memberNumber);

        if (member.isSitter() && member.isNotParent()) {
            return MemberResponse.sitter(member);
        }

        if (member.isNotSitter() && member.isParent()) {
            return MemberResponse.parent(member);
        }

        return MemberResponse.all(member);
    }

    private Member findByNumber(Long number) {
        return memberRepository.findByNumber(number)
            .orElseThrow(() -> new MemberNotFoundException(
                String.format("%s 에 해당하는 회원이 존재하지 않습니다.", number)
            ));
    }
}
