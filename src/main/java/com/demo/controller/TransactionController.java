package com.demo.controller;

import com.demo.constants.ErrorCodes;
import com.demo.entity.Model;
import com.demo.exception.InvalidCSVException;
import com.demo.response.TransactionResponse;
import com.demo.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import static com.demo.predicates.TransactionPredicates.isCSVFormat;


/**
 * Financial reconciliation API
 */
@Slf4j
@RequestMapping("")
@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/compare")
    public ModelAndView compareCSVTransactionRecords(@RequestPart("firstCSV") MultipartFile firstCSV,
                                                     @RequestPart("secondCSV") MultipartFile secondCSV, Model model) throws Exception {
        if (!isCSVFormat.test(firstCSV) || !isCSVFormat.test(secondCSV))
            throw new InvalidCSVException(ErrorCodes.INVALID_CSV);
        log.info("compareCSVTransactionRecords");

        ModelAndView modelAndView = new ModelAndView();
        TransactionResponse response = transactionService.compareCVSRecords(firstCSV, secondCSV);
        modelAndView.addObject("firstCSV", null);
        modelAndView.addObject("response", response);

        modelAndView.setViewName("compare");
        return modelAndView;

    }
}
