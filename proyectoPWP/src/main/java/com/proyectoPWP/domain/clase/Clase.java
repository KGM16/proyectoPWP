package com.proyectoPWP.domain.clase;

import com.proyectoPWP.domain.entrenador.Entrenador;
import com.proyectoPWP.domain.sede.Sede;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "clases")
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(length = 500)
    private String descripcion;

    private Integer duracionMinutos;

    private Integer cupoMaximo;
    
    private Integer cupoActual = 0;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    
    private String nivel; // PRINCIPIANTE, INTERMEDIO, AVANZADO
    
    private String categoria; // CARDIO, FUERZA, FLEXIBILIDAD, FUNCIONAL
    
    private Double precio;
    
    private boolean activa = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrenador_id")
    private Entrenador entrenador;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sede_id")
    private Sede sede;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion = new Date();

    public Clase() {}
    
    public boolean tieneCupoDisponible() {
        return cupoActual < cupoMaximo;
    }
    
    public void incrementarCupo() {
        if (tieneCupoDisponible()) {
            cupoActual++;
        }
    }
    
    public void decrementarCupo() {
        if (cupoActual > 0) {
            cupoActual--;
        }
    }
}
