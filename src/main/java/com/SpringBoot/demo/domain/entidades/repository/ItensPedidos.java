package com.SpringBoot.demo.domain.entidades.repository;

import com.SpringBoot.demo.domain.entidades.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedidos extends JpaRepository<ItemPedido,Integer> {
}
