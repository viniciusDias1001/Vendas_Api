package com.SpringBoot.demo.domain.entidades.repository;

import com.SpringBoot.demo.domain.entidades.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedido extends JpaRepository<ItemPedido,Integer> {
}
