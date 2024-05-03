package com.feraguiv.bankingservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feraguiv.bankingservice.model.dto.TransactionReportDTO;
import com.feraguiv.bankingservice.model.dto.TransactionRequestDTO;
import com.feraguiv.bankingservice.model.dto.TransactionResponseDTO;
// import com.feraguiv.bankingservice.model.entity.Transaction;
import com.feraguiv.bankingservice.service.TransactionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {


private final TransactionService transactionService;

//http://localhost:8080/api/v1/transactions/accounts/{accountNumber}

@GetMapping("/accounts/{accountNumber}")
public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByAccountNumber
(@PathVariable String accountNumber){
        List<TransactionResponseDTO> transactions = transactionService.getTransactionsByAccountNumber(accountNumber);
       
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }


// http:localhost:8080/api/v1/transactions
@PostMapping

public ResponseEntity<TransactionResponseDTO> createTransaction( @Validated @RequestBody TransactionRequestDTO transactionRequestDTO){

    TransactionResponseDTO createTransaction = transactionService.createTransaction(transactionRequestDTO);

    return new ResponseEntity<>(createTransaction, HttpStatus.CREATED);
}

// http:localhost:8080/api/v1/transactions/report
@GetMapping("/report")

public ResponseEntity<List<TransactionReportDTO>> generateTransactionReport(

        @RequestParam("startDate") String startDateStr,
        @RequestParam("endDate") String endDateStr,
        @RequestParam("accountNumber") String accountNumber){

    List<TransactionReportDTO> reportDTOs = transactionService.generateTransactionReport(startDateStr, endDateStr, accountNumber);

    return new ResponseEntity<>(reportDTOs, HttpStatus.OK);
}

}


