package com.momsitter.assignment.service;

import com.momsitter.assignment.domain.Email;
import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.domain.Parent;
import com.momsitter.assignment.exception.AlreadyExistEmailException;
import com.momsitter.assignment.exception.MemberNotFoundException;
import com.momsitter.assignment.repository.MemberRepository;
import com.momsitter.assignment.request.CreateParentRequest;
import com.momsitter.assignment.request.CreateSitterRequest;
import com.momsitter.assignment.request.ParentInfoRequest;
import com.momsitter.assignment.request.SitterInfoRequest;
import com.momsitter.assignment.request.UpdateInfoRequest;
import com.momsitter.assignment.response.MemberResponse;
import com.momsitter.assignment.response.ParentResponse;
import com.momsitter.assignment.response.SitterResponse;
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
        Member member = findMemberByNumber(memberNumber);

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
        Member member = findMemberByNumber(memberNumber);

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

    public MemberResponse findMemberInfo(Long memberNumber) {
        Member member = findMemberByNumber(memberNumber);

        return getMemberResponseByRole(member);
    }

    public MemberResponse updateMemberInfo(Long memberNumber, UpdateInfoRequest request) {
        Member member = findMemberByNumber(memberNumber);
        member.update(request.toPassword(), request.toEmail());

        return getMemberResponseByRole(member);
    }

    private MemberResponse getMemberResponseByRole(Member member) {
        if (member.isSitter() && member.isNotParent()) {
            return MemberResponse.sitter(member);
        }

        if (member.isNotSitter() && member.isParent()) {
            return MemberResponse.parent(member);
        }

        return MemberResponse.all(member);
    }

    private Member findMemberByNumber(Long memberNumber) {
        return memberRepository.findByNumber(memberNumber)
            .orElseThrow(() -> new MemberNotFoundException(
                String.format("%s 에 해당하는 회원이 존재하지 않습니다.", memberNumber)
            ));
    }
}
