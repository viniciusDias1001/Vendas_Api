package com.SpringBoot.demo.domain.entidades.repository;

import com.SpringBoot.demo.domain.entidades.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto,Integer> {


}
