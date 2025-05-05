package com.xxx.emsp.account.repository;

import com.xxx.emsp.account.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    Page<Account> findByLastUpdatedGreaterThanEqual(LocalDateTime dateTime, Pageable pageable);
}
