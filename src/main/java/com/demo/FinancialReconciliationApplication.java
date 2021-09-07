package com.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class FinancialReconciliationApplication {
    public static void main(String[] args) {
        log.info("Starting FinancialReconciliationApplication ...");
        SpringApplication.run(FinancialReconciliationApplication.class);
    }
}
