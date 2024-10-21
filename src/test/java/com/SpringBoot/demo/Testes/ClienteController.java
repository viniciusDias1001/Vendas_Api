package com.SpringBoot.demo.Testes;



import com.SpringBoot.demo.domain.entidades.Cliente;
import com.SpringBoot.demo.domain.entidades.repository.Clientes;
import com.SpringBoot.demo.rest.controller.ClienteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    private Clientes clientes;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClienteById() {
        Cliente cliente = new Cliente(1, "Test", "12345678901");
        when(clientes.findById(1)).thenReturn(Optional.of(cliente));

        Cliente result = clienteController.getClienteById(1);

        assertNotNull(result);
        assertEquals(cliente.getId(), result.getId());
        assertEquals(cliente.getNome(), result.getNome());
        assertEquals(cliente.getCpf(), result.getCpf());
    }

    @Test
    void testGetClienteByIdNotFound() {
        when(clientes.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clienteController.getClienteById(1);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testSaveCliente() {
        Cliente cliente = new Cliente(1, "Test", "12345678901");
        when(clientes.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteController.save(cliente);

        assertNotNull(result);
        assertEquals(cliente.getId(), result.getId());
        assertEquals(cliente.getNome(), result.getNome());
        assertEquals(cliente.getCpf(), result.getCpf());
    }

    @Test
    void testDeleteCliente() {
        Cliente cliente = new Cliente(1, "Test", "12345678901");
        when(clientes.findById(1)).thenReturn(Optional.of(cliente));
        doNothing().when(clientes).delete(cliente);

        assertDoesNotThrow(() -> clienteController.delete(1));
        verify(clientes, times(1)).delete(cliente);
    }

    @Test
    void testDeleteClienteNotFound() {
        when(clientes.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clienteController.delete(1);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testUpdateCliente() {
        Cliente cliente = new Cliente(1, "Test", "12345678901");
        when(clientes.findById(1)).thenReturn(Optional.of(cliente));
        when(clientes.save(any(Cliente.class))).thenReturn(cliente);

        Cliente updatedCliente = new Cliente(1, "Updated Test", "12345678901");
        assertDoesNotThrow(() -> clienteController.update(1, updatedCliente));
        verify(clientes, times(1)).save(updatedCliente);
    }

    @Test
    void testUpdateClienteNotFound() {
        when(clientes.findById(1)).thenReturn(Optional.empty());

        Cliente updatedCliente = new Cliente(1, "Updated Test", "12345678901");
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clienteController.update(1, updatedCliente);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testFindClientes() {
        Cliente cliente1 = new Cliente(1, "Test1", "12345678901");
        Cliente cliente2 = new Cliente(2, "Test2", "12345678902");
        List<Cliente> clientesList = Arrays.asList(cliente1, cliente2);
        when(clientes.findAll(any(Example.class))).thenReturn(clientesList);

        List<Cliente> result = clienteController.find(new Cliente());

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}