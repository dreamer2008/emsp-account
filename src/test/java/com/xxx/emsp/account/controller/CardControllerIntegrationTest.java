package com.xxx.emsp.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.emsp.account.dto.CardDTO;
import com.xxx.emsp.account.enums.CardStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCard_ShouldReturn400_WhenInvalidContractId() throws Exception {
        CardDTO cardDTO = CardDTO.builder()
                .contractId("invalid-format")
                .rfid("12345678")
                .status(CardStatus.CREATED)
                .build();

        mockMvc.perform(post("/api/v1/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCard_ShouldReturn400_WhenInvalidRfid() throws Exception {
        CardDTO cardDTO = CardDTO.builder()
                .contractId("DE-ABC-123456-7")
                .rfid("123")  // too short
                .status(CardStatus.CREATED)
                .build();

        mockMvc.perform(post("/api/v1/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void assignCard_ShouldReturn404_WhenAccountNotFound() throws Exception {
        mockMvc.perform(patch("/api/v1/cards/1/assign/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void searchByLastUpdated_ShouldReturnEmptyPage_WhenNoCards() throws Exception {
        LocalDateTime lastUpdated = LocalDateTime.now();

        mockMvc.perform(get("/api/v1/cards/search")
                        .param("lastUpdated", lastUpdated.toString())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").isEmpty())
                .andExpect(jsonPath("$.data.totalElements").value(0));
    }
}