package com.demo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchedResponse {
    private Integer totalRecords;
    private Integer matchingRecords;
    private Integer unMatchedRecords;
    private List<UnMatchedReportResponse> unMatchedReportResponse;

}
