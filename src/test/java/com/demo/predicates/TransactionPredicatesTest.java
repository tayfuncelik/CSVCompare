package com.demo.predicates;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import static com.demo.predicates.TransactionPredicates.isCSVFormat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransactionPredicatesTest {
    @Test
    void testIsCSVFileForValidFile() {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getContentType()).thenReturn("application/vnd.ms-excel");
        assertTrue(isCSVFormat.test(multipartFile));

        when(multipartFile.getContentType()).thenReturn("text/csv");
        assertTrue(isCSVFormat.test(multipartFile));
    }
}
