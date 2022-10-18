package com.SpringBoot.demo.rest.controller;

import com.SpringBoot.demo.domain.entidades.Cliente;
import com.SpringBoot.demo.domain.entidades.repository.Clientes;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById( @PathVariable Integer id){
        Optional<Cliente> cliente = clientes.findById(id);

        if (cliente.isPresent()){
           return ResponseEntity.ok(cliente.get());
        }

    }

}
