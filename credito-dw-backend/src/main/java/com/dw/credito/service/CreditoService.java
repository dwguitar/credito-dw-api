package com.dw.credito.service;


import com.dw.credito.model.Credito;
import com.dw.credito.repository.CreditoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditoService {
    private final CreditoRepository creditoRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public List<Credito> buscarPorNfse(String numeroNfse) {
        kafkaTemplate.send("consultas-creditos", "Consulta por NFS-e: " + numeroNfse);
        return creditoRepository.findByNumeroNfse(numeroNfse);
    }

    public Credito buscarPorNumeroCredito(String numeroCredito) {
        kafkaTemplate.send("consultas-creditos", "Consulta por Crédito: " + numeroCredito);
        return creditoRepository.findByNumeroCredito(numeroCredito);
    }

//    @Transactional(readOnly = true)
//    public Credito buscarPorNumeroCredito(String numeroCredito) {
//        log.info("Buscando crédito por número: {}", numeroCredito);
//
//        return creditoRepository.findByNumeroCredito(numeroCredito)
//                .orElseThrow(() -> new CreditoNaoEncontradoException(
//                        "Crédito não encontrado com o número: " + numeroCredito));
//    }
}