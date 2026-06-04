package com.superrrr.franky.auth.mapper;

import com.superrrr.franky.auth.dto.LoginResponseDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    @Test
    void toLoginResponse_ShouldReturnDtoWithToken() {
        LoginResponseDto dto = UsuarioMapper.toLoginResponse("jwt-token-123");

        assertNotNull(dto);
        assertEquals("jwt-token-123", dto.getToken());
    }

    @Test
    void toLoginResponse_ShouldHandleEmptyToken() {
        LoginResponseDto dto = UsuarioMapper.toLoginResponse("");

        assertEquals("", dto.getToken());
    }
}
