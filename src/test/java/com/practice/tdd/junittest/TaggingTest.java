package com.practice.tdd.junittest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaggingTest {

    @Test
    @Tag("fast")
    void create() {
        LessonOne lessonOne = new LessonOne();
        assertNotNull(lessonOne);
        System.out.println("createfast");
    }

    @Test
    @Tag("slow")
    void createOne() {
        System.out.println("createOne");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("beforeAll");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

}