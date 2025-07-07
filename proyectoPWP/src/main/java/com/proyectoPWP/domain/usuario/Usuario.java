// src/main/java/com/proyectoPWP/domain/usuario/Usuario.java
package com.proyectoPWP.domain.usuario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "usuarios")
@Getter @Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    private Long idRol;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    
    private String genero;
    private String contrasena;
    private String objetivoEntrenamiento;
    private boolean newsletter;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    
    private boolean activo = true;
    
    public Usuario() {
        this.fechaRegistro = new Date();
    }
}