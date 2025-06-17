package com.dw.credito.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditoDTO {
    private Long id;
    private String numeroCredito;
    private String numeroNfse;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataConstituicao;

    private BigDecimal valorIssqn;
    private String tipoCredito;
    private boolean simplesNacional;
    private BigDecimal aliquota;
    private BigDecimal valorFaturado;
    private BigDecimal valorDeducao;
    private BigDecimal baseCalculo;

    public boolean getSimplesNacional() {
        return simplesNacional;
    }
}
