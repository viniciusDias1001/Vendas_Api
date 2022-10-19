package com.SpringBoot.demo.Exception;

public class RegraNegocioException extends  RuntimeException{
    public RegraNegocioException(String msg){
        super(msg);
    }
}
