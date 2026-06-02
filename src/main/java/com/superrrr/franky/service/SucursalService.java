package com.superrrr.franky.service;

import com.superrrr.franky.dto.SucursalRequestDto;
import com.superrrr.franky.dto.SucursalResponseDto;
import com.superrrr.franky.enums.EstadoSucursal;
import com.superrrr.franky.exception.SucursalNoEncontradoException;
import com.superrrr.franky.mapper.SucursalMapper;
import com.superrrr.franky.model.Sucursal;
import com.superrrr.franky.repositories.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<SucursalResponseDto> obtenerSucursales(){
        List<Sucursal> sucursales = sucursalRepository.findByEstadoSucursalNot(EstadoSucursal.ELIMINADO);
        return sucursales.stream().map(SucursalMapper::toDTO).toList();
    }

    public SucursalResponseDto crearSucursal(SucursalRequestDto sucursalRequestDto){
        Sucursal sucursal = SucursalMapper.toModel(sucursalRequestDto);
        sucursal.setEstadoSucursal(EstadoSucursal.ACTIVO);
        Sucursal sucursalNuevo = sucursalRepository.save(sucursal);
        return SucursalMapper.toDTO(sucursalNuevo);
    }

    public SucursalResponseDto actualizarSucursal(Long id, SucursalRequestDto sucursalRequestDto){
        Sucursal sucursal = sucursalRepository.findByIdAndEstadoSucursalNot(id, EstadoSucursal.ELIMINADO)
                .orElseThrow(()->new SucursalNoEncontradoException("Sucursal no encontrado con el ID: ".concat(String.valueOf(id))));

        if (Objects.nonNull(sucursalRequestDto.getNombre())){
            sucursal.setNombre(sucursalRequestDto.getNombre());
        }
        if (Objects.nonNull(sucursalRequestDto.getDireccion())){
            sucursal.setDireccion(sucursalRequestDto.getDireccion());
        }
        if (Objects.nonNull(sucursalRequestDto.getTelefono())){
            sucursal.setTelefono(sucursalRequestDto.getTelefono());
        }

        Sucursal sucursalActualizado = sucursalRepository.save(sucursal);
        return SucursalMapper.toDTO(sucursalActualizado);
    }

    public void borrarSucursal(Long id){
        Sucursal sucursal = sucursalRepository.findByIdAndEstadoSucursalNot(id, EstadoSucursal.ELIMINADO)
                .orElseThrow(()->new SucursalNoEncontradoException("Sucursal no encontrado con el ID: ".concat(String.valueOf(id))));

        sucursal.setEstadoSucursal(EstadoSucursal.ELIMINADO);
        sucursalRepository.save(sucursal);
    }
}
