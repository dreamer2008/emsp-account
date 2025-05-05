package com.xxx.emsp.account.controller;

import com.xxx.emsp.account.dto.AccountDTO;
import com.xxx.emsp.account.dto.api.ApiResponse;
import com.xxx.emsp.account.enums.AccountStatus;
import com.xxx.emsp.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerUnitTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Test
    void createAccount_ShouldReturnSuccessResponse() {
        long timestamp = System.currentTimeMillis();
        AccountDTO inputDTO = AccountDTO.builder()
                .email("test" + timestamp + "@example.com")
                .contractId("AB123456789abc")
                .status(AccountStatus.CREATED)
                .build();

        AccountDTO createdDTO = AccountDTO.builder()
                .id(1L)
                .email("test" + timestamp + "@example.com")
                .contractId("AB123456789abc")
                .status(AccountStatus.CREATED)
                .lastUpdated(LocalDateTime.now())
                .build();

        when(accountService.createAccount(inputDTO)).thenReturn(createdDTO);

        ApiResponse<AccountDTO> response = accountController.createAccount(inputDTO);

        assertNotNull(response);
        assertEquals(createdDTO, response.getData());
        verify(accountService).createAccount(inputDTO);
    }

    @Test
    void updateStatus_ShouldReturnSuccessResponse() {
        Long accountId = 1L;
        AccountStatus newStatus = AccountStatus.ACTIVATED;
        AccountDTO updatedDTO = AccountDTO.builder()
                .id(accountId)
                .email("test" + System.currentTimeMillis() + "@example.com")
                .contractId("AB123456789abc")
                .status(newStatus)
                .lastUpdated(LocalDateTime.now())
                .build();

        when(accountService.updateAccountStatus(accountId, newStatus)).thenReturn(updatedDTO);

        ApiResponse<AccountDTO> response = accountController.updateStatus(accountId, newStatus);

        assertNotNull(response);
        assertEquals(updatedDTO, response.getData());
        verify(accountService).updateAccountStatus(accountId, newStatus);
    }

    @Test
    void searchByLastUpdated_ShouldReturnSuccessResponse() {
        LocalDateTime lastUpdated = LocalDateTime.now().minusDays(1);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<AccountDTO> accountPage = new PageImpl<>(List.of(
                AccountDTO.builder()
                        .id(1L)
                        .email("test" + System.currentTimeMillis() + "@example.com")
                        .contractId("AB123456789abc")
                        .status(AccountStatus.ACTIVATED)
                        .build()
        ));

        when(accountService.findAccountsByLastUpdated(lastUpdated, pageRequest)).thenReturn(accountPage);

        ApiResponse<Page<AccountDTO>> response = accountController.searchByLastUpdated(lastUpdated, 0, 10);

        assertNotNull(response);
        assertEquals(accountPage, response.getData());
        verify(accountService).findAccountsByLastUpdated(lastUpdated, pageRequest);
    }
}