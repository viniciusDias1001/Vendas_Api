package com.SpringBoot.demo.Exception;

public class SenhaInvalidaException extends RuntimeException{
    public SenhaInvalidaException() {
        super("Senha inválida");
    }
}
