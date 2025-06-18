package com.dw.credito.service;

import com.dw.credito.dto.CreditoDTO;
import com.dw.credito.exception.CreditoNotFoundException;
import com.dw.credito.mapper.CreditoMapper;
import com.dw.credito.model.Credito;
import com.dw.credito.repository.CreditoRepository;
import org.junit.jupiter.api.BeforeEach;
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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditoServiceTest {

    @Mock
    private CreditoRepository creditoRepository;

    @Mock
    private CreditoMapper creditoMapper;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private CreditoService creditoService;

    private Credito credito;
    private CreditoDTO creditoDTO;

    @BeforeEach
    void setup() {
        credito = new Credito();
        credito.setId(1L);
        credito.setNumeroCredito("CRD-123");
        credito.setNumeroNfse("NFS-456");
        credito.setDataConstituicao(LocalDate.now());
    credito.setValorIssqn(new BigDecimal("100.0"));
        credito.setTipoCredito("ISS");
        credito.setSimplesNacional(true);
        credito.setAliquota(new BigDecimal("2.5"));
        credito.setValorFaturado(new BigDecimal("100.0"));
        credito.setValorDeducao(new BigDecimal("50.0"));
        credito.setBaseCalculo(new BigDecimal("950.0"));

        creditoDTO = new CreditoDTO();
        creditoDTO.setId(credito.getId());
        creditoDTO.setNumeroCredito(credito.getNumeroCredito());
        creditoDTO.setNumeroNfse(credito.getNumeroNfse());
        creditoDTO.setDataConstituicao(credito.getDataConstituicao());
        creditoDTO.setValorIssqn(credito.getValorIssqn());
        creditoDTO.setTipoCredito(credito.getTipoCredito());
        creditoDTO.setSimplesNacional(credito.isSimplesNacional());
        creditoDTO.setAliquota(credito.getAliquota());
        creditoDTO.setValorFaturado(credito.getValorFaturado());
        creditoDTO.setValorDeducao(credito.getValorDeducao());
        creditoDTO.setBaseCalculo(credito.getBaseCalculo());
    }

    @Test
    void buscarPorNfse_sucesso() {
        when(creditoRepository.findByNumeroNfse("NFS-456")).thenReturn(List.of(credito));
        when(creditoMapper.toDTO(credito)).thenReturn(creditoDTO);

        List<CreditoDTO> resultado = creditoService.buscarPorNfse("NFS-456");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNumeroNfse()).isEqualTo("NFS-456");

        // Verifica se o evento Kafka foi enviado com a mensagem correta
        verify(kafkaTemplate).send(eq("consultas-creditos"), eq("Consulta por NFS-e: NFS-456"));
    }

    @Test
    void buscarPorNfse_semResultado_lancaExcecao() {
        when(creditoRepository.findByNumeroNfse("NFS-999")).thenReturn(List.of());

        assertThatThrownBy(() -> creditoService.buscarPorNfse("NFS-999"))
                .isInstanceOf(CreditoNotFoundException.class)
                .hasMessageContaining("Nenhum crédito encontrado para a NFSe: NFS-999");

        verify(kafkaTemplate, never()).send(anyString(), anyString());
    }

    @Test
    void buscarPorNumeroCredito_sucesso() {
        when(creditoRepository.findByNumeroCredito("CRD-123")).thenReturn(Optional.of(credito));
        when(creditoMapper.toDTO(credito)).thenReturn(creditoDTO);

        CreditoDTO dto = creditoService.buscarPorNumeroCredito("CRD-123");

        assertThat(dto.getNumeroCredito()).isEqualTo("CRD-123");

        verify(kafkaTemplate).send(eq("consultas-creditos"), eq("Consulta por Crédito: CRD-123"));
    }

    @Test
    void buscarPorNumeroCredito_naoEncontrado_lancaExcecao() {
        when(creditoRepository.findByNumeroCredito("CRD-999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> creditoService.buscarPorNumeroCredito("CRD-999"))
                .isInstanceOf(CreditoNotFoundException.class)
                .hasMessageContaining("Crédito não encontrado com número: CRD-999");

        verify(kafkaTemplate, never()).send(anyString(), anyString());
    }

    @Test
    void salvar_deveSalvarEConverterParaDTO() {
        when(creditoRepository.save(credito)).thenReturn(credito);
        when(creditoMapper.toDTO(credito)).thenReturn(creditoDTO);

        CreditoDTO dto = creditoService.salvar(credito);

        assertThat(dto).isNotNull();
        assertThat(dto.getNumeroCredito()).isEqualTo("CRD-123");

        verify(creditoRepository).save(credito);
    }
}
