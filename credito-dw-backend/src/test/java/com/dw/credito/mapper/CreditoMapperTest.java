package com.dw.credito.mapper;


import com.dw.credito.dto.CreditoDTO;
import com.dw.credito.model.Credito;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CreditoMapperTest {

    private final CreditoMapper mapper = new CreditoMapperImpl();

    @Test
    void deveConverterCreditoParaCreditoDTO() {

        Credito credito = new Credito();
        credito.setId(1L);
        credito.setNumeroCredito("12345");
        credito.setNumeroNfse("67890");
        credito.setDataConstituicao(LocalDate.of(2023, 1, 15));
        credito.setValorIssqn(new BigDecimal("50.00"));
        credito.setTipoCredito("ISSQN");
        credito.setSimplesNacional(true);
        credito.setAliquota(new BigDecimal("5.00"));
        credito.setValorFaturado(new BigDecimal("1000.00"));
        credito.setValorDeducao(new BigDecimal("100.00"));
        credito.setBaseCalculo(new BigDecimal("900.00"));

        CreditoDTO dto = mapper.toDTO(credito);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getNumeroCredito()).isEqualTo("12345");
        assertThat(dto.getNumeroNfse()).isEqualTo("67890");
        assertThat(dto.getDataConstituicao()).isEqualTo("2023-01-15");
        assertThat(dto.getValorIssqn()).isEqualByComparingTo("50.00");
        assertThat(dto.getTipoCredito()).isEqualTo("ISSQN");
        assertThat(dto.getSimplesNacional()).isEqualTo(true);
        assertThat(dto.getAliquota()).isEqualByComparingTo("5.00");
        assertThat(dto.getValorFaturado()).isEqualByComparingTo("1000.00");
        assertThat(dto.getValorDeducao()).isEqualByComparingTo("100.00");
        assertThat(dto.getBaseCalculo()).isEqualByComparingTo("900.00");
    }

    @Test
    void deveConverterCreditoDTOParaCredito() {
        CreditoDTO dto = new CreditoDTO();
        dto.setId(1L);
        dto.setNumeroCredito("12345");
        dto.setValorFaturado(new BigDecimal("1000.00"));

        Credito credito = mapper.toEntity(dto);

        assertThat(credito.getNumeroCredito()).isEqualTo("12345");
        assertThat(credito.getValorFaturado()).isEqualTo("1000.00");
    }
}
