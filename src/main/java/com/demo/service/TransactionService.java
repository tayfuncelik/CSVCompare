package com.demo.service;

import com.demo.response.TransactionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TransactionService {
    TransactionResponse compareCVSRecords(MultipartFile first, MultipartFile second) throws IOException;
}
