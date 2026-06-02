package com.superrrr.franky.repositories;

import com.superrrr.franky.enums.EstadoVenta;
import com.superrrr.franky.model.Sucursal;
import com.superrrr.franky.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByFechaBetweenAndSucursalAndEstadoVentaNot(Instant inicio, Instant fin, Sucursal sucursal, EstadoVenta estadoVenta);
}
