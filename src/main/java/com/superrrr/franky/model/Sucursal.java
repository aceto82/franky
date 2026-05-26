package com.superrrr.franky.model;

import com.superrrr.franky.enums.EstadoSucursal;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sucursales")
@Data
@Builder
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_sucursal", nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_sucursal", nullable = false)
    private EstadoSucursal estadoSucursal;

    @OneToMany(mappedBy = "sucursal")
    private List<Venta> ventas = new ArrayList<>();
}
