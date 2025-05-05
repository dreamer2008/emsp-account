package com.xxx.emsp.account.service.impl;

import com.xxx.emsp.account.dto.CardDTO;
import com.xxx.emsp.account.enums.CardStatus;
import com.xxx.emsp.account.exception.ResourceNotFoundException;
import com.xxx.emsp.account.model.Account;
import com.xxx.emsp.account.model.Card;
import com.xxx.emsp.account.repository.AccountRepository;
import com.xxx.emsp.account.repository.CardRepository;
import com.xxx.emsp.account.service.CardService;
import com.xxx.emsp.account.util.mapper.CardMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final CardMapper cardMapper;

    public CardServiceImpl(CardRepository cardRepository, AccountRepository accountRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
        this.cardMapper = cardMapper;
    }

    @Override
    public CardDTO createCard(CardDTO dto) {
        Card card = cardMapper.toEntity(dto);
        card.setStatus(CardStatus.CREATED);
        LocalDateTime now = LocalDateTime.now();
        card.setCreatedAt(now);
        card.setLastUpdated(now);
        return cardMapper.toDto(cardRepository.save(card));
    }

    @Override
    public CardDTO assignCard(Long cardId, Long accountId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new ResourceNotFoundException("Card with id = " + cardId + " not found"));
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account with id = " + accountId + " not found"));
        card.setAccount(account);
        card.setContractId(account.getContractId());
        card.setStatus(CardStatus.ASSIGNED);
        card.setLastUpdated(LocalDateTime.now());
        return cardMapper.toDto(cardRepository.save(card));
    }

    @Override
    public CardDTO updateCardStatus(Long id, CardStatus status) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card with id = " + id + " not found"));
        card.setStatus(status);
        card.setLastUpdated(LocalDateTime.now());
        return cardMapper.toDto(cardRepository.save(card));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CardDTO> findCardsByLastUpdated(LocalDateTime dateTime, Pageable pageable) {
        return cardRepository.findByLastUpdatedGreaterThanEqual(dateTime, pageable).map(cardMapper::toDto);
    }
}
