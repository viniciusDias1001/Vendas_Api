package com.SpringBoot.demo.Testes;



import com.SpringBoot.demo.domain.entidades.Cliente;
import com.SpringBoot.demo.domain.entidades.Usuario;
import com.SpringBoot.demo.domain.entidades.repository.Clientes;
import com.SpringBoot.demo.domain.entidades.repository.UsuarioRepository;
import com.SpringBoot.demo.rest.controller.dto.CredenciaisDTO;
import com.SpringBoot.demo.rest.controller.dto.TokenDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarios;

    @Autowired
    private Clientes clientes;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String token;

    @BeforeEach
    void setUp() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setLogin("testuser");
        usuario.setSenha(passwordEncoder.encode("password"));
        usuario.setAdmin(false);
        usuarios.save(usuario);


        CredenciaisDTO credenciais = new CredenciaisDTO();
        credenciais.setLogin("testuser");
        credenciais.setSenha("password");

        String response = mockMvc.perform(post("/api/usuarios/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(credenciais)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        TokenDTO tokenDTO = new ObjectMapper().readValue(response, TokenDTO.class);
        token = "Bearer " + tokenDTO.getToken();
    }

    @Test
    void testAllCliente() throws Exception {
        String clienteJson = "{\"nome\":\"Test Cliente\",\"cpf\":\"44599753866\"}";

        mockMvc.perform(post("/api/clientes")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson));


        mockMvc.perform(get("/api/clientes/{id}", 1)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Test Cliente"));

        mockMvc.perform(delete("/api/clientes/{id}", 1)
                        .header("Authorization", token))
                .andExpect(status().isNoContent());
    }

}

