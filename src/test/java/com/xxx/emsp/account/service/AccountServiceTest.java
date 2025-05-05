package com.xxx.emsp.account.service;

import com.xxx.emsp.account.dto.AccountDTO;
import com.xxx.emsp.account.enums.AccountStatus;
import com.xxx.emsp.account.model.Account;
import com.xxx.emsp.account.repository.AccountRepository;
import com.xxx.emsp.account.service.impl.AccountServiceImpl;
import com.xxx.emsp.account.util.mapper.AccountMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void createAccount_ShouldReturnAccountDTO_WhenValidInput() {
        String contractId = "AB123456789abc";
        AccountDTO inputDTO = AccountDTO.builder()
                .email("test@example.com")
                .contractId(contractId)
                .status(AccountStatus.CREATED)
                .build();

        Account account = Account.builder()
                .id(1L)
                .email("test@example.com")
                .contractId(contractId)
                .status(AccountStatus.CREATED)
                .build();

        AccountDTO expectedDTO = AccountDTO.builder()
                .id(1L)
                .email("test@example.com")
                .contractId(contractId)
                .status(AccountStatus.CREATED)
                .build();

        when(accountMapper.toEntity(inputDTO)).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountMapper.toDto(account)).thenReturn(expectedDTO);

        AccountDTO result = accountService.createAccount(inputDTO);

        assertNotNull(result);
        assertEquals(expectedDTO.getId(), result.getId());
        assertEquals(expectedDTO.getEmail(), result.getEmail());
        assertEquals(expectedDTO.getContractId(), result.getContractId());
        assertEquals(expectedDTO.getStatus(), result.getStatus());
        verify(accountRepository).save(any(Account.class));
    }


    @Test
    void updateAccountStatus_ShouldReturnUpdatedAccountDTO_WhenAccountExists() {
        Long accountId = 1L;
        AccountStatus newStatus = AccountStatus.ACTIVATED;

        Account existingAccount = Account.builder()
                .id(accountId)
                .email("test@example.com")
                .contractId("CONT001")
                .status(AccountStatus.CREATED)
                .build();

        Account updatedAccount = Account.builder()
                .id(accountId)
                .email("test@example.com")
                .contractId("CONT001")
                .status(newStatus)
                .build();

        AccountDTO expectedDTO = AccountDTO.builder()
                .id(accountId)
                .email("test@example.com")
                .contractId("CONT001")
                .status(newStatus)
                .build();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(updatedAccount);
        when(accountMapper.toDto(updatedAccount)).thenReturn(expectedDTO);

        AccountDTO result = accountService.updateAccountStatus(accountId, newStatus);

        assertNotNull(result);
        assertEquals(expectedDTO.getId(), result.getId());
        assertEquals(expectedDTO.getStatus(), newStatus);
        verify(accountRepository).save(any(Account.class));
    }

}