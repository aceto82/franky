package com.superrrr.franky.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Credenciales de inicio de sesión")
public class LoginRequestDto {

    @NotBlank
    @Schema(description = "Nombre de usuario", example = "admin")
    private String username;

    @NotBlank
    @Schema(description = "Contraseña del usuario", example = "123456")
    private String password;
}
