package com.SpringBoot.demo.Testes;


import com.SpringBoot.demo.domain.entidades.Produto;
import com.SpringBoot.demo.domain.entidades.Usuario;
import com.SpringBoot.demo.domain.entidades.repository.Produtos;
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
class ProdutoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarios;

    @Autowired
    private Produtos produtos;

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
    void testAllProduto() throws Exception {
        String produtoJson = "{\"descricao\":\"Test Produto\",\"precoUnitario\":10.00}";

        mockMvc.perform(post("/api/produto")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(produtoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao").value("Test Produto"));

        mockMvc.perform(get("/api/produto/{id}", 1)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Test Produto"));

        mockMvc.perform(delete("/api/produto/delete/{id}", 1)
                        .header("Authorization", token))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/produto/{id}", 1)
                        .header("Authorization", token))
                .andExpect(status().isNotFound());
    }
}
