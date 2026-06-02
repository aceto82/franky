package com.superrrr.franky.repositories;

import com.superrrr.franky.enums.EstadoSucursal;
import com.superrrr.franky.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal,Long> {

    List<Sucursal> findByEstadoSucursalNot(EstadoSucursal estadoSucursal);

    Optional<Sucursal> findByIdAndEstadoSucursalNot(Long id, EstadoSucursal estadoSucursal);
}
