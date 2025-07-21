package com.proyectoPWP.domain.entrenador;

import com.proyectoPWP.domain.empleado.Empleado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("ENTRENADOR")
@Getter
@Setter
public class Entrenador extends Empleado {

    private String especialidad;

    private Integer aniosExperiencia;

    public Entrenador() {
        super();
    }
}
