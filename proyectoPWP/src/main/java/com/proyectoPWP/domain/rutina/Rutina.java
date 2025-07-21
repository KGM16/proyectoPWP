package com.proyectoPWP.domain.rutina;

import com.proyectoPWP.domain.cliente.Cliente;
import com.proyectoPWP.domain.entrenador.Entrenador;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "rutinas")
public class Rutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    private String objetivo; // PERDIDA_PESO, GANANCIA_MUSCULAR, RESISTENCIA, FUERZA, TONIFICACION

    private Integer duracionSemanas;
    
    private String nivel; // PRINCIPIANTE, INTERMEDIO, AVANZADO
    
    private Integer diasPorSemana;
    
    private Integer duracionMinutos; // Duración promedio por sesión
    
    @Column(length = 2000)
    private String notas;
    
    private boolean activa = true;
    
    private boolean esPersonalizada = false; // Si es personalizada para un cliente específico
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion = new Date();
    
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    
    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente; // Solo si es personalizada
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrenador_id")
    private Entrenador entrenador; // Quien creó la rutina
    
    // Métricas de progreso
    private Integer caloriasEstimadas;
    
    private String equipoNecesario;
    
    // Estado de la rutina
    private String estado = "ACTIVA"; // ACTIVA, PAUSADA, COMPLETADA, CANCELADA

    public Rutina() {}
    
    public boolean estaVigente() {
        Date hoy = new Date();
        return fechaInicio != null && fechaFin != null && 
               !hoy.before(fechaInicio) && !hoy.after(fechaFin);
    }
    
    public String getInformacionCompleta() {
        return nombre + " - " + objetivo + " (" + duracionSemanas + " semanas)";
    }
}
