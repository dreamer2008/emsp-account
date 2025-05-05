package com.xxx.emsp.account.util.mapper;

import com.xxx.emsp.account.dto.AccountDTO;
import com.xxx.emsp.account.enums.AccountStatus;
import com.xxx.emsp.account.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class AccountMapperTest {

    @InjectMocks
    private AccountMapperImpl accountMapper;

    @Test
    void toDto_ShouldMapAccountToAccountDTO() {
        Account account = Account.builder()
                .id(1L)
                .email("test@example.com")
                .contractId("CONT001")
                .status(AccountStatus.CREATED)
                .build();

        AccountDTO result = accountMapper.toDto(account);

        assertNotNull(result);
        assertEquals(account.getId(), result.getId());
        assertEquals(account.getEmail(), result.getEmail());
        assertEquals(account.getContractId(), result.getContractId());
        assertEquals(account.getStatus(), result.getStatus());
    }

    @Test
    void toEntity_ShouldMapAccountDTOToAccount() {
        AccountDTO accountDTO = AccountDTO.builder()
                .id(1L)
                .email("test@example.com")
                .contractId("CONT001")
                .status(AccountStatus.CREATED)
                .build();

        Account result = accountMapper.toEntity(accountDTO);

        assertNotNull(result);
        assertEquals(accountDTO.getId(), result.getId());
        assertEquals(accountDTO.getEmail(), result.getEmail());
        assertEquals(accountDTO.getContractId(), result.getContractId());
        assertEquals(accountDTO.getStatus(), result.getStatus());
    }

    @Test
    void toDtos_ShouldMapSetOfAccountsToSetOfAccountDTOs() {
        Set<Account> accounts = Set.of(
                Account.builder()
                        .id(1L)
                        .email("test1@example.com")
                        .contractId("CONT001")
                        .status(AccountStatus.CREATED)
                        .build(),
                Account.builder()
                        .id(2L)
                        .email("test2@example.com")
                        .contractId("CONT002")
                        .status(AccountStatus.ACTIVATED)
                        .build()
        );

        Set<AccountDTO> result = accountMapper.toDtos(accounts);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void toEntities_ShouldMapSetOfAccountDTOsToSetOfAccounts() {
        Set<AccountDTO> accountDTOs = Set.of(
                AccountDTO.builder()
                        .id(1L)
                        .email("test1@example.com")
                        .contractId("CONT001")
                        .status(AccountStatus.CREATED)
                        .build(),
                AccountDTO.builder()
                        .id(2L)
                        .email("test2@example.com")
                        .contractId("CONT002")
                        .status(AccountStatus.ACTIVATED)
                        .build()
        );

        Set<Account> result = accountMapper.toEntities(accountDTOs);

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}