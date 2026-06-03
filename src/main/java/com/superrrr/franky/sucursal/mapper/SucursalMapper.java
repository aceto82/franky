package com.superrrr.franky.sucursal.mapper;

import com.superrrr.franky.sucursal.dto.SucursalRequestDto;
import com.superrrr.franky.sucursal.dto.SucursalResponseDto;
import com.superrrr.franky.sucursal.entity.Sucursal;

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
