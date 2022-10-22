package com.SpringBoot.demo.rest.controller;

import com.SpringBoot.demo.domain.entidades.Produto;
import com.SpringBoot.demo.domain.entidades.repository.Produtos;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import  static  org.springframework.http.HttpStatus.*;
@RestController
@RequestMapping("api/produto")
public class ProdutoController {
    Produtos produtos;

    public ProdutoController(Produtos produtos) {
        this.produtos = produtos;
    }


    @GetMapping("{id}")
    @ApiOperation("Obter as Informações de um Produto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto Encontrado com Sucesso"),
            @ApiResponse(code = 404,message = "Produto não encontrado no nosso DataBase, para o ID informado")
    })
    public Produto getProdutoById(@PathVariable @ApiParam("Id do Produto") Integer id){
       return produtos.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Salvar um Novo Produto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto Salvo com Sucesso."),
            @ApiResponse(code = 400,message = "Erro de validação, Observe o Json e veja se está tudo ok, ou está faltando algo.")
    })
    public Produto save( @Valid  @RequestBody Produto produto){
       return produtos.save(produto);
    }


    @DeleteMapping("/delete/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Deletar um Produto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Produto Deletado com Sucesso."),
            @ApiResponse(code = 404,message = "Produto não encontrado no nosso DataBase, para o ID informado")
    })
    public void delete(@PathVariable @ApiParam("Id do Produto") Integer id){
         produtos.findById(id).map(c -> {produtos.delete(c);
        return Void.TYPE ;}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Atualizar Alguma Informação do Produto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto Atualizado com Sucesso."),
            @ApiResponse(code = 404,message = "Produto não encontrado no nosso DataBase, para o ID informado")
    })
    public  Produto update(@PathVariable @ApiParam("Id do Produto") Integer id, @Valid @RequestBody Produto produto ){
        return produtos.findById(id).map(c -> { produto.setId(c.getId());  produtos.save(produto); return c ;}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));

    }

    @GetMapping
    @ApiOperation("Lista de todos os Produtos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista Completa de Produtos"),
    })
    public List<Produto> find(Produto filtro){
        ExampleMatcher matcher =  ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return produtos.findAll(example);

    }


}
