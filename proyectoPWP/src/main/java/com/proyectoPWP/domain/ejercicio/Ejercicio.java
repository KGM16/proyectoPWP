package com.proyectoPWP.domain.ejercicio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "ejercicios")
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    private String grupoMuscular; // PECHO, ESPALDA, PIERNAS, BRAZOS, HOMBROS, CORE, CARDIO

    private String equipoNecesario;
    
    private String categoria; // FUERZA, CARDIO, FLEXIBILIDAD, FUNCIONAL
    
    private String nivel; // PRINCIPIANTE, INTERMEDIO, AVANZADO
    
    @Column(length = 2000)
    private String instrucciones;
    
    private Integer duracionSegundos;
    
    private Integer calorias; // Calorías aproximadas por ejercicio
    
    private String urlVideo; // URL del video demostrativo
    
    private String urlImagen; // URL de la imagen del ejercicio
    
    private boolean activo = true;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion = new Date();
    
    // Métricas del ejercicio
    private Integer seriesRecomendadas;
    
    private Integer repeticionesRecomendadas;
    
    private Integer descansoSegundos;
    
    // Músculos trabajados (separados por comas)
    @Column(length = 500)
    private String musculosPrimarios;
    
    @Column(length = 500)
    private String musculosSecundarios;

    public Ejercicio() {}
    
    public String getInformacionCompleta() {
        return nombre + " - " + grupoMuscular + " (" + nivel + ")";
    }
}
