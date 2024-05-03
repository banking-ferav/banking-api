package com.feraguiv.bankingservice.service;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feraguiv.bankingservice.exception.BadRequestException;
import com.feraguiv.bankingservice.exception.ResourceNotFoundException;
import com.feraguiv.bankingservice.mapper.TransactionMapper;
// import com.feraguiv.bankingservice.model.dto.AccountResponseDTO;
import com.feraguiv.bankingservice.model.dto.TransactionReportDTO;
import com.feraguiv.bankingservice.model.dto.TransactionRequestDTO;
import com.feraguiv.bankingservice.model.dto.TransactionResponseDTO;
import com.feraguiv.bankingservice.model.entity.Account;
import com.feraguiv.bankingservice.model.entity.Transaction;
import com.feraguiv.bankingservice.repository.AccountRepository;
import com.feraguiv.bankingservice.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> getTransactionsByAccountNumber(String accountNumber){
        List<Transaction> transactions = transactionRepository.findBySourceOrTargetAccountNumber(accountNumber);
        return transactionMapper.convertToListDTO(transactions);
    }

    @Transactional
    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionrequestDTO){
        // obtener las cuentas involucradas en la transacción y verificar si existen

        Account sourceAccount = accountRepository.findByAccountNumber(transactionrequestDTO.getSourceAccountNumber())
        .orElseThrow(() -> new ResourceNotFoundException("La cuenta de origen no existe"));

        Account targetAccount = accountRepository.findByAccountNumber(transactionrequestDTO.getTargetAccountNumber())
        .orElseThrow(() -> new ResourceNotFoundException("La cuenta de destino no existe"));
    

    // si existe, verificar si el saldo de la cuenta de origen es suficiente para realizar la transaccion

    BigDecimal amount = transactionrequestDTO.getAmount();
    BigDecimal sourceAccountBalance = sourceAccount.getBalance();
    if(sourceAccountBalance.compareTo(amount) < 0){
        throw new BadRequestException("Saldo insuficiente en la cuenta de origen");
    }

    // realizar la transaccion

    Transaction transaction = transactionMapper.convertToEntity((transactionrequestDTO));
    transaction.setTransactionDate(LocalDate.now());
    transaction.setSourceAccount(sourceAccount);
    transaction.setTargetAccount(targetAccount);
    transaction = transactionRepository.save(transaction);

    // actualizar los saldos de las cuentas

    BigDecimal newSourceAccountBalance = sourceAccountBalance.subtract(amount);
    BigDecimal newTargetAccountBalance = targetAccount.getBalance().add(amount);

    sourceAccount.setBalance(newSourceAccountBalance);
    targetAccount.setBalance(newTargetAccountBalance);

    // guardar los cambios

    accountRepository.save(sourceAccount);
    accountRepository.save(targetAccount);

    // convertir entidad a DTO
    
    return transactionMapper.convertToDTO(transaction);

    }


    @Transactional(readOnly = true)
    public List<TransactionReportDTO> generateTransactionReport(String startDateStr, String endDateStr, String accountNumber){

    LocalDate startDate = LocalDate.parse(startDateStr);
    LocalDate endDate = LocalDate.parse(endDateStr);

    List<Object[]> transactionCounts = transactionRepository
    .getTransactionCountByDateRangeAndAccountNumber(startDate, endDate, accountNumber);
    return transactionCounts.stream()
    .map(transactionMapper::convertTransactionReportDTO)
    .toList();

}

}
