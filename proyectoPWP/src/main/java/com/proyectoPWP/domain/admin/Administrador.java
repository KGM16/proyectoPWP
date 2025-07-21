// src/main/java/com/proyectoPWP/domain/admin/Administrador.java
package com.proyectoPWP.domain.admin;

import com.proyectoPWP.domain.empleado.Empleado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("ADMIN")
@Getter @Setter
public class Administrador extends Empleado {
    private String departamento;

    public Administrador() {
        super();
    }
}
