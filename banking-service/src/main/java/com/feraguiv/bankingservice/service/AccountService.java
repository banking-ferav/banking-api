package com.feraguiv.bankingservice.service;

import com.feraguiv.bankingservice.exception.ResourceNotFoundException;
import com.feraguiv.bankingservice.model.dto.AccountRequestDTO;
import com.feraguiv.bankingservice.model.dto.AccountResponseDTO;
import com.feraguiv.bankingservice.model.entity.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feraguiv.bankingservice.mapper.AccountMapper;
import com.feraguiv.bankingservice.repository.AccountRepository;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    // acciones CRUD
    
    // obtener todas las cuentas
    
    @Transactional(readOnly = true)
    public List<AccountResponseDTO> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        return accountMapper.convertToListDTO(accounts);
    }

    // buscar cuenta por id
    
    @Transactional(readOnly = true)
    public AccountResponseDTO getAccountById(Long id){

       Account account = accountRepository.findById(id)
       .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con el nro:" +id));

       return accountMapper.convertToDTO(account);

    }

    // crear cuenta 

    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO){
        Account account = accountMapper.convertToEntity(accountRequestDTO);
        account.setCreatedAt(LocalDate.now());
        accountRepository.save(account);
        return accountMapper.convertToDTO(account);
    }

    // actualizar cuenta

    @Transactional
    public AccountResponseDTO updateAccount(Long id, AccountRequestDTO accountRequestDTO){

        Account account = accountRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException
        ("Cuenta no encontrada con el nro:" +id));
        
        account.setAccountNumber(accountRequestDTO.getAccountNumber());
        account.setBalance(accountRequestDTO.getBalance());
        account.setOwnerName(accountRequestDTO.getOwnerName());
        account.setOwnerEmail(accountRequestDTO.getOwnerEmail());
        account.setUpdatedAt(LocalDate.now());

        account = accountRepository.save(account);
        return accountMapper.convertToDTO((account));
    }

    // eliminar cuenta

    @Transactional
    public void deleteAccount(Long id){

        accountRepository.deleteById(id);
    }

    }
