package com.superrrr.franky.auth.service;

import com.superrrr.franky.auth.dto.LoginRequestDto;
import com.superrrr.franky.auth.dto.LoginResponseDto;
import com.superrrr.franky.auth.entity.Usuario;
import com.superrrr.franky.auth.enums.EstadoUsuario;
import com.superrrr.franky.auth.exception.CredencialesInvalidasException;
import com.superrrr.franky.auth.mapper.UsuarioMapper;
import com.superrrr.franky.auth.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto request) {
        Usuario usuario = usuarioRepository
                .findByUsernameAndEstadoUsuarioNot(request.getUsername(), EstadoUsuario.ELIMINADO)
                .orElseThrow(() -> new CredencialesInvalidasException("Credenciales invalidas"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new CredencialesInvalidasException("Credenciales invalidas");
        }

        String token = jwtTokenProvider.generateToken(usuario);
        return UsuarioMapper.toLoginResponse(token);
    }
}
