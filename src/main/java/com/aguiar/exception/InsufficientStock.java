package com.aguiar.exception;

public class InsufficientStock extends RuntimeException {
    public InsufficientStock() {
        super("[Erro] A quantidade de produtos no estoque é insuficiente para realizar essa transação.");
    }
}
