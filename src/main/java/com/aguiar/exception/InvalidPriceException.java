package com.aguiar.exception;

public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException() {
        super("[Erro] O valor inserido não é válido, o preço precisa ser maior que zero.");
    }
}
