// src/main/java/com/proyectoPWP/domain/empleado/Empleado.java
package com.proyectoPWP.domain.empleado;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
public abstract class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long idEmpleado;
    
    protected String nombre;
    protected String apellido;
    protected String email;
    protected String telefono;
    
    public Empleado() {
    }
}