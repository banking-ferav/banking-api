package com.feraguiv.bankingservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feraguiv.bankingservice.model.dto.AccountRequestDTO;
import com.feraguiv.bankingservice.model.dto.AccountResponseDTO;
import java.util.List;

import com.feraguiv.bankingservice.service.AccountService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/accounts") //http://localhost:8080/api/v1/accounts/
@AllArgsConstructor
public class AccountController {

private final AccountService accountService;

@GetMapping

public ResponseEntity<List<AccountResponseDTO>> getAllAccounts(){

    List<AccountResponseDTO> accounts = accountService.getAllAccounts();
    return new ResponseEntity<>(accounts, HttpStatus.OK);

}

@GetMapping("/{id}") //http://localhost:8080/api/v1/accounts/{id}

public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable Long id){

    AccountResponseDTO account = accountService.getAccountById(id);
    return new ResponseEntity<>(account, HttpStatus.OK);
}

@PostMapping
public ResponseEntity<AccountResponseDTO> createdAccount(@Validated @RequestBody AccountRequestDTO accountRequestDTO){

    AccountResponseDTO createdAccount = accountService.createAccount(accountRequestDTO);
    return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
}

@PutMapping("/{id}")
public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long id, @Validated @RequestBody AccountRequestDTO accountRequestDTO){

    AccountResponseDTO updateAccount = accountService.updateAccount(id, accountRequestDTO);
    return new ResponseEntity<>(updateAccount, HttpStatus.OK);
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteAccount(@PathVariable Long id){

    accountService.deleteAccount(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}

}
