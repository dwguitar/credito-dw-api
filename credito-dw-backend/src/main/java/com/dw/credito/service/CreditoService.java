package com.dw.credito.service;

import com.dw.credito.dto.CreditoDTO;
import com.dw.credito.mapper.CreditoMapper;
import com.dw.credito.model.Credito;
import com.dw.credito.repository.CreditoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditoService {

    private final CreditoRepository creditoRepository;
    private final CreditoMapper creditoMapper;

    public List<CreditoDTO> buscarPorNfse(String numeroNfse) {
        return creditoRepository.findByNumeroNfse(numeroNfse).stream()
                .map(creditoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CreditoDTO buscarPorNumeroCredito(String numeroCredito) {
        Optional<Credito> credito = creditoRepository.findByNumeroCredito(numeroCredito);
        return credito.map(creditoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Crédito não encontrado"));
    }

    public CreditoDTO salvar(Credito credito) {
        Credito creditoSalvo = creditoRepository.save(credito);
        return creditoMapper.toDTO(creditoSalvo);
    }
}