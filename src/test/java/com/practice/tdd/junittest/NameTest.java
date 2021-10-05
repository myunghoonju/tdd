package com.practice.tdd.junittest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // 전체 적용
class NameTest {

    @Test
    void create_name() {
        Name name = new Name();

        assertNotNull(name);
        assertEquals(NameStatus.DRAFT, name.getStatus(), "default:: DRAFT");
    }

    @Test
    void create_name_test() {
        Name name = new Name();

        assertAll(
                ()->assertNotNull(name),
                ()->assertEquals(NameStatus.DRAFT, name.getStatus(), "default:: DRAFT")
        );
        //assertNotNull(name);
        //assertEquals(NameStatus.DRAFT, name.getStatus(), "default:: DRAFT");
    }

    @Test
    @DisplayName("테스트 이름 짓기")
    void create() {
        System.out.println("create");
    }

}