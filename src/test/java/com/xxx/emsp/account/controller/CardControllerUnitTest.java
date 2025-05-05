package com.xxx.emsp.account.controller;

import com.xxx.emsp.account.dto.CardDTO;
import com.xxx.emsp.account.dto.api.ApiResponse;
import com.xxx.emsp.account.enums.CardStatus;
import com.xxx.emsp.account.service.CardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardControllerUnitTest {

    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController;

    @Test
    void createCard_ShouldReturnSuccessResponse_WhenValidInput() {
        CardDTO inputDTO = CardDTO.builder()
                .contractId("DE-ABC-123456-7")
                .rfid("12345678")
                .status(CardStatus.CREATED)
                .build();

        CardDTO expectedDTO = CardDTO.builder()
                .id(1L)
                .contractId("DE-ABC-123456-7")
                .rfid("12345678")
                .status(CardStatus.CREATED)
                .lastUpdated(LocalDateTime.now())
                .build();

        when(cardService.createCard(inputDTO)).thenReturn(expectedDTO);

        ApiResponse<CardDTO> response = cardController.createCard(inputDTO);

        assertNotNull(response);
        assertEquals(expectedDTO, response.getData());
        verify(cardService).createCard(inputDTO);
    }

    @Test
    void assignCard_ShouldReturnSuccessResponse_WhenCardAndAccountExist() {
        Long cardId = 1L;
        Long accountId = 1L;
        CardDTO expectedDTO = CardDTO.builder()
                .id(cardId)
                .contractId("DE-ABC-123456-7")
                .rfid("12345678")
                .status(CardStatus.ASSIGNED)
                .accountId(accountId)
                .lastUpdated(LocalDateTime.now())
                .build();

        when(cardService.assignCard(cardId, accountId)).thenReturn(expectedDTO);

        ApiResponse<CardDTO> response = cardController.assignCard(cardId, accountId);

        assertNotNull(response);
        assertEquals(expectedDTO, response.getData());
        verify(cardService).assignCard(cardId, accountId);
    }

    @Test
    void updateStatus_ShouldReturnSuccessResponse_WhenCardExists() {
        Long cardId = 1L;
        CardStatus newStatus = CardStatus.ACTIVATED;
        CardDTO expectedDTO = CardDTO.builder()
                .id(cardId)
                .contractId("DE-ABC-123456-7")
                .rfid("12345678")
                .status(newStatus)
                .lastUpdated(LocalDateTime.now())
                .build();

        when(cardService.updateCardStatus(cardId, newStatus)).thenReturn(expectedDTO);

        ApiResponse<CardDTO> response = cardController.updateStatus(cardId, newStatus);

        assertNotNull(response);
        assertEquals(expectedDTO, response.getData());
        verify(cardService).updateCardStatus(cardId, newStatus);
    }
}