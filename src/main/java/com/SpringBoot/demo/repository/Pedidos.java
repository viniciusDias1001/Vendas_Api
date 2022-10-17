package com.SpringBoot.demo.repository;

import com.SpringBoot.demo.entidades.Cliente;
import com.SpringBoot.demo.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Pedidos extends JpaRepository<Pedido,Integer> {

    List<Pedido> findByCliente(Cliente cliente);

}
