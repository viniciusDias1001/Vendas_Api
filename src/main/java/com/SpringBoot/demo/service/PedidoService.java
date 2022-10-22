package com.SpringBoot.demo.service;

import com.SpringBoot.demo.domain.entidades.Pedido;
import com.SpringBoot.demo.domain.entidades.enums.StatusPedido;
import com.SpringBoot.demo.rest.controller.dto.InformacoesPedidosDTO;
import com.SpringBoot.demo.rest.controller.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar( PedidoDTO dto );
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}