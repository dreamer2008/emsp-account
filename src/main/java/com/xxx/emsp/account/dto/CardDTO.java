package com.xxx.emsp.account.dto;

import com.xxx.emsp.account.enums.CardStatus;
import com.xxx.emsp.account.util.constants.AppConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CardDTO {
    private Long id;

    @Pattern(regexp = AppConstants.CONTRACT_ID_FORMAT, message = "Invalid Contract Id (EMAID) format")
    private String contractId;

    @NotNull(message = "Status is required")
    private CardStatus status;

    private String rfid;

    private Long accountId;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;
}