package com.proyectoPWP.domain.sede;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "sedes")
public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String direccion;

    private String telefono;
    
    private String email;
    
    private String ciudad;
    
    private String codigoPostal;
    
    @Column(length = 1000)
    private String descripcion;
    
    private String horarioApertura;
    
    private String horarioCierre;
    
    private Integer capacidadMaxima;
    
    private boolean activa = true;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion = new Date();
    
    // Coordenadas para mapas
    private Double latitud;
    
    private Double longitud;
    
    // Servicios disponibles
    private boolean tieneEstacionamiento = false;
    
    private boolean tieneVestidores = true;
    
    private boolean tieneDuchas = true;
    
    private boolean tieneWifi = false;
    
    private boolean tieneCafeteria = false;

    public Sede() {}
    
    public String getDireccionCompleta() {
        return direccion + ", " + ciudad + " " + codigoPostal;
    }
}
