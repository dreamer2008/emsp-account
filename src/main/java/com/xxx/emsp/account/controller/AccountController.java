package com.xxx.emsp.account.controller;

import com.xxx.emsp.account.dto.AccountDTO;
import com.xxx.emsp.account.dto.api.ApiResponse;
import com.xxx.emsp.account.enums.AccountStatus;
import com.xxx.emsp.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Account", description = "Account management APIs")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @Operation(summary = "Create a new account")
    public ApiResponse<AccountDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        log.info("Creating account: {}", accountDTO);
        return ApiResponse.success(accountService.createAccount(accountDTO));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change account status")
    public ApiResponse<AccountDTO> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody AccountStatus status) {
        log.info("Updating account status: {}", status);
        return ApiResponse.success(accountService.updateAccountStatus(id, status));
    }

    @GetMapping("/search")
    @Operation(summary = "Search accounts by last updated date")
    public ApiResponse<Page<AccountDTO>> searchByLastUpdated(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastUpdated,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Searching accounts by last updated date: {}", lastUpdated);
        return ApiResponse.success(accountService.findAccountsByLastUpdated(lastUpdated,
                PageRequest.of(page, size)));
    }
}