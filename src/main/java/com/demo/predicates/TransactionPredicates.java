package com.demo.predicates;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionPredicates {
    private static final String CSV_TYPE = "text/csv";
    private static final String EXCEL_TYPE = "application/vnd.ms-excel";
    public static final Predicate<MultipartFile> isCSVFormat = file -> (CSV_TYPE.equals(file.getContentType()) || EXCEL_TYPE.equals(file.getContentType()));
}
