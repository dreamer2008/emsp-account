package com.xxx.emsp.account.service;

import com.xxx.emsp.account.dto.AccountDTO;
import com.xxx.emsp.account.enums.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface AccountService {

    AccountDTO createAccount(AccountDTO dto);

    AccountDTO updateAccountStatus(Long id, AccountStatus status);

    Page<AccountDTO> findAccountsByLastUpdated(LocalDateTime dateTime, Pageable pageable);
}
