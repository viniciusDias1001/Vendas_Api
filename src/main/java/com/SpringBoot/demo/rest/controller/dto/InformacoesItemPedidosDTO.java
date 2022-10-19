package com.SpringBoot.demo.rest.controller.dto;

import java.math.BigDecimal;

public class InformacoesItemPedidosDTO {

    private String descricao;
    private BigDecimal precoUnitario;
    private Integer quantidade;

    public InformacoesItemPedidosDTO(String descricao, BigDecimal precoUnitario, Integer quantidade) {
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }
    public InformacoesItemPedidosDTO(){

    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
