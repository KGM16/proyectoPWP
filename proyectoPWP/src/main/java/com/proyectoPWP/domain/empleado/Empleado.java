// src/main/java/com/proyectoPWP/domain/empleado/Empleado.java
package com.proyectoPWP.domain.empleado;

import com.proyectoPWP.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public abstract class Empleado extends Usuario {
    @Column(unique = true, length = 20)
    private String codigo;

    private String rol;

    public Empleado() {}
}
