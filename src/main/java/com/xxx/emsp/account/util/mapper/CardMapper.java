package com.xxx.emsp.account.util.mapper;

import com.xxx.emsp.account.dto.CardDTO;
import com.xxx.emsp.account.model.Card;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CardMapper {
    CardDTO toDto(Card card);
    Card toEntity(CardDTO dto);
    Set<CardDTO> toDtos(Set<Card> cards);
    Set<Card> toEntities(Set<CardDTO> dtos);
}