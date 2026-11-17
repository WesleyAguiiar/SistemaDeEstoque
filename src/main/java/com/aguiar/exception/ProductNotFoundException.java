package com.aguiar.exception;

public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException() {
        super("[Erro] Produto não encontrado.");
    }
}
