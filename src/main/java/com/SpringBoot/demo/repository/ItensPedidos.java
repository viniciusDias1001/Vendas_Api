package com.SpringBoot.demo.repository;

import com.SpringBoot.demo.entidades.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedidos extends JpaRepository<ItemPedido,Integer> {
}
