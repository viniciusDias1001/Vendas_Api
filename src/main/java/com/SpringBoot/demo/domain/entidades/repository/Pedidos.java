package com.SpringBoot.demo.domain.entidades.repository;

import com.SpringBoot.demo.domain.entidades.Cliente;
import com.SpringBoot.demo.domain.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository<Pedido,Integer> {

    List<Pedido> findByCliente(Cliente cliente);

}
