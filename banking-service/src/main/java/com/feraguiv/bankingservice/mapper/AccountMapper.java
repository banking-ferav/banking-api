package com.feraguiv.bankingservice.mapper;

import com.feraguiv.bankingservice.model.dto.AccountRequestDTO;
import com.feraguiv.bankingservice.model.dto.AccountResponseDTO;
import com.feraguiv.bankingservice.model.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class AccountMapper {

    private final ModelMapper modelMapper;

    public AccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Account convertToEntity(AccountRequestDTO accountRequestDTO){

        return modelMapper.map(accountRequestDTO, Account.class);
    }

    public AccountResponseDTO convertToDTO(Account account){

        return modelMapper.map(account, AccountResponseDTO.class);
    }

    public List<AccountResponseDTO> convertToDTO(List<Account> accounts){

        return accounts.stream()
                .map(this::convertToDTO)
                .toList();
    }

}
