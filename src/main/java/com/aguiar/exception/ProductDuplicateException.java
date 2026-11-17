package com.aguiar.exception;

public class ProductDuplicateException extends BusinessException {
    public ProductDuplicateException() {
        super("[Erro] Esse produto já está cadastrado no sistema.");
    }
}
