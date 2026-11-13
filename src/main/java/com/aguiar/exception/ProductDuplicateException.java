package com.aguiar.exception;

public class ProductDuplicateException extends RuntimeException {
    public ProductDuplicateException() {
        super("[Erro] Esse produto já está cadastrado no sistema.");
    }
}
