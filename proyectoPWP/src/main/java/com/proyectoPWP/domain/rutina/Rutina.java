package com.proyectoPWP.domain.rutina;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rutinas")
public class Rutina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String nivel;

    private Integer duracionMin;

    @Column(length = 500)
    private String descripcion;

    public Rutina() {}
}
