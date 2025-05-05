package com.xxx.emsp.account.repository;

import com.xxx.emsp.account.enums.CardStatus;
import com.xxx.emsp.account.model.Card;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CardRepositoryTest {

    @Autowired
    private CardRepository cardRepository;


    @Test
    void findByEmaid_ShouldReturnCard_WhenContractIdExists() {
        // Given
        String emaid = "AB1C2DE3FG4568";
        Card card = Card.builder()
                .contractId(emaid)
                .rfid("38525426")
                .status(CardStatus.CREATED)
                .build();
        cardRepository.save(card);

        // When
        Optional<Card> result = cardRepository.findByContractId(emaid);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getContractId()).isEqualTo(emaid);
    }

    @Test
    void findByEmaid_ShouldReturnEmpty_WhenContractIdNotExists() {
        // When
        Optional<Card> result = cardRepository.findByContractId("nonexistent");

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void findByLastUpdatedGreaterThanEqual_ShouldReturnMatchingCards() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Card card1 = Card.builder()
                .contractId("AB1C2DE3FG4568")
                .rfid("12345678")
                .status(CardStatus.CREATED)
                .lastUpdated(now)
                .build();
        Card card2 = Card.builder()
                .contractId("AB-123-456789abc")
                .rfid("38525426")
                .status(CardStatus.CREATED)
                .lastUpdated(now.plusHours(1))
                .build();
        cardRepository.save(card1);
        cardRepository.save(card2);

        // When
        Page<Card> result = cardRepository.findByLastUpdatedGreaterThanEqual(
                now.minusHours(1),
                PageRequest.of(0, 10)
        );

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
    }

    @Test
    void save_ShouldPersistCard() {
        // Given
        Card card = Card.builder()
                .contractId("AB-123-456789abc")
                .rfid("38525426")
                .status(CardStatus.CREATED)
                .build();

        // When
        Card savedCard = cardRepository.save(card);

        // Then
        assertThat(savedCard.getId()).isNotNull();
        assertThat(savedCard.getContractId()).isEqualTo(card.getContractId());
        assertThat(savedCard.getStatus()).isEqualTo(card.getStatus());
    }

    @Test
    void delete_ShouldRemoveCard() {
        // Given
        Card card = Card.builder()
                .contractId("AB-123-456789abc")
                .rfid("38525426")
                .status(CardStatus.CREATED)
                .build();
        Card savedCard = cardRepository.save(card);

        // When
        cardRepository.delete(savedCard);

        // Then
        Optional<Card> result = cardRepository.findById(savedCard.getId());
        assertThat(result).isEmpty();
    }
}