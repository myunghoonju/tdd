package com.practice.tdd.mock.study;

import com.practice.tdd.mock.domain.Member;
import com.practice.tdd.mock.domain.Study;
import com.practice.tdd.mock.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

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

    @Test
    void bddTest() {
        // given
        StudyService studyService = new StudyService(memberService, studyRepository);
        Member member = new Member();
        member.setId(1L);
        member.setEmail("myunghoonju@gmail.com");

        Study study = new Study(10, "java");

       /* when(memberService.findById(any())).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);*/
        given(memberService.findById(any())).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        //when
        studyService.createNewStudy(1L, study);

        //then
        assertEquals(member.getId(), study.getOwnerId());
        then(memberService).should(times(1)).notify(study);
    }

    @Test
    void spyingList() {
        List<String> list = new ArrayList<>();
        List<String> spyList = spy(list);

        spyList.add("one");
        spyList.add("two");

        verify(spyList).add("one");
        verify(spyList).add("two");

        assertThat(spyList.size()).isEqualTo(2);
    }

    @Test
    void spyingList2() {
        List<String> list = new ArrayList<>();
        List<String> spyList = spy(list);

        assertThat(spyList.size()).isEqualTo(0);

        doReturn(2).when(spyList).size();
        assertThat(spyList.size()).isEqualTo(2);
    }

    @Test
    void spyingTest3() {
        List<String> spyList = spy(new ArrayList<>());
        doReturn(2).when(spyList).size();

        assertThat(spyList.size()).isGreaterThanOrEqualTo(2);
    }
}