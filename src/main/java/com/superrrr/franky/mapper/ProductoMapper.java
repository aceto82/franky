package com.superrrr.franky.mapper;

import com.superrrr.franky.dto.ProductoRequestDto;
import com.superrrr.franky.dto.ProductoResponseDto;
import com.superrrr.franky.model.Producto;

public class ProductoMapper {

    public static ProductoResponseDto toDTO(Producto producto){
        return ProductoResponseDto.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .categoria(producto.getCategoria())
                .precio(producto.getPrecio())
                .estadoProducto(producto.getEstadoProducto())
                .build();
    }

    public static Producto toModel(ProductoRequestDto productoRequestDto){
        return Producto.builder()
                .nombre(productoRequestDto.getNombre())
                .categoria(productoRequestDto.getCategoria())
                .precio(productoRequestDto.getPrecio())
                .build();
    }
}
