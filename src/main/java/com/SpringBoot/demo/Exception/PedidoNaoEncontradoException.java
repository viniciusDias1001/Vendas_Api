package com.SpringBoot.demo.Exception;

public class PedidoNaoEncontradoException extends RuntimeException{

    public PedidoNaoEncontradoException(){
        super("Pedido não Encontrado");
    }
}
