package com.dw.credito.service;

import com.dw.credito.dto.CreditoDTO;
import com.dw.credito.mapper.CreditoMapper;
import com.dw.credito.model.Credito;
import com.dw.credito.repository.CreditoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.dw.credito.exception.CreditoNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditoService {

    private final CreditoRepository creditoRepository;
    private final CreditoMapper creditoMapper;

    public List<CreditoDTO> buscarPorNfse(String numeroNfse) {
        List<Credito> creditos = creditoRepository.findByNumeroNfse(numeroNfse);

        if (creditos.isEmpty()) {
            throw new CreditoNotFoundException("Nenhum crédito encontrado para a NFSe: " + numeroNfse);
        }

        return creditos.stream()
                .map(creditoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CreditoDTO buscarPorNumeroCredito(String numeroCredito) {
        return creditoRepository.findByNumeroCredito(numeroCredito)
                .map(credito -> {
                    CreditoDTO dto = new CreditoDTO();
                    dto.setId(credito.getId());
                    dto.setNumeroCredito(credito.getNumeroCredito());
                    dto.setNumeroNfse(credito.getNumeroNfse());
                    dto.setDataConstituicao(credito.getDataConstituicao());
                    dto.setValorIssqn(credito.getValorIssqn());
                    dto.setTipoCredito(credito.getTipoCredito());
                    dto.setSimplesNacional(credito.isSimplesNacional());
                    dto.setAliquota(credito.getAliquota());
                    dto.setValorFaturado(credito.getValorFaturado());
                    dto.setValorDeducao(credito.getValorDeducao());
                    dto.setBaseCalculo(credito.getBaseCalculo());
                    return dto;
                })
                .orElseThrow(() -> new CreditoNotFoundException("Crédito não encontrado com número: " + numeroCredito));
    }

    public CreditoDTO salvar(Credito credito) {
        Credito creditoSalvo = creditoRepository.save(credito);
        return creditoMapper.toDTO(creditoSalvo);
    }
}