package com.superrrr.franky.auth.service;

import com.superrrr.franky.auth.entity.Usuario;
import com.superrrr.franky.auth.enums.EstadoUsuario;
import com.superrrr.franky.auth.enums.Rol;
import com.superrrr.franky.auth.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!production")
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() > 0) {
            log.info("Users already exist, skipping data initialization");
            return;
        }

        Usuario admin = Usuario.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .rol(Rol.ADMIN)
                .estadoUsuario(EstadoUsuario.ACTIVO)
                .build();

        Usuario user = Usuario.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .rol(Rol.USER)
                .estadoUsuario(EstadoUsuario.ACTIVO)
                .build();

        usuarioRepository.save(admin);
        usuarioRepository.save(user);

        log.info("Initial users created: admin (ADMIN), user (USER)");
    }
}
