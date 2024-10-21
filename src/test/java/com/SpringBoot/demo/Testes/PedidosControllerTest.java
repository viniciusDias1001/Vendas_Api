package com.SpringBoot.demo.Testes;


import com.SpringBoot.demo.Exception.RegraNegocioException;
import com.SpringBoot.demo.domain.entidades.Cliente;
import com.SpringBoot.demo.domain.entidades.ItemPedido;
import com.SpringBoot.demo.domain.entidades.Pedido;
import com.SpringBoot.demo.domain.entidades.Produto;
import com.SpringBoot.demo.domain.entidades.enums.StatusPedido;
import com.SpringBoot.demo.rest.controller.PedidosController;
import com.SpringBoot.demo.rest.controller.dto.AtualizacaoStatusPedidoDTO;
import com.SpringBoot.demo.rest.controller.dto.PedidoDTO;
import com.SpringBoot.demo.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidosControllerTest {

    @Mock
    private PedidoService service;

    @InjectMocks
    private PedidosController pedidosController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePedido() {
        PedidoDTO pedidoDTO = new PedidoDTO();
        Pedido pedido = new Pedido();
        pedido.setId(1);
        when(service.salvar(any(PedidoDTO.class))).thenReturn(pedido);

        Integer result = pedidosController.save(pedidoDTO);

        assertNotNull(result);
        assertEquals(1, result);
    }

    @Test
    void testGetPedidoById() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        Cliente cliente = new Cliente();
        cliente.setCpf("12345678901");
        cliente.setNome("Test Client");
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDate.now());
        pedido.setTotal(new BigDecimal("100.00"));
        pedido.setStatus(StatusPedido.CONFIRMADO_E_REALIZADO);
        ItemPedido itemPedido = new ItemPedido();
        Produto produto = new Produto();
        produto.setDescricao("Test Product");
        produto.setPrecoUnitario(new BigDecimal("50.00"));
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(2);
        pedido.setItens(Arrays.asList(itemPedido));
        when(service.obterPedidoCompleto(1)).thenReturn(Optional.of(pedido));

        var result = pedidosController.getById(1);

        assertNotNull(result);
        assertEquals(pedido.getId(), result.getCodigo());
        assertEquals(pedido.getCliente().getCpf(), result.getCpf());
        assertEquals(pedido.getCliente().getNome(), result.getNomeCliente());
        assertEquals(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), result.getData());
        assertEquals(pedido.getTotal(), result.getTotal());
        assertEquals(pedido.getStatus().name(), result.getStatus());
        assertEquals(1, result.getItems().size());
    }

    @Test
    void testGetPedidoByIdNotFound() {
        when(service.obterPedidoCompleto(1)).thenReturn(Optional.empty());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> {
            pedidosController.getById(1);
        });

        assertEquals("Pedido não Encontrado", exception.getMessage());
    }

    @Test
    void testUpdateStatus() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setStatus(StatusPedido.CONFIRMADO_E_REALIZADO);

        when(service.obterPedidoCompleto(1)).thenReturn(Optional.of(pedido));

        AtualizacaoStatusPedidoDTO dto = new AtualizacaoStatusPedidoDTO();
        dto.setNovoStatus("CONFIRMADO_E_REALIZADO");

        assertDoesNotThrow(() -> pedidosController.updateStatus(1, dto));

        verify(service, times(1)).atualizaStatus(1, StatusPedido.CONFIRMADO_E_REALIZADO);
        assertEquals(StatusPedido.CONFIRMADO_E_REALIZADO, pedido.getStatus());
    }

    @Test
    void testSaveItemPedido() {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(1);
        itemPedido.setQuantidade(2);
        Produto produto = new Produto();
        produto.setId(1);
        produto.setDescricao("Test Product");
        produto.setPrecoUnitario(new BigDecimal("50.00"));
        itemPedido.setProduto(produto);
        Pedido pedido = new Pedido();
        pedido.setId(1);
        itemPedido.setPedido(pedido);

        assertNotNull(itemPedido);
        assertEquals(1, itemPedido.getId());
        assertEquals(2, itemPedido.getQuantidade());
        assertEquals(produto, itemPedido.getProduto());
        assertEquals(pedido, itemPedido.getPedido());
    }

    @Test
    void testGetItemPedido() {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(1);
        itemPedido.setQuantidade(2);
        Produto produto = new Produto();
        produto.setId(1);
        produto.setDescricao("Test Product");
        produto.setPrecoUnitario(new BigDecimal("50.00"));
        itemPedido.setProduto(produto);
        Pedido pedido = new Pedido();
        pedido.setId(1);
        itemPedido.setPedido(pedido);

        assertNotNull(itemPedido);
        assertEquals(1, itemPedido.getId());
        assertEquals(2, itemPedido.getQuantidade());
        assertEquals(produto, itemPedido.getProduto());
        assertEquals(pedido, itemPedido.getPedido());
    }
}