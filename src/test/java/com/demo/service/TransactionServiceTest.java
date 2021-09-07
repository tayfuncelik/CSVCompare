package com.demo.service;

import com.demo.response.TransactionResponse;
import com.demo.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void testCompareCVSRecords() throws IOException {

        InputStream inputStream1 = getClass().getClassLoader().getResourceAsStream("clientmarkofffile20140113.csv");
        MockMultipartFile multipartFile = new MockMultipartFile("firstCSV",
                "clientmarkofffile20140113.csv", "text/csv", inputStream1);

        InputStream inputStream2 = getClass().getClassLoader().getResourceAsStream("tutukamarkofffile20140113.csv");
        MockMultipartFile multipartFile2 = new MockMultipartFile("secondCSV",
                "tutukamarkofffile20140113.csv", "text/csv", inputStream2);

        TransactionResponse response = transactionService.compareCVSRecords(multipartFile, multipartFile2);
        Assertions.assertEquals(response.getMatchedResponseFile1().getTotalRecords(), 306);
        Assertions.assertEquals(response.getMatchedResponseFile1().getMatchingRecords(), 288);
        Assertions.assertEquals(response.getMatchedResponseFile1().getUnMatchedRecords(), 18);

        Assertions.assertEquals(response.getMatchedResponseFile2().getTotalRecords(), 305);
        Assertions.assertEquals(response.getMatchedResponseFile2().getMatchingRecords(), 288);
        Assertions.assertEquals(response.getMatchedResponseFile2().getUnMatchedRecords(), 17);
    }

    @Test
    void testCompareCVSRecordsReverseOrder() throws IOException {

        InputStream inputStream1 = getClass().getClassLoader().getResourceAsStream("tutukamarkofffile20140113.csv");
        MockMultipartFile multipartFile = new MockMultipartFile("firstCSV",
                "tutukamarkofffile20140113.csv", "text/csv", inputStream1);

        InputStream inputStream2 = getClass().getClassLoader().getResourceAsStream("clientmarkofffile20140113.csv");
        MockMultipartFile multipartFile2 = new MockMultipartFile("secondCSV",
                "clientmarkofffile20140113.csv", "text/csv", inputStream2);

        TransactionResponse response = transactionService.compareCVSRecords(multipartFile, multipartFile2);
        Assertions.assertEquals(response.getMatchedResponseFile2().getTotalRecords(), 306);
        Assertions.assertEquals(response.getMatchedResponseFile2().getMatchingRecords(), 288);
        Assertions.assertEquals(response.getMatchedResponseFile2().getUnMatchedRecords(), 18);

        Assertions.assertEquals(response.getMatchedResponseFile1().getTotalRecords(), 305);
        Assertions.assertEquals(response.getMatchedResponseFile1().getMatchingRecords(), 288);
        Assertions.assertEquals(response.getMatchedResponseFile1().getUnMatchedRecords(), 17);
    }

}
