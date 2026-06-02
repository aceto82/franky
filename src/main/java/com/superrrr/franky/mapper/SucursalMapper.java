package com.superrrr.franky.mapper;

import com.superrrr.franky.dto.SucursalRequestDto;
import com.superrrr.franky.dto.SucursalResponseDto;
import com.superrrr.franky.model.Sucursal;

public class SucursalMapper {

    public static SucursalResponseDto toDTO(Sucursal sucursal){
        return SucursalResponseDto.builder()
                .id(sucursal.getId())
                .nombre(sucursal.getNombre())
                .direccion(sucursal.getDireccion())
                .telefono(sucursal.getTelefono())
                .estadoSucursal(sucursal.getEstadoSucursal())
                .build();
    }

    public static Sucursal toModel(SucursalRequestDto sucursalRequestDto){
        return Sucursal.builder()
                .nombre(sucursalRequestDto.getNombre())
                .direccion(sucursalRequestDto.getDireccion())
                .telefono(sucursalRequestDto.getTelefono())
                .build();
    }
}
