package com.momsitter.assignment.service;

import com.momsitter.assignment.authorization.AuthMemberDto;
import com.momsitter.assignment.controller.request.ChildInfoRequest;
import com.momsitter.assignment.controller.request.CreateParentRequest;
import com.momsitter.assignment.controller.request.CreateSitterRequest;
import com.momsitter.assignment.controller.request.ParentInfoRequest;
import com.momsitter.assignment.controller.request.SitterInfoRequest;
import com.momsitter.assignment.controller.response.MemberResponse;
import com.momsitter.assignment.controller.response.ParentResponse;
import com.momsitter.assignment.controller.response.SitterResponse;
import com.momsitter.assignment.domain.Child;
import com.momsitter.assignment.domain.Member;
import com.momsitter.assignment.domain.Parent;
import com.momsitter.assignment.domain.Sitter;
import com.momsitter.assignment.exception.AlreadyParentMemberException;
import com.momsitter.assignment.exception.AlreadySitterMemberException;
import com.momsitter.assignment.exception.MemberNotFoundException;
import com.momsitter.assignment.repository.ChildRepository;
import com.momsitter.assignment.repository.MemberRepository;
import com.momsitter.assignment.repository.ParentRepository;
import com.momsitter.assignment.repository.SitterRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final SitterRepository sitterRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;

    public MemberService(
        MemberRepository memberRepository,
        SitterRepository sitterRepository,
        ParentRepository parentRepository,
        ChildRepository childRepository
    ) {
        this.memberRepository = memberRepository;
        this.sitterRepository = sitterRepository;
        this.parentRepository = parentRepository;
        this.childRepository = childRepository;
    }

    @Transactional
    public SitterResponse createMemberAndAddSitterRole(CreateSitterRequest request) {
        Member member = memberRepository.save(request.toMember());

        return createAndAddSitterRole(member.getNumber(), request.getSitterInfo());
    }

    @Transactional
    public SitterResponse createAndAddSitterRole(Long memberNumber, SitterInfoRequest request) {
        Member member = findByNumber(memberNumber);
        validateAlreadySitterMember(member);
        Sitter sitter = sitterRepository.save(new Sitter(
            member,
            request.getMinCareAge(),
            request.getMaxCareAge(),
            request.getIntroduction()
        ));

        return SitterResponse.from(sitter);
    }

    private void validateAlreadySitterMember(Member member) {
        if (sitterRepository.findByMember(member).isPresent()) {
            throw new AlreadySitterMemberException(
                String.format("%s 사용자는 이미 시터로 등록되어 있습니다.", member.getNumber())
            );
        }
    }

    @Transactional
    public ParentResponse createMemberAndAddParentRole(CreateParentRequest request) {
        Member member = memberRepository.save(request.toMember());

        return createAndAddParentRole(member.getNumber(), request.getParentInfo());
    }

    @Transactional
    public ParentResponse createAndAddParentRole(Long memberNumber, ParentInfoRequest request) {
        Member member = findByNumber(memberNumber);
        validateAlreadyParentMember(member);
        Parent parent = parentRepository.save(new Parent(member, request.getRequestInfo()));
        List<Child> children = childRepository.saveAll(parseChildren(parent, request.getChildInfos()));

        return ParentResponse.from(parent, children);
    }

    private void validateAlreadyParentMember(Member member) {
        if (parentRepository.findByMember(member).isPresent()) {
            throw new AlreadyParentMemberException(
                String.format("%s 사용자는 이미 부모로 등록되어 있습니다.", member.getNumber())
            );
        }
    }

    private List<Child> parseChildren(Parent parent, List<ChildInfoRequest> childInfos) {
        List<Child> children = new ArrayList<>();

        for (ChildInfoRequest childInfo : childInfos) {
            LocalDate dateOfBirth = childInfo.getDateOfBirth();
            String gender = childInfo.getGender();
            children.add(new Child(parent, dateOfBirth, gender));
        }

        return children;
    }

    public MemberResponse findMemberInfo(AuthMemberDto authMember) {
        return MemberResponse.from(findByNumber(authMember.getNumber()));
    }

    private Member findByNumber(Long number) {
        return memberRepository.findByNumber(number)
            .orElseThrow(() -> new MemberNotFoundException(String.format("%s 에 해당하는 회원이 존재하지 않습니다.", number)));
    }
}
