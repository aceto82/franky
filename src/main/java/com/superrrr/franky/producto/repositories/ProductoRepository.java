package com.superrrr.franky.producto.repositories;

import com.superrrr.franky.producto.entity.Producto;
import com.superrrr.franky.producto.enums.EstadoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByEstadoProductoNot(EstadoProducto estadoProducto);

    Optional<Producto> findByIdAndEstadoProductoNot(Long id, EstadoProducto estadoProducto);
}
