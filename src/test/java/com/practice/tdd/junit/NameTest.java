package com.practice.tdd.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // 전체 적용
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 클래스마다 인스턴스 생성, 즉 아래 테스트는 하나의 클래스 인스턴스를 공유한다.
class NameTest {

    @BeforeAll
    //static void // static일 필요없다
    void beforeAll() {
        System.out.println("beforeAll");
    }

    @Order(1)
    @Test
    void create_name() {
        Name name = new Name();

        assertNotNull(name);
        assertEquals(NameStatus.DRAFT, name.getStatus(), "default:: DRAFT");
    }

    @Order(0)
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

    @Order(2)
    @Test
    @DisplayName("테스트 이름 짓기")
    void create() {
        System.out.println("create");
    }

}