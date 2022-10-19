package com.SpringBoot.demo.rest.controller;

import com.SpringBoot.demo.Exception.RegraNegocioException;
import com.SpringBoot.demo.domain.entidades.ItemPedido;
import com.SpringBoot.demo.domain.entidades.Pedido;
import com.SpringBoot.demo.rest.controller.dto.InformacoesItemPedidosDTO;
import com.SpringBoot.demo.rest.controller.dto.InformacoesPedidosDTO;
import com.SpringBoot.demo.rest.controller.dto.PedidoDTO;
import com.SpringBoot.demo.service.PedidoService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {

    private PedidoService service;

    public PedidosController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save( @RequestBody PedidoDTO dto ){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("/{id}")
    public InformacoesPedidosDTO getById(@PathVariable Integer id){
       return service.obterPedidoCompleto(id).map(pedido -> converter(pedido)).orElseThrow(() -> new RegraNegocioException("Pedido não Encontrado"));

    }

    private InformacoesPedidosDTO converter(Pedido pedido){
         InformacoesPedidosDTO infPedidos = new InformacoesPedidosDTO();

         infPedidos.setCodigo(pedido.getId());
         infPedidos.setCpf(pedido.getCliente().getCpf());
         infPedidos.setNomeCliente(pedido.getCliente().getNome());
         infPedidos.setData(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
         infPedidos.setTotal(pedido.getTotal());
         infPedidos.setStatus(pedido.getStatus().name());
         infPedidos.setItems(converter(pedido.getItens()));

         return infPedidos;

    }

    private List<InformacoesItemPedidosDTO> converter(List<ItemPedido> item){
        if (CollectionUtils.isEmpty(item)){
            return Collections.EMPTY_LIST;
        }

        return item.stream().map(itemm -> {
            InformacoesItemPedidosDTO infIntemPedido = new InformacoesItemPedidosDTO();

            infIntemPedido.setDescricao(itemm.getProduto().getDescricao());
            infIntemPedido.setQuantidade(itemm.getQuantidade());
            infIntemPedido.setPrecoUnitario(itemm.getProduto().getPrecoUnitario());
                   return infIntemPedido;
        }).collect(Collectors.toList());

    }



}