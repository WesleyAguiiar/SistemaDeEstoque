package com.aguiar.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("[Erro] Produto não encontrado.");
    }
}
