package com.proyectoPWP.domain.clase;

import com.proyectoPWP.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "clases_matriculadas")
public class ClaseMatriculada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Clase clase;

    private boolean activo = true;
}
