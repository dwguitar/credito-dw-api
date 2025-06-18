package com.dw.credito.service;

import com.dw.credito.dto.CreditoDTO;
import com.dw.credito.exception.CreditoNotFoundException;
import com.dw.credito.mapper.CreditoMapper;
import com.dw.credito.model.Credito;
import com.dw.credito.repository.CreditoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditoService {

    private final CreditoRepository creditoRepository;
    private final CreditoMapper creditoMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPICO = "consultas-creditos";

    public List<CreditoDTO> buscarPorNfse(String numeroNfse) {
        List<Credito> creditos = creditoRepository.findByNumeroNfse(numeroNfse);

        if (creditos.isEmpty()) {
            throw new CreditoNotFoundException("Nenhum crédito encontrado para a NFSe: " + numeroNfse);
        }

        publicarEventoKafka("Consulta por NFS-e: " + numeroNfse);

        return creditos.stream()
                .map(creditoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CreditoDTO buscarPorNumeroCredito(String numeroCredito) {
        Credito credito = creditoRepository.findByNumeroCredito(numeroCredito)
                .orElseThrow(() -> new CreditoNotFoundException("Crédito não encontrado com número: " + numeroCredito));

        publicarEventoKafka("Consulta por Crédito: " + numeroCredito);

        return creditoMapper.toDTO(credito);
    }

    public CreditoDTO salvar(Credito credito) {
        Credito creditoSalvo = creditoRepository.save(credito);
        return creditoMapper.toDTO(creditoSalvo);
    }

    private void publicarEventoKafka(String mensagem) {
        kafkaTemplate.send(TOPICO, mensagem);
         log.info("Evento Kafka enviado para tópico {}: {}", TOPICO, mensagem);
    }
}
