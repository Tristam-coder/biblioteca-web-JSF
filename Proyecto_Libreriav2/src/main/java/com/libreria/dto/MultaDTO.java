package com.libreria.dto;

import com.libreria.model.Multa;
import java.time.LocalDate;

public class MultaDTO {

    private Integer id;
    private Integer usuarioId;
    private Integer prestamoId;
    private Double monto;
    private String motivo;
    private String estado;
    private LocalDate fechaGeneracion;
    private LocalDate fechaPago;

    // --- Constructor vacío ---
    public MultaDTO() {
    }

    // --- Constructor para mapear Entidad a DTO ---
    public MultaDTO(Multa multa) {
        this.id = multa.getId();
        this.monto = multa.getMonto();
        this.motivo = multa.getMotivo();
        this.estado = multa.getEstado();
        this.fechaGeneracion = multa.getFechaGeneracion();
        this.fechaPago = multa.getFechaPago();

        // Mapear IDs de relaciones
        if (multa.getUsuario() != null) {
            this.usuarioId = multa.getUsuario().getId();
        }
        if (multa.getPrestamo() != null) {
            this.prestamoId = multa.getPrestamo().getId();
        }
    }

    // --- Mapear DTO a Entidad (Sin relaciones, las relaciones se setean en el Service) ---
    public Multa toEntity() {
        Multa m = new Multa();
        m.setId(this.id);
        m.setMonto(this.monto);
        m.setMotivo(this.motivo);
        m.setEstado(this.estado);
        m.setFechaGeneracion(this.fechaGeneracion);
        m.setFechaPago(this.fechaPago);
        // Las entidades Usuario y Prestamo se configuran en el Service
        return m;
    }

    // --- Getters y Setters ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public Integer getPrestamoId() { return prestamoId; }
    public void setPrestamoId(Integer prestamoId) { this.prestamoId = prestamoId; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(LocalDate fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }

    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }
}