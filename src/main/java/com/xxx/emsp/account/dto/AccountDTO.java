package com.xxx.emsp.account.dto;

import com.xxx.emsp.account.enums.AccountStatus;
import com.xxx.emsp.account.util.constants.AppConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class AccountDTO {
    private Long id;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = AppConstants.CONTRACT_ID_FORMAT, message = "Invalid Contract Id (EMAID) format")
    @NotBlank(message = "Contract ID is required")
    private String contractId;

    @NotNull(message = "Status is required")
    private AccountStatus status;

    private Set<CardDTO> cards;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdated;
}
