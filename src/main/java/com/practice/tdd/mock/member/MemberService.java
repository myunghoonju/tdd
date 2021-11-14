package com.practice.tdd.mock.member;

import com.practice.tdd.mock.domain.Member;
import com.practice.tdd.mock.domain.Study;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);

}
