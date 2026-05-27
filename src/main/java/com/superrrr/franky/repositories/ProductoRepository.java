package com.superrrr.franky.repositories;

import com.superrrr.franky.enums.EstadoProducto;
import com.superrrr.franky.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByEstadoProductoNot(EstadoProducto estadoProducto);
}
