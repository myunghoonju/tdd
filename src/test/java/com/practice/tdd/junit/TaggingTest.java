package com.practice.tdd.junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 클래스마다 인스턴스 생성, 즉 아래 테스트는 하나의 클래스 인스턴스를 공유한다.
class TaggingTest {

    @Test
    @FastTest
    void create() {
        LessonOne lessonOne = new LessonOne();
        assertNotNull(lessonOne);
        System.out.println("createfast");
    }

    @Test
    void createOne() {
        System.out.println("createOne");
    }

    @DisplayName("반복테스트")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("test:: " + repetitionInfo.getCurrentRepetition());
        System.out.println("rep toal:: " + repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("파라미터테스트")
    @ParameterizedTest(name = "{index} {displayName} message = {0}") // 첫번째 인자 String message
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요"})
    //@NullAndEmptySource //@EmptySource + @NullSource
    void parameterizedTest(String message) {
        System.out.println(message);
    }

    @DisplayName("파라미터테스트 2")
    @ParameterizedTest(name = "{index} {displayName} limit = {0}")
    @ValueSource(ints = {10, 20, 30})
    void parameterizedTestTwo(Integer limit) {
        System.out.println(limit);
    }

    @DisplayName("파라미터테스트 3")
    @ParameterizedTest(name = "{index} {displayName} limit = {0}")
    @CsvSource({"10, 'java'", "20, 'spring'"})
    void parameterizedTestThree(Integer limit, String name) {
        System.out.println(limit + name);
    }

    @DisplayName("파라미터테스트 4")
    @ParameterizedTest(name = "{index} {displayName} limit = {0}")
    @CsvSource({"10, 'java'", "20, 'spring'"})
    void parameterizedTestFour(Integer limit, String name) {
        Study study = new Study(limit, name);
        System.out.println(study.getLimit());
    }

    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object o, Class<?> aClass) throws ArgumentConversionException {
            assertEquals(Study.class, aClass, "convert to lessonOne");
            return new Study(Integer.parseInt(o.toString()));
        }
    }

    @DisplayName("파라미터테스트 5")
    @ParameterizedTest(name = "{index} {displayName} limit = {0}")
    @CsvSource({"10, 'java'", "20, 'spring'"})
    void parameterizedTestFive(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }

    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        }
    }


   /*
   @TestInstance(TestInstance.Lifecycle.PER_CLASS) // 클래스마다 인스턴스 생성, 즉 아래 테스트는 하나의 클래스 인스턴스를 공유한다.
   @BeforeAll
    static void beforeAll() { // static 필요없다.
        System.out.println("beforeAll");
    }*/

    @BeforeAll
    void beforeAll() {
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