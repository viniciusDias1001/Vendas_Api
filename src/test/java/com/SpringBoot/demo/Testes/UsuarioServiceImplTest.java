package com.SpringBoot.demo.Testes;

import com.SpringBoot.demo.domain.entidades.Usuario;
import com.SpringBoot.demo.domain.entidades.repository.UsuarioRepository;
import com.SpringBoot.demo.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class UsuarioServiceImplTest {

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalvarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setLogin("test");
        usuario.setSenha("password");

        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario result = usuarioService.salvar(usuario);

        assertNotNull(result);
        assertEquals("test", result.getLogin());
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testAutenticarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setLogin("test");
        usuario.setSenha("password");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getPassword()).thenReturn("encodedPassword");
        when(encoder.matches(any(String.class), any(String.class))).thenReturn(true);
        when(repository.findByLogin(any(String.class))).thenReturn(Optional.of(usuario));

        UserDetails result = usuarioService.autenticar(usuario);

        assertNotNull(result);
        verify(encoder, times(1)).matches(any(String.class), any(String.class));
    }


    @Test
    void testAutenticarUsuarioComErro() {
        Usuario usuario = new Usuario();
        usuario.setLogin("test");
        usuario.setSenha("password");

        when(repository.findByLogin(any(String.class))).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            usuarioService.autenticar(usuario);
        });
    }

    @Test
    void testLoadUserByUsername() {
        Usuario usuario = new Usuario();
        usuario.setLogin("test");
        usuario.setSenha("password");

        when(repository.findByLogin(any(String.class))).thenReturn(Optional.of(usuario));

        UserDetails result = usuarioService.loadUserByUsername("test");

        assertNotNull(result);
        assertEquals("test", result.getUsername());
        verify(repository, times(1)).findByLogin(any(String.class));
    }

    @Test
    void testLoadUserByUsernameComErro() {
        when(repository.findByLogin(any(String.class))).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            usuarioService.loadUserByUsername("test");
        });
    }
}