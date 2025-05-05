package com.xxx.emsp.account.repository;

import com.xxx.emsp.account.enums.AccountStatus;
import com.xxx.emsp.account.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void findByEmail_ShouldReturnAccount_WhenEmailExists() {
        // Given
        String emailPrefix = UUID.randomUUID().toString().substring(0, 10);
        String email = emailPrefix + "@example.com";
        Account account = Account.builder()
                .contractId("AB3CD1ABCDEFGH")
                .email(email)
                .status(AccountStatus.CREATED)
                .lastUpdated(LocalDateTime.now())
                .build();
        accountRepository.save(account);

        // When
        Optional<Account> result = accountRepository.findByEmail(email);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo(email);
    }

    @Test
    void findByEmail_ShouldReturnEmpty_WhenEmailNotExists() {
        // Given
        String nonExistentEmail = "nonexistent@example.com";

        // When
        Optional<Account> result = accountRepository.findByEmail(nonExistentEmail);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void findByLastUpdatedGreaterThanEqual_ShouldReturnMatchingAccounts() {
        // Given
        String emailPrefix = UUID.randomUUID().toString().substring(0, 10);
        String email1 = emailPrefix + "@example.com";
        emailPrefix = UUID.randomUUID().toString().substring(0, 10);
        String email2 = emailPrefix + "@example.com";
        LocalDateTime now = LocalDateTime.now();
        Account account1 = Account.builder()
                .contractId("AB3CD1ABCDEFGH")
                .email(email1)
                .status(AccountStatus.CREATED)
                .lastUpdated(now)
                .build();
        Account account2 = Account.builder()
                .contractId("AB-123-456789abc")
                .email(email2)
                .status(AccountStatus.ACTIVATED)
                .lastUpdated(now.plusHours(1))
                .build();
        accountRepository.save(account1);
        accountRepository.save(account2);

        // When
        Page<Account> result = accountRepository.findByLastUpdatedGreaterThanEqual(
                now,
                PageRequest.of(0, 10)
        );

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSizeGreaterThanOrEqualTo(2);
        assertThat(result.getContent()).extracting(Account::getEmail)
                .containsExactlyInAnyOrder(email1, email2);
    }

    @Test
    void findByLastUpdatedGreaterThanEqual_ShouldReturnEmpty_WhenNoMatchingAccounts() {
        // Given
        String emailPrefix = UUID.randomUUID().toString().substring(0, 10);
        String email = emailPrefix + "@example.com";
        LocalDateTime now = LocalDateTime.now();
        Account account = Account.builder()
                .contractId("AB3CD1ABCDEFGH")
                .email(email)
                .status(AccountStatus.CREATED)
                .lastUpdated(now)
                .build();
        accountRepository.save(account);

        // When
        Page<Account> result = accountRepository.findByLastUpdatedGreaterThanEqual(
                now.plusHours(1),
                PageRequest.of(0, 10)
        );

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    void save_ShouldPersistAccount() {
        // Given
        String emailPrefix = UUID.randomUUID().toString().substring(0, 10);
        String email = emailPrefix + "@example.com";
        Account account = Account.builder()
                .contractId("AB3CD1ABCDEFGH")
                .email(email)
                .status(AccountStatus.CREATED)
                .lastUpdated(LocalDateTime.now())
                .build();

        // When
        Account savedAccount = accountRepository.save(account);

        // Then
        assertThat(savedAccount.getId()).isNotNull();
        assertThat(savedAccount.getEmail()).isEqualTo(account.getEmail());
        assertThat(savedAccount.getStatus()).isEqualTo(account.getStatus());
        assertThat(savedAccount.getLastUpdated()).isNotNull();
    }

    @Test
    void delete_ShouldRemoveAccount() {
        // Given
        String emailPrefix = UUID.randomUUID().toString().substring(0, 10);
        String email = emailPrefix + "@example.com";
        Account account = Account.builder()
                .contractId("AB3CD1ABCDEFGH")
                .email(email)
                .status(AccountStatus.CREATED)
                .lastUpdated(LocalDateTime.now())
                .build();
        Account savedAccount = accountRepository.save(account);

        // When
        accountRepository.delete(savedAccount);

        // Then
        Optional<Account> result = accountRepository.findById(savedAccount.getId());
        assertThat(result).isEmpty();
    }
}