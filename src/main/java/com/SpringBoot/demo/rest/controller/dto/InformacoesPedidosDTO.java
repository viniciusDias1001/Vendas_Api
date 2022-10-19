package com.SpringBoot.demo.rest.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class InformacoesPedidosDTO {

    private Integer codigo;
    private String cpf;
    private String nomeCliente;
    private BigDecimal total;
    private String status;

    private String data;
    private List<InformacoesItemPedidosDTO> items;

    public InformacoesPedidosDTO(Integer codigo, String cpf, String nomeCliente, BigDecimal total, List<InformacoesItemPedidosDTO> items, String data, String status) {
        this.codigo = codigo;
        this.cpf = cpf;
        this.nomeCliente = nomeCliente;
        this.total = total;
        this.items = items;
        this.data = data;
        this.status = status;
    }
    public InformacoesPedidosDTO(){

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<InformacoesItemPedidosDTO> getItems() {
        return items;
    }

    public void setItems(List<InformacoesItemPedidosDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "InformacoesPedidosDTO{" +
                "codigo=" + codigo +
                ", cpf='" + cpf + '\'' +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", total=" + total +
                ", data=" + data +
                ", items=" + items +
                '}';
    }
}
