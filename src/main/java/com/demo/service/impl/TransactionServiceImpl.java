package com.demo.service.impl;

import com.demo.entity.Model;
import com.demo.response.MatchedResponse;
import com.demo.response.TransactionResponse;
import com.demo.response.UnMatchedReportResponse;
import com.demo.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.demo.functions.StatisticsFunctions.*;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Override
    public TransactionResponse compareCVSRecords(MultipartFile first, MultipartFile second) throws IOException {
        log.info("CSV files in process");
        List<CSVRecord> csvParsedRecords = readCSVFiles(first);
        List<CSVRecord> csvParsedRecords2 = readCSVFiles(second);

        List<Model> records1 = new ArrayList<>();
        fillRecordsAndMatchedItem(csvParsedRecords, records1);
        List<Model> records2 = new ArrayList<>();
        fillRecordsAndMatchedItem(csvParsedRecords2, records2);//check checkList and fill matchedItemList

        List<Model> matchedItems;
        if (records1.size() > records2.size())
            matchedItems = records2.stream().filter(i -> records1.contains(i)).collect(Collectors.toList());
        else
            matchedItems = records1.stream().filter(i -> records2.contains(i)).collect(Collectors.toList());

        TransactionResponse response = new TransactionResponse();
        response.setMatchedResponseFile1(prepareMatchedResponse(records1, matchedItems));
        response.setMatchedResponseFile2(prepareMatchedResponse(records2, matchedItems));
        return response;
    }

    private void fillRecordsAndMatchedItem(List<CSVRecord> csvParsedRecords, List<Model> records) {
        AtomicInteger count = new AtomicInteger();
        csvParsedRecords.forEach(i -> {
            if (count.getAndIncrement() != 0) {//get rid of header
                Model model = getModelData(i);
                records.add(model);
            }
        });
    }

    private Model getModelData(CSVRecord data) {

        return Model.builder()
                .profileName(data.get(0))
                .transactionDate(toLocalDateTime.apply(data.get(1)))
                .transactionAmount(toLongValue.apply(data.get(2)))
                .transactionNarrative(data.get(3))
                .transactionDescription(data.get(4))
                .transactionID(toLongValue.apply(data.get(5)))
                .transactionType(toIntegerValue.apply(data.get(6)))
                .walletReference(data.get(7))
                .build();
    }

    private List<CSVRecord> readCSVFiles(MultipartFile file) throws IOException {

        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
            return csvParser.getRecords();
        } catch (IOException ioEx) {
            log.error("IO Exception occurred");
            throw new IOException();
        }
    }

    private MatchedResponse prepareMatchedResponse(List<Model> records, List<Model> matchedItems) {
        MatchedResponse matchedResponse = new MatchedResponse();
        matchedResponse.setTotalRecords(records.size());
        matchedResponse.setMatchingRecords(matchedItems.size());
        matchedResponse.setUnMatchedRecords(records.size() - matchedItems.size());
        matchedResponse.setUnMatchedReportResponse(findNonMatchedList(records, matchedItems));
        return matchedResponse;
    }

    private List<UnMatchedReportResponse> findNonMatchedList(List<Model> records, List<Model> matchedItems) {
        return records.stream()
                .filter(i -> !matchedItems.contains(i))
                .map(i -> new UnMatchedReportResponse(i.getTransactionDate(), i.getWalletReference(), i.getTransactionAmount()))
                .collect(Collectors.toList());
    }

}
