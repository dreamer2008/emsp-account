package com.xxx.emsp.account.service;

import com.xxx.emsp.account.dto.CardDTO;
import com.xxx.emsp.account.enums.CardStatus;
import com.xxx.emsp.account.exception.ResourceNotFoundException;
import com.xxx.emsp.account.model.Account;
import com.xxx.emsp.account.model.Card;
import com.xxx.emsp.account.repository.AccountRepository;
import com.xxx.emsp.account.repository.CardRepository;
import com.xxx.emsp.account.service.impl.CardServiceImpl;
import com.xxx.emsp.account.util.mapper.CardMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private CardMapper cardMapper;
    @InjectMocks
    private CardServiceImpl cardService;

    @Test
    void createCard_ShouldReturnCardDTO() {
        // Given
        CardDTO dto = CardDTO.builder()
                .contractId("DE*ABC*123456*7")
                .rfid(UUID.randomUUID().toString().replace("-", "").substring(0, 10))
                .status(CardStatus.CREATED)
                .build();
        Card card = Card.builder()
                .contractId("DE*ABC*123456*7")
                .status(CardStatus.CREATED)
                .build();

        when(cardMapper.toEntity(dto)).thenReturn(card);
        when(cardRepository.save(card)).thenReturn(card);
        when(cardMapper.toDto(card)).thenReturn(dto);

        // When
        CardDTO result = cardService.createCard(dto);

        // Then
        assertNotNull(result);
        assertEquals(dto.getStatus(), result.getStatus());
        verify(cardRepository).save(card);
    }

    @Test
    void assignCard_ShouldThrowException_WhenCardNotFound() {
        // Given
        Long cardId = 1L;
        Long accountId = 1L;
        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> {
            cardService.assignCard(cardId, accountId);
        });
    }

    @Test
    void assignCard_ShouldThrowException_WhenAccountNotFound() {
        // Given
        Long cardId = 1L;
        Long accountId = 1L;
        Card card = Card.builder().id(cardId).build();

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> {
            cardService.assignCard(cardId, accountId);
        });
        verify(cardRepository, never()).save(any());
    }

    @Test
    void assignCard_ShouldReturnUpdatedCard() {
        // Given
        Long cardId = 1L;
        Long accountId = 1L;
        Card card = Card.builder()
                .id(cardId)
                .status(CardStatus.CREATED)
                .build();
        Account account = Account.builder()
                .id(accountId)
                .build();
        CardDTO expectedDto = CardDTO.builder()
                .id(cardId)
                .status(CardStatus.ASSIGNED)
                .accountId(accountId)
                .build();

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(cardRepository.save(any(Card.class))).thenReturn(card);
        when(cardMapper.toDto(any(Card.class))).thenReturn(expectedDto);

        // When
        CardDTO result = cardService.assignCard(cardId, accountId);

        // Then
        assertNotNull(result);
        assertEquals(CardStatus.ASSIGNED, result.getStatus());
        assertEquals(accountId, result.getAccountId());
        verify(cardRepository).save(card);
    }

    @Test
    void updateCardStatus_ShouldThrowException_WhenCardNotFound() {
        // Given
        Long cardId = 1L;
        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> {
            cardService.updateCardStatus(cardId, CardStatus.ACTIVATED);
        });
        verify(cardRepository, never()).save(any());
    }

    @Test
    void updateCardStatus_ShouldReturnUpdatedCard() {
        // Given
        Long cardId = 1L;
        Card card = Card.builder()
                .id(cardId)
                .status(CardStatus.CREATED)
                .build();
        CardDTO expectedDto = CardDTO.builder()
                .id(cardId)
                .status(CardStatus.ACTIVATED)
                .build();

        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenReturn(card);
        when(cardMapper.toDto(any(Card.class))).thenReturn(expectedDto);

        // When
        CardDTO result = cardService.updateCardStatus(cardId, CardStatus.ACTIVATED);

        // Then
        assertNotNull(result);
        assertEquals(CardStatus.ACTIVATED, result.getStatus());
        verify(cardRepository).save(card);
    }

    @Test
    void findCardsByLastUpdated_ShouldReturnEmptyPage_WhenNoCardsFound() {
        // Given
        LocalDateTime dateTime = LocalDateTime.now();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Card> emptyPage = new PageImpl<>(Collections.emptyList());

        when(cardRepository.findByLastUpdatedGreaterThanEqual(dateTime, pageRequest))
                .thenReturn(emptyPage);

        // When
        Page<CardDTO> result = cardService.findCardsByLastUpdated(dateTime, pageRequest);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void findCardsByLastUpdated_ShouldReturnPageOfCards() {
        // Given
        LocalDateTime dateTime = LocalDateTime.now();
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Card> cards = Arrays.asList(
                Card.builder().id(1L).build(),
                Card.builder().id(2L).build()
        );
        Page<Card> cardPage = new PageImpl<>(cards, pageRequest, cards.size());

        List<CardDTO> cardDtos = Arrays.asList(
                CardDTO.builder().id(1L).build(),
                CardDTO.builder().id(2L).build()
        );

        when(cardRepository.findByLastUpdatedGreaterThanEqual(dateTime, pageRequest))
                .thenReturn(cardPage);
        when(cardMapper.toDto(any(Card.class)))
                .thenReturn(cardDtos.get(0), cardDtos.get(1));

        // When
        Page<CardDTO> result = cardService.findCardsByLastUpdated(dateTime, pageRequest);

        // Then
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        verify(cardMapper, times(2)).toDto(any(Card.class));
    }

}
