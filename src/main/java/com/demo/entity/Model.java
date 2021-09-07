package com.demo.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Model {

    private Long transactionID;
    private String profileName;
    private LocalDateTime transactionDate;
    private Long transactionAmount;
    private String transactionNarrative;
    private String transactionDescription;
    private int transactionType;
    private String walletReference;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Model model = (Model) o;

        if (transactionType != model.transactionType) return false;
        if (transactionID != null ? !transactionID.equals(model.transactionID) : model.transactionID != null)
            return false;
        if (profileName != null ? !profileName.equals(model.profileName) : model.profileName != null) return false;
        if (transactionDate != null ? !transactionDate.equals(model.transactionDate) : model.transactionDate != null)
            return false;
        if (transactionAmount != null ? !transactionAmount.equals(model.transactionAmount) : model.transactionAmount != null)
            return false;
        if (transactionNarrative != null ? !transactionNarrative.equals(model.transactionNarrative) : model.transactionNarrative != null)
            return false;
        if (transactionDescription != null ? !transactionDescription.equals(model.transactionDescription) : model.transactionDescription != null)
            return false;
        return walletReference != null ? walletReference.equals(model.walletReference) : model.walletReference == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionID, profileName, transactionDate, transactionAmount, transactionNarrative, transactionDescription, transactionType, walletReference);
    }
}
