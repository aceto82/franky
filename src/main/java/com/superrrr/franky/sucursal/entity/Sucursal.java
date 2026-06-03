package com.superrrr.franky.sucursal.entity;

import com.superrrr.franky.sucursal.enums.EstadoSucursal;
import com.superrrr.franky.venta.entity.Venta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sucursales")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
