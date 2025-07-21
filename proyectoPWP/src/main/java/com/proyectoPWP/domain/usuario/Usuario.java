// src/main/java/com/proyectoPWP/domain/usuario/Usuario.java
package com.proyectoPWP.domain.usuario;

import jakarta.persistence.*;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;


@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String genero;
    private String contrasena;
    private String objetivoEntrenamiento;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro = new Date();

    private boolean activo = true;

    public Usuario() {
    }

   

}
