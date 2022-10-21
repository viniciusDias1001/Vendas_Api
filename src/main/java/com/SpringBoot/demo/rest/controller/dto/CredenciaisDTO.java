package com.SpringBoot.demo.rest.controller.dto;

public class CredenciaisDTO {
    private String login;
    private String senha;

    public CredenciaisDTO(){


    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
