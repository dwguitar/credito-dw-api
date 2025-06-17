package com.dw.credito.service;

import static org.junit.jupiter.api.Assertions.*;


import com.dw.credito.dto.CreditoDTO;
import com.dw.credito.mapper.CreditoMapper;
import com.dw.credito.model.Credito;
import com.dw.credito.repository.CreditoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditoServiceTest {

    @Mock
    private CreditoRepository creditoRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private CreditoMapper modelMapper;

    @InjectMocks
    private CreditoService creditoService;

    @Test
    void buscarPorNfse_deveRetornarListaDeCreditos() {

        String numeroNfse = "7891011";
        List<Credito> creditos = List.of(
                new Credito(1L, "123456", numeroNfse, LocalDate.now(),
                        BigDecimal.valueOf(1500.75), "ISSQN", true,
                        BigDecimal.valueOf(5.0), BigDecimal.valueOf(30000.00),
                        BigDecimal.valueOf(5000.00), BigDecimal.valueOf(25000.00))
        );

        when(creditoRepository.findByNumeroNfse(numeroNfse)).thenReturn(creditos);

        List<CreditoDTO> resultado = creditoService.buscarPorNfse(numeroNfse);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(numeroNfse, resultado.get(0).getNumeroNfse());
        verify(kafkaTemplate).send(eq("consultas-creditos"), anyString());
    }

    @Test
    void buscarPorNumeroCredito_deveRetornarCredito() {

        String numeroCredito = "123456";
        Credito credito = new Credito(1L, numeroCredito, "7891011", LocalDate.now(),
                BigDecimal.valueOf(1500.75), "ISSQN", true,
                BigDecimal.valueOf(5.0), BigDecimal.valueOf(30000.00),
                BigDecimal.valueOf(5000.00), BigDecimal.valueOf(25000.00));

        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.of(credito));

        CreditoDTO resultado = creditoService.buscarPorNumeroCredito(numeroCredito);

        assertNotNull(resultado);
        assertEquals(numeroCredito, resultado.getNumeroCredito());
        verify(kafkaTemplate).send(eq("consultas-creditos"), anyString());
    }
}