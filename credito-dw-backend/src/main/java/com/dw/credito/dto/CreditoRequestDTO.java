package com.dw.credito.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditoRequestDTO {

    @NotBlank(message = "Número do crédito é obrigatório")
    @Size(max = 50, message = "Número do crédito deve ter no máximo 50 caracteres")
    private String numeroCredito;

    @NotBlank(message = "Número da NFS-e é obrigatório")
    @Size(max = 50, message = "Número da NFS-e deve ter no máximo 50 caracteres")
    private String numeroNfse;

    @NotNull(message = "Data de constituição é obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataConstituicao;

    @NotNull(message = "Valor ISSQN é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Valor ISSQN deve ser maior que zero")
    @Digits(integer = 13, fraction = 2, message = "Valor ISSQN deve ter no máximo 13 dígitos inteiros e 2 decimais")
    private BigDecimal valorIssqn;

    @NotBlank(message = "Tipo de crédito é obrigatório")
    @Size(max = 50, message = "Tipo de crédito deve ter no máximo 50 caracteres")
    private String tipoCredito;

    @NotNull(message = "Indicador de Simples Nacional é obrigatório")
    private boolean simplesNacional;

    @NotNull(message = "Alíquota é obrigatória")
    @DecimalMin(value = "0.0", inclusive = false, message = "Alíquota deve ser maior que zero")
    @DecimalMax(value = "100.0", message = "Alíquota não pode ser maior que 100")
    @Digits(integer = 3, fraction = 2, message = "Alíquota deve ter no máximo 3 dígitos inteiros e 2 decimais")
    private BigDecimal aliquota;

    @NotNull(message = "Valor faturado é obrigatório")
    @DecimalMin(value = "0.0", message = "Valor faturado não pode ser negativo")
    @Digits(integer = 13, fraction = 2, message = "Valor faturado deve ter no máximo 13 dígitos inteiros e 2 decimais")
    private BigDecimal valorFaturado;

    @NotNull(message = "Valor dedução é obrigatório")
    @DecimalMin(value = "0.0", message = "Valor dedução não pode ser negativo")
    @Digits(integer = 13, fraction = 2, message = "Valor dedução deve ter no máximo 13 dígitos inteiros e 2 decimais")
    private BigDecimal valorDeducao;

    @NotNull(message = "Base de cálculo é obrigatória")
    @DecimalMin(value = "0.0", message = "Base de cálculo não pode ser negativa")
    @Digits(integer = 13, fraction = 2, message = "Base de cálculo deve ter no máximo 13 dígitos inteiros e 2 decimais")
    private BigDecimal baseCalculo;
}