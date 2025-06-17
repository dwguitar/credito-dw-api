package com.dw.credito.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditoResponseDTO {
    private Long id;
    private String numeroCredito;
    private String numeroNfse;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataConstituicao;

    private BigDecimal valorIssqn;
    private String tipoCredito;
    private String simplesNacional; // "Sim" ou "Não"
    private BigDecimal aliquota;
    private BigDecimal valorFaturado;
    private BigDecimal valorDeducao;
    private BigDecimal baseCalculo;

    private BigDecimal percentualDeducao;
    private BigDecimal valorLiquido;
}