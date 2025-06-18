package com.dw.credito.service;

import com.dw.credito.dto.CreditoDTO;
import com.dw.credito.mapper.CreditoMapper;
import com.dw.credito.model.Credito;
import com.dw.credito.repository.CreditoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditoServiceTest {

    @Mock
    private CreditoRepository creditoRepository;

    @Mock
    private CreditoMapper creditoMapper;

    @InjectMocks
    private CreditoService creditoService;

    @Test
    void buscarPorNfse_deveRetornarListaDeCreditos() {
        // Arrange
        String numeroNfse = "7891011";
        Credito credito = criarCreditoMock();
        CreditoDTO creditoDTO = criarCreditoDTOMock();

        when(creditoRepository.findByNumeroNfse(numeroNfse)).thenReturn(List.of(credito));
        when(creditoMapper.toDTO(credito)).thenReturn(creditoDTO);

        // Act
        List<CreditoDTO> resultado = creditoService.buscarPorNfse(numeroNfse);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(creditoDTO, resultado.get(0));

        verify(creditoRepository).findByNumeroNfse(numeroNfse);
        verify(creditoMapper).toDTO(credito);
    }

    @Test
    void buscarPorNfse_deveRetornarListaVaziaQuandoNaoExistir() {
        // Arrange
        String numeroNfse = "000000";

        when(creditoRepository.findByNumeroNfse(numeroNfse)).thenReturn(List.of());

        // Act
        List<CreditoDTO> resultado = creditoService.buscarPorNfse(numeroNfse);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(creditoRepository).findByNumeroNfse(numeroNfse);
    }

    @Test
    void buscarPorNumeroCredito_deveRetornarCreditoDTO() {
        // Arrange
        String numeroCredito = "123456";
        Credito credito = criarCreditoMock();
        CreditoDTO creditoDTO = criarCreditoDTOMock();

        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.of(credito));
        when(creditoMapper.toDTO(credito)).thenReturn(creditoDTO);

        // Act
        CreditoDTO resultado = creditoService.buscarPorNumeroCredito(numeroCredito);

        // Assert
        assertNotNull(resultado);
        assertEquals(creditoDTO, resultado);

        verify(creditoRepository).findByNumeroCredito(numeroCredito);
        verify(creditoMapper).toDTO(credito);
    }

    @Test
    void buscarPorNumeroCredito_deveLancarExcecaoQuandoNaoEncontrado() {
        // Arrange
        String numeroCredito = "999999";

        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            creditoService.buscarPorNumeroCredito(numeroCredito);
        });

        assertEquals("Crédito não encontrado", exception.getMessage());
        verify(creditoRepository).findByNumeroCredito(numeroCredito);
    }

    @Test
    void salvar_deveRetornarCreditoDTO() {
        // Arrange
        Credito credito = criarCreditoMock();
        Credito creditoSalvo = criarCreditoMock();
        creditoSalvo.setId(1L);
        CreditoDTO creditoDTO = criarCreditoDTOMock();

        when(creditoRepository.save(credito)).thenReturn(creditoSalvo);
        when(creditoMapper.toDTO(creditoSalvo)).thenReturn(creditoDTO);

        // Act
        CreditoDTO resultado = creditoService.salvar(credito);

        // Assert
        assertNotNull(resultado);
        assertEquals(creditoDTO, resultado);

        verify(creditoRepository).save(credito);
        verify(creditoMapper).toDTO(creditoSalvo);
    }

    private Credito criarCreditoMock() {
        return Credito.builder()
                .id(1L)
                .numeroCredito("123456")
                .numeroNfse("7891011")
                .dataConstituicao(LocalDate.of(2024, 1, 15))
                .valorIssqn(new BigDecimal("1500.75"))
                .tipoCredito("ISSQN")
                .simplesNacional(true)
                .aliquota(new BigDecimal("5.00"))
                .valorFaturado(new BigDecimal("30000.00"))
                .valorDeducao(new BigDecimal("5000.00"))
                .baseCalculo(new BigDecimal("25000.00"))
                .build();
    }

    private CreditoDTO criarCreditoDTOMock() {
        return CreditoDTO.builder()
                .id(1L)
                .numeroCredito("123456")
                .numeroNfse("7891011")
                .dataConstituicao(LocalDate.of(2023, 1, 15))
                .valorIssqn(new BigDecimal("1500.75"))
                .tipoCredito("ISSQN")
                .simplesNacional(true)
                .aliquota(new BigDecimal("5.00"))
                .valorFaturado(new BigDecimal("30000.00"))
                .valorDeducao(new BigDecimal("5000.00"))
                .baseCalculo(new BigDecimal("25000.00"))
                .build();
    }
}