package com.proyectoPWP.repository;

import com.proyectoPWP.domain.oferta.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfertaRepository extends JpaRepository<Oferta, Long> {
}
