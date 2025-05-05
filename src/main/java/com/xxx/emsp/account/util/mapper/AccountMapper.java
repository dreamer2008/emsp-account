package com.xxx.emsp.account.util.mapper;

import com.xxx.emsp.account.dto.AccountDTO;
import com.xxx.emsp.account.model.Account;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDTO toDto(Account account);
    Account toEntity(AccountDTO dto);
    Set<AccountDTO> toDtos(Set<Account> accounts);
    Set<Account> toEntities(Set<AccountDTO> dtos);
}