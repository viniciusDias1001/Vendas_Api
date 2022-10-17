package com.SpringBoot.demo;


import com.SpringBoot.demo.entidades.Cliente;
import com.SpringBoot.demo.entidades.Pedido;
import com.SpringBoot.demo.entidades.Produto;
import com.SpringBoot.demo.repository.Clientes;
import com.SpringBoot.demo.repository.ClientesJPA;
import com.SpringBoot.demo.repository.Pedidos;
import com.SpringBoot.demo.repository.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class CursoSpringApplication {


	public static void main(String[] args) {
		SpringApplication.run(CursoSpringApplication.class, args);


	}

}
