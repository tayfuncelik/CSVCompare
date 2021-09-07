package com.demo.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UnMatchedReportResponse {
    private LocalDateTime date;
    private String reference;
    private Long amount;
}
