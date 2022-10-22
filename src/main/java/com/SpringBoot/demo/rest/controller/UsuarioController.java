package com.SpringBoot.demo.rest.controller;

import com.SpringBoot.demo.Exception.SenhaInvalidaException;
import com.SpringBoot.demo.domain.entidades.Usuario;
import com.SpringBoot.demo.rest.controller.dto.CredenciaisDTO;
import com.SpringBoot.demo.rest.controller.dto.TokenDTO;
import com.SpringBoot.demo.security.jwt.JwtAuthFilte;
import com.SpringBoot.demo.security.jwt.JwtService;
import com.SpringBoot.demo.service.impl.UsuarioServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;


    public UsuarioController(UsuarioServiceImpl usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioService = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar um Novo Usuario")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuario salvo com sucesso"),
            @ApiResponse(code = 400,message = "Erro de validação, Observe o Json e veja se está tudo ok, ou está faltando algo.")
    })
    public Usuario salvar( @RequestBody @Valid Usuario usuario ){
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioService.salvar(usuario);
    }
    @PostMapping("/auth")
    @ApiOperation("Autenticar um Usuario. Para receber seu Token (Bearer)")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Usuario usuario = new Usuario();
            usuario.setLogin(credenciais.getLogin());
            usuario.setSenha(credenciais.getSenha());
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}


