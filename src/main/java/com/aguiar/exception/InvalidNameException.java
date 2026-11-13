package com.aguiar.exception;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException() {
        super("[Erro] O nome inserido não é válido.");
    }
}
