package com.xxx.emsp.account.controller;

import com.xxx.emsp.account.dto.CardDTO;
import com.xxx.emsp.account.dto.api.ApiResponse;
import com.xxx.emsp.account.enums.CardStatus;
import com.xxx.emsp.account.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/cards")
@Tag(name = "Card", description = "Card management APIs")
@Slf4j
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    @Operation(summary = "Create a new card")
    public ApiResponse<CardDTO> createCard(@Valid @RequestBody CardDTO cardDTO) {
        log.info("Creating card: {}", cardDTO);
        return ApiResponse.success(cardService.createCard(cardDTO));
    }

    @PatchMapping("/{id}/assign/{accountId}")
    @Operation(summary = "Assign card to account")
    public ApiResponse<CardDTO> assignCard(
            @PathVariable Long id,
            @PathVariable Long accountId) {
        log.info("Assigning card {} to account {}", id, accountId);
        return ApiResponse.success(cardService.assignCard(id, accountId));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change card status")
    public ApiResponse<CardDTO> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody CardStatus status) {
        log.info("Updating card {} status to {}", id, status);
        return ApiResponse.success(cardService.updateCardStatus(id, status));
    }

    @GetMapping("/search")
    @Operation(summary = "Search cards by last updated date")
    public ApiResponse<Page<CardDTO>> searchByLastUpdated(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastUpdated,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Searching cards by last updated date: {}", lastUpdated);
        return ApiResponse.success(cardService.findCardsByLastUpdated(lastUpdated,
                PageRequest.of(page, size)));
    }
}