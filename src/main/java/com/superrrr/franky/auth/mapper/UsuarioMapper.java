package com.superrrr.franky.auth.mapper;

import com.superrrr.franky.auth.dto.LoginResponseDto;
import com.superrrr.franky.auth.entity.Usuario;

public class UsuarioMapper {

    public static LoginResponseDto toLoginResponse(String token) {
        return LoginResponseDto.builder()
                .token(token)
                .build();
    }
}
