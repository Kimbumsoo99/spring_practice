package com.ssafy.memberPjt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicTest {

    @Test
    void test(){
        // given

        // when

        // then
    }

    @Test
    void assertAllTest() {
        assertAll(
                () -> assertEquals(3, 6 / 2),
                () -> assertEquals(4, 8 / 2) // assertAll은 모든 검증을 실행함
        );
    }
}
