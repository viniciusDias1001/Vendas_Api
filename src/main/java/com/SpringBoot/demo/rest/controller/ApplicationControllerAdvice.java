package com.SpringBoot.demo.rest.controller;

import com.SpringBoot.demo.Exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handRegraNegocioExceptions(RegraNegocioException ex){
        String msgErro = ex.getMessage();
        return new ApiErros(msgErro);
    }

}
