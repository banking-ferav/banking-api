package com.feraguiv.bankingservice.model.dto;


import java.time.LocalDate;


public class TransactionReportDTO {

    private LocalDate date;
    private long transactionCount;

    public TransactionReportDTO() {

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(long transactionCount) {
        this.transactionCount = transactionCount;
    }


}

