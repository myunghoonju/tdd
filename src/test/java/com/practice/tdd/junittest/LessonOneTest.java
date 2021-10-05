package com.practice.tdd.junittest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class LessonOneTest {

    @Test
    void create() {
        LessonOne lessonOne = new LessonOne();
        assertNotNull(lessonOne);
        System.out.println("create");
    }

    @Test
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