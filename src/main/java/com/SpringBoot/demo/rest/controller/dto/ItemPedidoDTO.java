package com.SpringBoot.demo.rest.controller.dto;

public class ItemPedidoDTO {

    private Integer produto;
    private Integer quantidade;

    public Integer getProduto() {
        return produto;
    }

    public void setProduto(Integer produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
