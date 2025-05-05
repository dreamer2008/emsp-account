package com.xxx.emsp.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.emsp.account.dto.AccountDTO;
import com.xxx.emsp.account.enums.AccountStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createAccount_ShouldReturn400_WhenMissingContractId() throws Exception {
        AccountDTO accountDTO = AccountDTO.builder()
                .email("test" + System.currentTimeMillis() + "@example.com")
                .status(AccountStatus.CREATED)
                .build();

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStatus_ShouldReturn404_WhenAccountNotFound() throws Exception {
        mockMvc.perform(patch("/api/v1/accounts/999/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(AccountStatus.ACTIVATED)))
                .andExpect(status().isNotFound());
    }


    @Test
    void searchByLastUpdated_ShouldReturn200_WhenValidRequest() throws Exception {
        String lastUpdated = LocalDateTime.now().minusDays(1).toString();

        mockMvc.perform(get("/api/v1/accounts/search")
                        .param("lastUpdated", lastUpdated)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }



}
