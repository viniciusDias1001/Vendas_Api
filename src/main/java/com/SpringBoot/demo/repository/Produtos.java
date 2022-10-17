package com.SpringBoot.demo.repository;

import com.SpringBoot.demo.entidades.Pedido;
import com.SpringBoot.demo.entidades.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Produtos extends JpaRepository<Produto,Integer> {


}
