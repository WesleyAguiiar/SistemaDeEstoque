package com.aguiar.exception;

public class InvalidNameException extends BusinessException {
    public InvalidNameException() {
        super("[Erro] O nome inserido não é válido.");
    }
}
