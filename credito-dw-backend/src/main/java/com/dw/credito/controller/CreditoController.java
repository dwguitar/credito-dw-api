package com.dw.credito.controller;

import com.dw.credito.dto.CreditoDTO;
import com.dw.credito.service.CreditoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/creditos")
@Tag(name = "Créditos", description = "API para consulta de créditos")
public class CreditoController {

    private final CreditoService creditoService;

    public CreditoController(CreditoService creditoService) {
        this.creditoService = creditoService;
    }

    @Operation(
            summary = "Buscar créditos por número da NFS-e",
            description = "Retorna uma lista de créditos vinculados ao número da NFS-e informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Créditos não encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoDTO>> getByNumeroNfse(
            @Parameter(description = "Número da NFS-e", example = "123456789")
            @PathVariable String numeroNfse) {
        List<CreditoDTO> creditos = creditoService.buscarPorNfse(numeroNfse);
        return ResponseEntity.ok(creditos);
    }

    @Operation(
            summary = "Buscar crédito por número do crédito",
            description = "Retorna os detalhes de um crédito específico através do número do crédito."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crédito encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Crédito não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoDTO> getByNumeroCredito(
            @Parameter(description = "Número do crédito", example = "7891011")
            @PathVariable String numeroCredito) {
        CreditoDTO credito = creditoService.buscarPorNumeroCredito(numeroCredito);
        return ResponseEntity.ok(credito);
    }
}
