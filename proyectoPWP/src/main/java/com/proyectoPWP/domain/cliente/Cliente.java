package com.proyectoPWP.domain.cliente;

import com.proyectoPWP.domain.usuario.Usuario;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("CLIENTE")
@Getter
@Setter
public class Cliente extends Usuario {

    private String direccion;

    private String cedula;

    public Cliente() {
        super();
    }
}
