package com.dw.credito.service;


import com.dw.credito.dto.CreditoDTO;
import com.dw.credito.exception.CreditoNotFoundException;
import com.dw.credito.mapper.CreditoMapper;
import com.dw.credito.model.Credito;
import com.dw.credito.repository.CreditoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditoService {
    private final CreditoRepository creditoRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CreditoMapper modelMapper;

    public List<CreditoDTO> buscarPorNfse(String numeroNfse) {
        List<Credito> creditos = creditoRepository.findByNumeroNfse(numeroNfse);

        if (creditos.isEmpty()) {
            throw new CreditoNotFoundException(numeroNfse);
        }
        return creditos.stream()
                .map(credito -> modelMapper.toDTO(credito))
                .collect(Collectors.toList());
    }

    public CreditoDTO buscarPorNumeroCredito(String numeroCredito) {
        Optional<Credito> optionalCredito = creditoRepository.findByNumeroCredito(numeroCredito);
        if (!optionalCredito.isPresent()) {
            optionalCredito.orElseThrow(() -> new CreditoNotFoundException(numeroCredito));
        }

        return modelMapper.toDTO(optionalCredito.get());
    }

}