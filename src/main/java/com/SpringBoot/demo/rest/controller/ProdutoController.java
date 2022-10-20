package com.SpringBoot.demo.rest.controller;

import com.SpringBoot.demo.domain.entidades.Produto;
import com.SpringBoot.demo.domain.entidades.repository.Produtos;
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
    public Produto getProdutoById(@PathVariable Integer id){
       return produtos.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save( @Valid  @RequestBody Produto produto){
       return produtos.save(produto);
    }


    @DeleteMapping("/delete/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id){
         produtos.findById(id).map(c -> {produtos.delete(c);
        return Void.TYPE ;}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(NO_CONTENT)
    public  Produto update(@PathVariable Integer id, @Valid @RequestBody Produto produto ){
        return produtos.findById(id).map(c -> { produto.setId(c.getId());  produtos.save(produto); return c ;}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));

    }

    @GetMapping
    public List<Produto> find(Produto filtro){
        ExampleMatcher matcher =  ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return produtos.findAll(example);

    }


}
