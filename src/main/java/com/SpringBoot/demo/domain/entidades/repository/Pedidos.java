package com.SpringBoot.demo.domain.entidades.repository;

import com.SpringBoot.demo.domain.entidades.Cliente;
import com.SpringBoot.demo.domain.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface Pedidos extends JpaRepository<Pedido,Integer> {

    List<Pedido> findByCliente(Cliente cliente);


    @Query("select p from Pedido p left join fetch p.itens where p.id =:id" )
    Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);



}
