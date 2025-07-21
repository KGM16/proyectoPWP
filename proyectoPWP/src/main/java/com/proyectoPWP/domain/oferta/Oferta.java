package com.proyectoPWP.domain.oferta;

import com.proyectoPWP.domain.plan.Plan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "ofertas")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    private Integer descuentoPorcentaje;
    
    private Double descuentoFijo; // Descuento en cantidad fija
    
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    
    private boolean activa = true;
    
    private String codigoPromocional; // Código opcional para la oferta
    
    private Integer usosMaximos; // Límite de usos de la oferta
    
    private Integer usosActuales = 0;
    
    private String tipoOferta; // PORCENTAJE, FIJO, PRIMERA_MENSUALIDAD_GRATIS, DOS_POR_UNO
    
    @Column(length = 500)
    private String condiciones; // Condiciones de la oferta
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion = new Date();
    
    // Relación con planes (opcional - si la oferta es para planes específicos)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan planEspecifico;
    
    // Configuración de aplicabilidad
    private boolean aplicaANuevosClientes = true;
    
    private boolean aplicaAClientesExistentes = false;
    
    private Double montoMinimoCompra; // Monto mínimo para aplicar la oferta

    public Oferta() {}
    
    public boolean estaVigente() {
        Date hoy = new Date();
        return activa && fechaInicio != null && fechaFin != null &&
               !hoy.before(fechaInicio) && !hoy.after(fechaFin);
    }
    
    public boolean puedeUsarse() {
        return estaVigente() && (usosMaximos == null || usosActuales < usosMaximos);
    }
    
    public void incrementarUsos() {
        if (puedeUsarse()) {
            usosActuales++;
        }
    }
    
    public Double calcularDescuento(Double montoOriginal) {
        if (!puedeUsarse()) return 0.0;
        
        if (montoMinimoCompra != null && montoOriginal < montoMinimoCompra) {
            return 0.0;
        }
        
        if ("PORCENTAJE".equals(tipoOferta) && descuentoPorcentaje != null) {
            return montoOriginal * (descuentoPorcentaje / 100.0);
        } else if ("FIJO".equals(tipoOferta) && descuentoFijo != null) {
            return Math.min(descuentoFijo, montoOriginal);
        }
        
        return 0.0;
    }
}
