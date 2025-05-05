package com.xxx.emsp.account.repository;

import com.xxx.emsp.account.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByContractId(String contractId);
    Page<Card> findByLastUpdatedGreaterThanEqual(LocalDateTime dateTime, Pageable pageable);
}