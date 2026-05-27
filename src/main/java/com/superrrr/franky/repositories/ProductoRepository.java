package com.superrrr.franky.repositories;

import com.superrrr.franky.enums.EstadoProducto;
import com.superrrr.franky.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByEstadoProductoNot(EstadoProducto estadoProducto);
    Optional<Producto> findByIdAndEstadoProductoNot(Long id, EstadoProducto estadoProducto);

}
