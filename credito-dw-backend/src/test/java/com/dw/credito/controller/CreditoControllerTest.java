package com.dw.credito.controller;

import com.dw.credito.dto.CreditoDTO;
import com.dw.credito.service.CreditoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(CreditoController.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class CreditoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditoService creditoService;

    private CreditoDTO mockCredito;

    @BeforeEach
    void setup() {
        mockCredito = new CreditoDTO();
        mockCredito.setId(1L);
        mockCredito.setNumeroCredito("12345");
        mockCredito.setNumeroNfse("NFSE001");
        mockCredito.setValorFaturado(new BigDecimal("1000.0"));
    }

    @Test
    void deveBuscarCreditosPorNumeroNfse() throws Exception {
        when(creditoService.buscarPorNfse(anyString())).thenReturn(List.of(mockCredito));

        mockMvc.perform(get("/api/creditos/{numeroNfse}", "NFSE001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].numeroCredito").value("12345"))
                .andExpect(jsonPath("$[0].numeroNfse").value("NFSE001"))
                .andExpect(jsonPath("$[0].valorFaturado").value("1000.0"));

        Mockito.verify(creditoService).buscarPorNfse("NFSE001");
    }

    @Test
    void deveBuscarCreditoPorNumeroCredito() throws Exception {
        when(creditoService.buscarPorNumeroCredito(anyString())).thenReturn(mockCredito);

        mockMvc.perform(get("/api/creditos/credito/{numeroCredito}", "12345")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.numeroCredito").value("12345"))
                .andExpect(jsonPath("$.numeroNfse").value("NFSE001"))
                .andExpect(jsonPath("$.valorFaturado").value("1000.0"));

        Mockito.verify(creditoService).buscarPorNumeroCredito("12345");
    }
}
