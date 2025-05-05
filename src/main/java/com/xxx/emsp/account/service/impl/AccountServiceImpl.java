package com.xxx.emsp.account.service.impl;

import com.xxx.emsp.account.dto.AccountDTO;
import com.xxx.emsp.account.enums.AccountStatus;
import com.xxx.emsp.account.exception.ResourceNotFoundException;
import com.xxx.emsp.account.model.Account;
import com.xxx.emsp.account.repository.AccountRepository;
import com.xxx.emsp.account.service.AccountService;
import com.xxx.emsp.account.util.mapper.AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Transactional
    @Override
    public AccountDTO createAccount(AccountDTO dto) {
        Account account = accountMapper.toEntity(dto);
        account.setStatus(AccountStatus.CREATED);
        LocalDateTime now = LocalDateTime.now();
        account.setCreatedAt(now);
        account.setLastUpdated(now);
        return accountMapper.toDto(accountRepository.save(account));
    }

    @Transactional
    @Override
    public AccountDTO updateAccountStatus(Long id, AccountStatus status) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with id = " + id + " not found"));
        account.setStatus(status);
        account.setLastUpdated(LocalDateTime.now());
        return accountMapper.toDto(accountRepository.save(account));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AccountDTO> findAccountsByLastUpdated(LocalDateTime dateTime, Pageable pageable) {
        return accountRepository.findByLastUpdatedGreaterThanEqual(dateTime, pageable).map(accountMapper::toDto);
    }
}