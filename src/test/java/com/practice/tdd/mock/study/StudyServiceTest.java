package com.practice.tdd.mock.study;

import com.practice.tdd.mock.domain.Member;
import com.practice.tdd.mock.domain.Study;
import com.practice.tdd.mock.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyOne() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();

        member.setId(1L);
        member.setEmail("myunghoonju@gmail.com");

        //when -> a method has a return type.
        when(memberService.findById(any())).thenReturn(Optional.of(member));
        Study study = new Study(10, "java");
        Optional<Member> member1 = memberService.findById(1L); // 2, 3, 4... etc..,
        assertEquals("myunghoonju@gmail.com", member1.get().getEmail());

        studyService.createNewStudy(1L, study);

        //exception test
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });


    }

    @Test
    void createStudyTwo() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();

        member.setId(1L);
        member.setEmail("myunghoonju@gmail.com");

        //multiple call
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        //.thenReturn(Optional.of(member))
        Optional<Member> member2 = memberService.findById(1L);
        assertEquals("myunghoonju@gmail.com", member2.get().getEmail());

        //.thenThrow(new RuntimeException())
        assertThrows(RuntimeException.class, () -> {
            memberService.findById(1L);
        });

        //.thenReturn(Optional.empty());
        assertEquals(Optional.empty(), memberService.findById(1L));
    }

    @Test
    void createStudyThree() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        Member member = new Member();
        member.setId(1L);
        member.setEmail("myunghoonju@gmail.com");

        Study study = new Study(10, "java");

        when(memberService.findById(any())).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        //how many times called?
        verify(memberService, times(1)).notify(study);
        verify(memberService, never()).validate(any());

        //is method called in expected order?
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);
    }
}