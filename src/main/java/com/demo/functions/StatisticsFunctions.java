package com.demo.functions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatisticsFunctions {

    public static final Function<String, Double> toDoubleValue = Double::valueOf;
    public static final Function<String, Long> toLongValue = Long::valueOf;
    public static final Function<String, Integer> toIntegerValue = Integer::valueOf;
    public static final Function<String, LocalDateTime> toLocalDateTime = date ->
            LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
}
