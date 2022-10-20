package com.SpringBoot.demo.rest.controller;

import com.SpringBoot.demo.Exception.PedidoNaoEncontradoException;
import com.SpringBoot.demo.Exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handRegraNegocioExceptions(RegraNegocioException ex){
        String msgErro = ex.getMessage();
        return new ApiErros(msgErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErros handPedidoNaoEncontrado(PedidoNaoEncontradoException ex){
        String msgErro = ex.getMessage();
        return new ApiErros(msgErro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public  ApiErros handMethodNotValidException(MethodArgumentNotValidException ex){
       List<String> msgErro= ex.getBindingResult().getAllErrors().stream().map(erro -> erro.getDefaultMessage()).collect(Collectors.toList());

        return new ApiErros(msgErro);

    }

}
