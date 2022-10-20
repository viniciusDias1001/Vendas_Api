package com.SpringBoot.demo.rest.controller;

import java.util.Arrays;
import java.util.List;

public class ApiErros {
    private List<String> erros;

    public ApiErros(String mensagemErros){
        this.erros = Arrays.asList(mensagemErros);
    }

    public ApiErros(List<String> list){
        this.erros = list;
    }

    public List<String> getErros() {
        return erros;
    }

    public void setErros(List<String> erros) {
        this.erros = erros;
    }

    @Override
    public String toString() {
        return "ApiErros{" +
                "erros=" + erros +
                '}';
    }
}
