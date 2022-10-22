package com.SpringBoot.demo.rest.controller;

import com.SpringBoot.demo.domain.entidades.Cliente;
import com.SpringBoot.demo.domain.entidades.repository.Clientes;
import io.swagger.annotations.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@Api("Api Clientes")
public class ClienteController {

    private Clientes clientes;

    public ClienteController( Clientes clientes ) {
        this.clientes = clientes;
    }

    @GetMapping("{id}")
    @ApiOperation("Obter as Informações de um Cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente Encontrado com Sucesso"),
            @ApiResponse(code = 404,message = "Cliente não encontrado no nosso DataBase, para o ID informado")
    })
    public Cliente getClienteById( @PathVariable @ApiParam("Id do Cliente") Integer id ){
        return clientes
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar um Cliente Novo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente Salvo com Sucesso."),
            @ApiResponse(code = 400,message = "Erro de validação, Observe o Json e veja se está tudo ok, ou está faltando algo.")
    })
    public Cliente save( @RequestBody @Valid  Cliente cliente ){
        return clientes.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Deletar um Cliente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente Deletado com Sucesso."),
            @ApiResponse(code = 404,message = "Cliente não encontrado no nosso DataBase, para o ID informado")
    })
    public void delete( @PathVariable  @ApiParam("Id do Cliente") Integer id ){
        clientes.findById(id)
                .map( cliente -> {
                    clientes.delete(cliente );
                    return cliente;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado") );

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualizar Alguma Informação do Cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente Atualizado com Sucesso."),
            @ApiResponse(code = 404,message = "Cliente não encontrado no nosso DataBase, para o ID informado")
    })
    public void update( @PathVariable  @ApiParam("Id do Cliente") Integer id,
                        @RequestBody @Valid Cliente cliente ){
        clientes
                .findById(id)
                .map( clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientes.save(cliente);
                    return clienteExistente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado") );
    }

    @GetMapping
    @ApiOperation("Lista de todos os Clientes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista Completa de Clientes"),
    })
    public List<Cliente> find( Cliente filtro ){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of(filtro, matcher);
        return clientes.findAll(example);
    }

}



