//package com.dw.credito.controller;
//
//import com.dw.credito.dto.CreditoDTO;
//import com.dw.credito.service.CreditoService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)
//class CreditoControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CreditoService creditoService;
//
//    @Test
//    void getByNumeroNfse_deveRetornarListaCreditos() throws Exception {
//
//        String numeroNfse = "7891011";
//        CreditoDTO creditoDTO = criarCreditoDTOMock();
//
//        when(creditoService.buscarPorNfse(numeroNfse))
//                .thenReturn(List.of(creditoDTO));
//
//        mockMvc.perform(get("/api/creditos/{numeroNfse}", numeroNfse)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].numeroCredito", is("123456")))
//                .andExpect(jsonPath("$[0].numeroNfse", is(numeroNfse)));
//
//        verify(creditoService).buscarPorNfse(numeroNfse);
//    }
//
//    @Test
//    void getByNumeroNfse_deveRetornar404QuandoNaoEncontrado() throws Exception {
//
//        String numeroNfse = "000000";
//
//        when(creditoService.buscarPorNfse(numeroNfse))
//                .thenReturn(List.of());
//
//
//        mockMvc.perform(get("/api/creditos/{numeroNfse}", numeroNfse))
//                .andExpect(status().isOk()) // Retorna lista vazia, não 404
//                .andExpect(jsonPath("$", hasSize(0)));
//
//        verify(creditoService).buscarPorNfse(numeroNfse);
//    }
//
//    @Test
//    void getByNumeroCredito_deveRetornarCredito() throws Exception {
//
//        String numeroCredito = "123456";
//        CreditoDTO creditoDTO = criarCreditoDTOMock();
//
//        when(creditoService.buscarPorNumeroCredito(numeroCredito))
//                .thenReturn(creditoDTO);
//
//
//        mockMvc.perform(get("/api/creditos/credito/{numeroCredito}", numeroCredito))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.numeroCredito", is(numeroCredito)))
//                .andExpect(jsonPath("$.valorIssqn", is(1500.75)));
//
//        verify(creditoService).buscarPorNumeroCredito(numeroCredito);
//    }
//
//    @Test
//    void getByNumeroCredito_deveRetornar404QuandoNaoEncontrado() throws Exception {
//
//        String numeroCredito = "999999";
//
//        when(creditoService.buscarPorNumeroCredito(numeroCredito))
//                .thenThrow(new RuntimeException("Crédito não encontrado"));
//
//
//        mockMvc.perform(get("/api/creditos/credito/{numeroCredito}", numeroCredito))
//                .andExpect(status().is5xxServerError()); // Ou configure para retornar 404
//
//        verify(creditoService).buscarPorNumeroCredito(numeroCredito);
//    }
//
//    private CreditoDTO criarCreditoDTOMock() {
//        return CreditoDTO.builder()
//                .id(1L)
//                .numeroCredito("123456")
//                .numeroNfse("7891011")
//                .dataConstituicao(LocalDate.of(2024, 1, 15))
//                .valorIssqn(new BigDecimal("1500.75"))
//                .tipoCredito("ISSQN")
//                .simplesNacional(true)
//                .aliquota(new BigDecimal("5.00"))
//                .valorFaturado(new BigDecimal("30000.00"))
//                .valorDeducao(new BigDecimal("5000.00"))
//                .baseCalculo(new BigDecimal("25000.00"))
//                .build();
//    }
//}


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
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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
        mockCredito.setValorFaturado(new BigDecimal("1000.00"));
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
                .andExpect(jsonPath("$[0].valorFaturado").value("1000.00"));

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
                .andExpect(jsonPath("$.valorFaturado").value("1000.00"));

        Mockito.verify(creditoService).buscarPorNumeroCredito("12345");
    }
}
