package com.superrrr.franky.venta.repositories;

import com.superrrr.franky.sucursal.entity.Sucursal;
import com.superrrr.franky.venta.enums.EstadoVenta;
import com.superrrr.franky.venta.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByFechaBetweenAndSucursalAndEstadoVentaNot(Instant inicio, Instant fin, Sucursal sucursal, EstadoVenta estadoVenta);

    Optional<Venta> findByIdAndEstadoVentaNot(Long id, EstadoVenta estadoVenta);

    @Query("SELECT v FROM Venta v LEFT JOIN FETCH v.detalles d LEFT JOIN FETCH d.producto WHERE v.estadoVenta <> :estado")
    List<Venta> findAllByEstadoVentaNot(EstadoVenta estado);
}
