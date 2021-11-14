package com.practice.tdd.Mock.member;

import com.practice.tdd.Mock.domain.Member;
import com.practice.tdd.Mock.domain.Study;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);

}
