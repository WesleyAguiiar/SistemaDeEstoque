package com.aguiar.exception;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException() {
        super("[Erro] A quantidade inserida deve ser maior que zero.");
    }
}
