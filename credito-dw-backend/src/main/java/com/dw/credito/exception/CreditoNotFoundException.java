package com.dw.credito.exception;


public class CreditoNotFoundException extends RuntimeException {
    public CreditoNotFoundException(String numero) {
        super("Crédito não encontrado para o identificador: " + numero);
    }
}
