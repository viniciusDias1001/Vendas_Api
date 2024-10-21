package com.SpringBoot.demo.Testes;


import com.SpringBoot.demo.domain.entidades.Produto;
import com.SpringBoot.demo.domain.entidades.repository.Produtos;
import com.SpringBoot.demo.rest.controller.ProdutoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoControllerTest {

    @Mock
    private Produtos produtos;

    @InjectMocks
    private ProdutoController produtoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProdutoById() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setDescricao("Test Product");
        produto.setPrecoUnitario(new BigDecimal("10.00"));
        when(produtos.findById(1)).thenReturn(Optional.of(produto));

        Produto result = produtoController.getProdutoById(1);

        assertNotNull(result);
        assertEquals(produto.getId(), result.getId());
        assertEquals(produto.getDescricao(), result.getDescricao());
        assertEquals(produto.getPrecoUnitario(), result.getPrecoUnitario());
    }

    @Test
    void testGetProdutoByIdNotFound() {
        when(produtos.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            produtoController.getProdutoById(1);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testSaveProduto() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setDescricao("Test Product");
        produto.setPrecoUnitario(new BigDecimal("10.00"));
        when(produtos.save(any(Produto.class))).thenReturn(produto);

        Produto result = produtoController.save(produto);

        assertNotNull(result);
        assertEquals(produto.getId(), result.getId());
        assertEquals(produto.getDescricao(), result.getDescricao());
        assertEquals(produto.getPrecoUnitario(), result.getPrecoUnitario());
    }

    @Test
    void testDeleteProduto() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setDescricao("Test Product");
        produto.setPrecoUnitario(new BigDecimal("10.00"));
        when(produtos.findById(1)).thenReturn(Optional.of(produto));
        doNothing().when(produtos).delete(produto);

        assertDoesNotThrow(() -> produtoController.delete(1));
        verify(produtos, times(1)).delete(produto);
    }

    @Test
    void testDeleteProdutoNotFound() {
        when(produtos.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            produtoController.delete(1);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testUpdateProduto() {
        Produto produto = new Produto();
        produto.setId(1);
        produto.setDescricao("Test Product");
        produto.setPrecoUnitario(new BigDecimal("10.00"));
        when(produtos.findById(1)).thenReturn(Optional.of(produto));
        when(produtos.save(any(Produto.class))).thenReturn(produto);

        Produto updatedProduto = new Produto();
        updatedProduto.setId(1);
        updatedProduto.setDescricao("Updated Product");
        updatedProduto.setPrecoUnitario(new BigDecimal("15.00"));
        assertDoesNotThrow(() -> produtoController.update(1, updatedProduto));
        verify(produtos, times(1)).save(updatedProduto);
    }

    @Test
    void testUpdateProdutoNotFound() {
        when(produtos.findById(1)).thenReturn(Optional.empty());

        Produto updatedProduto = new Produto();
        updatedProduto.setId(1);
        updatedProduto.setDescricao("Updated Product");
        updatedProduto.setPrecoUnitario(new BigDecimal("15.00"));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            produtoController.update(1, updatedProduto);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testFindProdutos() {
        Produto produto1 = new Produto();
        produto1.setId(1);
        produto1.setDescricao("Test Product 1");
        produto1.setPrecoUnitario(new BigDecimal("10.00"));

        Produto produto2 = new Produto();
        produto2.setId(2);
        produto2.setDescricao("Test Product 2");
        produto2.setPrecoUnitario(new BigDecimal("20.00"));

        List<Produto> produtosList = Arrays.asList(produto1, produto2);
        when(produtos.findAll(any(Example.class))).thenReturn(produtosList);

        List<Produto> result = produtoController.find(new Produto());

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}