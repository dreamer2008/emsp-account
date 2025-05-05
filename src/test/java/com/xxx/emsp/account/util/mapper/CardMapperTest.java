package com.xxx.emsp.account.util.mapper;

import com.xxx.emsp.account.dto.CardDTO;
import com.xxx.emsp.account.enums.CardStatus;
import com.xxx.emsp.account.model.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CardMapperTest {

    @InjectMocks
    private CardMapperImpl cardMapper;

    @Test
    void toDto_ShouldMapCardToCardDTO() {
        Card card = Card.builder()
                .id(1L)
                .contractId("DE*ABC*123456*7")
                .rfid("12345678")
                .status(CardStatus.CREATED)
                .build();

        CardDTO result = cardMapper.toDto(card);

        assertNotNull(result);
        assertEquals(card.getId(), result.getId());
        assertEquals(card.getContractId(), result.getContractId());
        assertEquals(card.getRfid(), result.getRfid());
        assertEquals(card.getStatus(), result.getStatus());
    }

    @Test
    void toEntity_ShouldMapCardDTOToCard() {
        CardDTO cardDTO = CardDTO.builder()
                .id(1L)
                .contractId("DE*ABC*123456*7")
                .rfid("12345678")
                .status(CardStatus.CREATED)
                .build();

        Card result = cardMapper.toEntity(cardDTO);

        assertNotNull(result);
        assertEquals(cardDTO.getId(), result.getId());
        assertEquals(cardDTO.getContractId(), result.getContractId());
        assertEquals(cardDTO.getRfid(), result.getRfid());
        assertEquals(cardDTO.getStatus(), result.getStatus());
    }

    @Test
    void toDtos_ShouldMapSetOfCardsToSetOfCardDTOs() {
        Set<Card> cards = Set.of(
                Card.builder()
                        .id(1L)
                        .contractId("DE*ABC*123456*7")
                        .rfid("12345678")
                        .status(CardStatus.CREATED)
                        .build(),
                Card.builder()
                        .id(2L)
                        .contractId("DE*ABC*123457*8")
                        .rfid("87654321")
                        .status(CardStatus.ACTIVATED)
                        .build()
        );

        Set<CardDTO> result = cardMapper.toDtos(cards);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void toEntities_ShouldMapSetOfCardDTOsToSetOfCards() {
        Set<CardDTO> cardDTOs = Set.of(
                CardDTO.builder()
                        .id(1L)
                        .contractId("DE*ABC*123456*7")
                        .rfid("12345678")
                        .status(CardStatus.CREATED)
                        .build(),
                CardDTO.builder()
                        .id(2L)
                        .contractId("DE*ABC*123457*8")
                        .rfid("87654321")
                        .status(CardStatus.ACTIVATED)
                        .build()
        );

        Set<Card> result = cardMapper.toEntities(cardDTOs);

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
