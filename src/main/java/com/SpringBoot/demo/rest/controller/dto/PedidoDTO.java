package com.SpringBoot.demo.rest.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

public class PedidoDTO {
        private Integer cliente;
        private BigDecimal total;

        private List<ItemPedidoDTO> items;


    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<ItemPedidoDTO> getItems() {
        return items;
    }

    public void setItensPedidos(List<ItemPedidoDTO> itensPedidos) {
        this.items = itensPedidos;
    }
}
