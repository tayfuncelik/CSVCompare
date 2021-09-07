package com.demo.controller;

import com.demo.constants.ErrorCodes;
import com.demo.exception.InvalidCSVException;
import com.demo.response.TransactionResponse;
import com.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.demo.predicates.TransactionPredicates.isCSVFormat;


/**
 * Financial reconciliation API
 */
@RequestMapping("/api")
@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/compare")
    public ResponseEntity<TransactionResponse> compareCSVTransactionRecords(@RequestPart("firstCSV") MultipartFile firstCSV,
                                                                            @RequestPart("secondCSV") MultipartFile secondCSV) throws Exception {
        if (!isCSVFormat.test(firstCSV) || !isCSVFormat.test(secondCSV))
            throw new InvalidCSVException(ErrorCodes.INVALID_CSV);

        return new ResponseEntity<>(transactionService.compareCVSRecords(firstCSV, secondCSV), HttpStatus.OK);
    }
}
