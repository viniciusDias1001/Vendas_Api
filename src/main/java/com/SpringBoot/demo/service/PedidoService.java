package com.SpringBoot.demo.service;

import com.SpringBoot.demo.domain.entidades.Pedido;
import com.SpringBoot.demo.rest.controller.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar( PedidoDTO dto );
}