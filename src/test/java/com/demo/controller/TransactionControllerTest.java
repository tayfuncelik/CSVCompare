package com.demo.controller;

import com.demo.exception.InvalidCSVException;
import com.demo.response.MatchedResponse;
import com.demo.response.TransactionResponse;
import com.demo.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void testCompareCSVTransactionRecords() throws Exception {

        MockMultipartFile mockMultipartFile = prepareFirst();
        MockMultipartFile mockMultipartFile2 = prepareSecond();

        //declare mocked response and excpect to see same result
        TransactionResponse response = new TransactionResponse();
        MatchedResponse matchedResponse = new MatchedResponse();
        matchedResponse.setTotalRecords(12);
        matchedResponse.setMatchingRecords(10);
        matchedResponse.setUnMatchedRecords(2);
        matchedResponse.setUnMatchedReportResponse(new ArrayList<>());
        response.setMatchedResponseFile1(matchedResponse);

        when(transactionService.compareCVSRecords(mockMultipartFile, mockMultipartFile2)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/compare")
                        .file(mockMultipartFile)
                        .file(mockMultipartFile2)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
//                .content(content))
                .andExpect(status().is(200))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchedResponseFile2").value(response.getMatchedResponseFile2()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchedResponseFile1.matchingRecords").value(response.getMatchedResponseFile1().getMatchingRecords()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchedResponseFile1.totalRecords").value(response.getMatchedResponseFile1().getTotalRecords()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchedResponseFile1.unMatchedRecords").value(response.getMatchedResponseFile1().getUnMatchedRecords()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchedResponseFile1.unMatchedReportResponse").value(response.getMatchedResponseFile1().getUnMatchedReportResponse()));
    }

    @Test
    void testReversedCompareCSVTransactionRecords() throws Exception {

        MockMultipartFile mockMultipartFile = prepareFirst();// here it is changed
        MockMultipartFile mockMultipartFile2 = prepareSecond();

        //declare mocked response and excpect to see same result
        TransactionResponse response = new TransactionResponse();
        MatchedResponse matchedResponse = new MatchedResponse();
        matchedResponse.setTotalRecords(12);
        matchedResponse.setMatchingRecords(10);
        matchedResponse.setUnMatchedRecords(2);
        matchedResponse.setUnMatchedReportResponse(new ArrayList<>());
        response.setMatchedResponseFile1(matchedResponse);

        MatchedResponse matchedResponse2 = new MatchedResponse();
        matchedResponse2.setTotalRecords(22);
        matchedResponse2.setMatchingRecords(11);
        matchedResponse2.setUnMatchedRecords(5);
        matchedResponse2.setUnMatchedReportResponse(new ArrayList<>());
        response.setMatchedResponseFile2(matchedResponse2);

        when(transactionService.compareCVSRecords(mockMultipartFile, mockMultipartFile2)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/compare")
                        .file(mockMultipartFile)
                        .file(mockMultipartFile2)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
//                .content(content))
                .andExpect(status().is(200))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchedResponseFile1.matchingRecords").value(response.getMatchedResponseFile1().getMatchingRecords()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchedResponseFile1.totalRecords").value(response.getMatchedResponseFile1().getTotalRecords()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchedResponseFile1.unMatchedRecords").value(response.getMatchedResponseFile1().getUnMatchedRecords()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchedResponseFile1.unMatchedReportResponse").value(response.getMatchedResponseFile1().getUnMatchedReportResponse()))

                .andExpect(MockMvcResultMatchers.jsonPath("$.matchedResponseFile2.matchingRecords").value(response.getMatchedResponseFile2().getMatchingRecords()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchedResponseFile2.totalRecords").value(response.getMatchedResponseFile2().getTotalRecords()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchedResponseFile2.unMatchedRecords").value(response.getMatchedResponseFile2().getUnMatchedRecords()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchedResponseFile2.unMatchedReportResponse").value(response.getMatchedResponseFile2().getUnMatchedReportResponse()));
    }

    private MockMultipartFile prepareFirst() throws IOException {
        InputStream inputStream1 = getClass().getClassLoader().getResourceAsStream("clientmarkofffile20140113.csv");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("firstCSV",
                "clientmarkofffile20140113.csv", "text/csv", inputStream1);
        return mockMultipartFile;
    }


    private MockMultipartFile prepareSecond() throws IOException {

        InputStream inputStream2 = getClass().getClassLoader().getResourceAsStream("tutukamarkofffile20140113.csv");
        MockMultipartFile mockMultipartFile2 = new MockMultipartFile("secondCSV",
                "tutukamarkofffile20140113.csv", "text/csv", inputStream2);
        return mockMultipartFile2;
    }

    @Test
    void testInvalidCSVException() throws Exception {
        MockMultipartFile mockMultipartFile = prepareFirst();
        MockMultipartFile mockMultipartFile2 = prepareSecond();

        when(transactionService.compareCVSRecords(mockMultipartFile, mockMultipartFile2)).thenThrow(InvalidCSVException.class);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/compare")
                        .file(mockMultipartFile)
                        .file(mockMultipartFile2)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidCSVException));
    }

    @Test
    void testIOException() throws Exception {
        MockMultipartFile mockMultipartFile = prepareFirst();
        MockMultipartFile mockMultipartFile2 = prepareSecond();

        when(transactionService.compareCVSRecords(mockMultipartFile, mockMultipartFile2)).thenThrow(IOException.class);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/compare")
                        .file(mockMultipartFile)
                        .file(mockMultipartFile2)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.EXPECTATION_FAILED.value()))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IOException));
    }

    @Test
    void testException() throws Exception {
        MockMultipartFile mockMultipartFile = prepareFirst();
        MockMultipartFile mockMultipartFile2 = prepareSecond();

        Mockito.when(transactionService.compareCVSRecords(mockMultipartFile, mockMultipartFile2)).thenThrow(NullPointerException.class);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/compare")
                        .file(mockMultipartFile)
                        .file(mockMultipartFile2)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception));
    }
}

