package com.feraguiv.bankingservice.model.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {

    @NotNull(message = "El numero de la cuenta de origen no puede estar vacío")
    private String sourceAccountNumber;

    @NotNull(message = "El numero de la cuenta de destino no puede estar vacío")
    private String targetAccountNumber;

    @NotNull(message = "La cantidad no puede estar vacía")
    @DecimalMin(value = "0.01" , message = "La cantidad debe ser mayor que cero")
    private BigDecimal amount;

    @NotBlank(message = "La descripcion no puede estar vacía")
    private String description;
}
