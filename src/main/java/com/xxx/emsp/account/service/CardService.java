package com.xxx.emsp.account.service;

import com.xxx.emsp.account.dto.CardDTO;
import com.xxx.emsp.account.enums.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface CardService {

    CardDTO createCard(CardDTO dto);

    CardDTO assignCard(Long cardId, Long accountId);

    CardDTO updateCardStatus(Long id, CardStatus status);

    Page<CardDTO> findCardsByLastUpdated(LocalDateTime dateTime, Pageable pageable);
}
