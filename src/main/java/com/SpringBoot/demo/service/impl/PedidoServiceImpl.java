package com.SpringBoot.demo.service.impl;

import com.SpringBoot.demo.Exception.PedidoNaoEncontradoException;
import com.SpringBoot.demo.Exception.RegraNegocioException;
import com.SpringBoot.demo.domain.entidades.Cliente;
import com.SpringBoot.demo.domain.entidades.ItemPedido;
import com.SpringBoot.demo.domain.entidades.Pedido;
import com.SpringBoot.demo.domain.entidades.Produto;
import com.SpringBoot.demo.domain.entidades.enums.StatusPedido;
import com.SpringBoot.demo.domain.entidades.repository.Clientes;
import com.SpringBoot.demo.domain.entidades.repository.ItemsPedido;
import com.SpringBoot.demo.domain.entidades.repository.Pedidos;
import com.SpringBoot.demo.domain.entidades.repository.Produtos;
import com.SpringBoot.demo.rest.controller.dto.ItemPedidoDTO;
import com.SpringBoot.demo.rest.controller.dto.PedidoDTO;
import com.SpringBoot.demo.service.PedidoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    private  Pedidos repository;
    private  Clientes clientesRepository;
    private  Produtos produtosRepository;
    private ItemsPedido itemsPedidoRepository;

    public PedidoServiceImpl(Pedidos repository, Clientes clientesRepository, Produtos produtosRepository, ItemsPedido itemsPedidoRepository) {
        this.repository = repository;
        this.clientesRepository = clientesRepository;
        this.produtosRepository = produtosRepository;
        this.itemsPedidoRepository = itemsPedidoRepository;
    }

    @Override
    @Transactional
    public Pedido salvar( PedidoDTO dto ) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.CONFIRMADO_E_REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus( Integer id, StatusPedido statusPedido ) {
        repository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException() );
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());

    }
}
