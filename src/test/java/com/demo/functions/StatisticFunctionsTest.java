package com.demo.functions;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.demo.functions.StatisticsFunctions.toDoubleValue;
import static com.demo.functions.StatisticsFunctions.toLongValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticFunctionsTest {
    @Test
    void testToDoubleValue() {
        Double expectedVal = 2.0;
        assertEquals(expectedVal, toDoubleValue.apply("2.0"));
    }

    @Test
    void testToLongValue() {
        Long expectedVal = 2L;
        assertEquals(expectedVal, toLongValue.apply("2"));
    }

    @Test
    void testToLocalDateTime() {
        LocalDateTime time = StatisticsFunctions.toLocalDateTime.apply("2021-09-08 10:10:10");
        assertEquals(LocalDate.of(2021, 9, 8), time.toLocalDate());
        assertEquals("10:10:10", time.toLocalTime().toString());
    }
}
