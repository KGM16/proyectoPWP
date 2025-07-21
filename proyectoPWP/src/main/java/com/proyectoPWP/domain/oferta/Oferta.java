package com.proyectoPWP.domain.oferta;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "ofertas")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer porcentajeDescuento;

    private boolean activo;

    private LocalDate fechaFin;

    public Oferta() {}
}
