package com.SpringBoot.demo.rest.controller;

import com.SpringBoot.demo.Exception.RegraNegocioException;
import com.SpringBoot.demo.domain.entidades.ItemPedido;
import com.SpringBoot.demo.domain.entidades.Pedido;
import com.SpringBoot.demo.domain.entidades.enums.StatusPedido;
import com.SpringBoot.demo.rest.controller.dto.AtualizacaoStatusPedidoDTO;
import com.SpringBoot.demo.rest.controller.dto.InformacoesItemPedidosDTO;
import com.SpringBoot.demo.rest.controller.dto.InformacoesPedidosDTO;
import com.SpringBoot.demo.rest.controller.dto.PedidoDTO;
import com.SpringBoot.demo.service.PedidoService;
import io.swagger.annotations.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/pedidos")
@Api("Api Pedidos")
public class PedidosController {

    private PedidoService service;

    public PedidosController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Fazer um novo Pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido feito com sucesso"),
            @ApiResponse(code = 400,message = "Erro de validação, Observe o Json e veja se está tudo ok, ou está faltando algo.")
    })
    public Integer save( @RequestBody @Valid PedidoDTO dto ){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("/{id}")
    @ApiOperation("Buscar Pedido por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Busca feita com sucesso"),
            @ApiResponse(code = 404,message = "Pedido não encontrado no nosso DataBase, para o ID informado.")
    })
    public InformacoesPedidosDTO getById(@PathVariable @ApiParam("Id do Pedido") Integer id){
       return service.obterPedidoCompleto(id).map(pedido -> converter(pedido)).orElseThrow(() -> new RegraNegocioException("Pedido não Encontrado"));

    }

    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Atualizar Informações do Pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido Atualizado com sucesso"),
            @ApiResponse(code = 404,message = "Pedido não encontrado no nosso DataBase, para o ID informado.")
    })
    public  void updateStatus(@PathVariable  @ApiParam("Id do Pedido") Integer id,@RequestBody AtualizacaoStatusPedidoDTO atualizacaoStatusPedidoDTO){

        String novoStatus = atualizacaoStatusPedidoDTO.getNovoStatus();
          service.atualizaStatus(id,StatusPedido.valueOf(novoStatus));
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