package com.dw.credito;

import com.dw.credito.dto.CreditoDTO;
import com.dw.credito.service.CreditoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creditos")
public class CreditoController {

    private final CreditoService creditoService;

    public CreditoController(CreditoService creditoService) {
        this.creditoService = creditoService;
    }

    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoDTO>> getByNumeroNfse(@PathVariable String numeroNfse) {
        List<CreditoDTO> creditos = creditoService.buscarPorNfse(numeroNfse);
        return ResponseEntity.ok(creditos);
    }

    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoDTO> getByNumeroCredito(@PathVariable String numeroCredito) {
        CreditoDTO credito = creditoService.buscarPorNumeroCredito(numeroCredito);
        return ResponseEntity.ok(credito);
    }
}
