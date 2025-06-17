package com.dw.credito.model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CreditoTest {

    @Test
    void deveCriarCreditoComDadosCorretos() {
        Credito credito = new Credito();
        credito.setId(1L);
        credito.setNumeroCredito("12345");
        credito.setValorFaturado(new BigDecimal("1000.00"));

        assertThat(credito.getId()).isEqualTo(1L);
        assertThat(credito.getNumeroCredito()).isEqualTo("12345");
        assertThat(credito.getValorFaturado()).isEqualTo("1000.00");
    }
}
