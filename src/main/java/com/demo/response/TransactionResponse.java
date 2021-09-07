package com.demo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private MatchedResponse matchedResponseFile1;
    private MatchedResponse matchedResponseFile2;

}
