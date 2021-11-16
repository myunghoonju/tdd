package com.practice.tdd.mock.study;

import com.practice.tdd.mock.domain.Member;
import com.practice.tdd.mock.domain.Study;
import com.practice.tdd.mock.member.MemberService;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudyService {

    private final MemberService memberService;

    private final StudyRepository studyRepository;

    public StudyService(MemberService memberService, StudyRepository studyRepository) {
        this.memberService = memberService;
        this.studyRepository = studyRepository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);
        if (member.isPresent()) {
            study.setOwnerId(memberId);
        } else {
            throw new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'");
        }
        Study newstudy = studyRepository.save(study);
        memberService.notify(newstudy);
        memberService.notify(member.get());
        return newstudy;
    }

    public Study openStudy(Study study) {
        study.open();
        Study openedStudy = studyRepository.save(study);
        return openedStudy;
    }

}
