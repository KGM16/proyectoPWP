package com.proyectoPWP.domain.ejercicio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ejercicios")
public class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String grupoMuscular;

    @Column(length = 500)
    private String videoUrl;

    public Ejercicio() {}
}
