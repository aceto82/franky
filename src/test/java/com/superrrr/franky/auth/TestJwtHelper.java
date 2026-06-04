package com.superrrr.franky.auth;

import com.superrrr.franky.auth.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class TestJwtHelper {

    private static final String TEST_SECRET = "ZmFra3ktZXktc2VjcmV0by1wYXJhLWp3dC1kZS1wcnVlYmEtdGVjbmljYS1zdXBlcm1lcmNhZG8tcHJ1ZWJhLXRlY25pY2E=";
    private static final long EXPIRATION = 86400000;
    private static final SecretKey KEY = Keys.hmacShaKeyFor(TEST_SECRET.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(Usuario usuario) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION);

        return Jwts.builder()
                .subject(usuario.getUsername())
                .claim("rol", usuario.getRol().name())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(KEY)
                .compact();
    }
}
