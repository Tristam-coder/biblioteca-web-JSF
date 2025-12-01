package com.libreria.dto;

import com.libreria.model.Prestamo;
import java.math.BigDecimal;
import java.time.LocalDate; // Usamos LocalDate para fechas simples

public class PrestamoDTO {

    private Integer id;
    private Integer usuarioId;
    private Integer ejemplarId;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal;
    private String estado;
    private BigDecimal multa;
    private String observaciones;
    // createdAt generalmente no se incluye en el DTO de entrada/salida para el cliente

    // --- Constructor vacío ---
    public PrestamoDTO() {
    }

    // --- Constructor para mapear Entidad a DTO ---
    public PrestamoDTO(Prestamo prestamo) {
        this.id = prestamo.getId();
        this.fechaPrestamo = convertDateToLocalDate(prestamo.getFechaPrestamo());
        this.fechaDevolucionPrevista = convertDateToLocalDate(prestamo.getFechaDevolucionPrevista());
        this.fechaDevolucionReal = convertDateToLocalDate(prestamo.getFechaDevolucionReal());
        this.estado = prestamo.getEstado();
        this.multa = prestamo.getMulta();
        this.observaciones = prestamo.getObservaciones();

        // Mapear IDs de relaciones
        if (prestamo.getUsuario() != null) {
            this.usuarioId = prestamo.getUsuario().getId();
        }
        if (prestamo.getEjemplar() != null) {
            this.ejemplarId = prestamo.getEjemplar().getId();
        }
    }

    // Método auxiliar (necesario si la entidad usa java.util.Date)
    private LocalDate convertDateToLocalDate(java.util.Date date) {
        if (date == null) return null;
        // La conversión de Date a LocalDate requiere un paso intermedio (Instant/ZoneId).
        // Si no cambias la Entidad Prestamo a LocalDate, este método debe ser implementado
        // correctamente, o simplemente cambia java.util.Date por java.time.LocalDate en la entidad Prestamo.
        // Asumiendo que la Entidad Prestamo USA java.util.Date, necesitas una conversión real:
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    private java.util.Date convertLocalDateToDate(LocalDate localDate) {
        if (localDate == null) return null;
        return java.util.Date.from(localDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
    }

    // --- Mapear DTO a Entidad (Sin relaciones) ---
    public Prestamo toEntity() {
        Prestamo p = new Prestamo();
        p.setId(this.id);
        p.setFechaPrestamo(convertLocalDateToDate(this.fechaPrestamo));
        p.setFechaDevolucionPrevista(convertLocalDateToDate(this.fechaDevolucionPrevista));
        p.setFechaDevolucionReal(convertLocalDateToDate(this.fechaDevolucionReal));
        p.setEstado(this.estado);
        p.setMulta(this.multa);
        p.setObservaciones(this.observaciones);
        // Las entidades Usuario y Ejemplar se configuran en el Service
        return p;
    }

    // --- Getters y Setters ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public Integer getEjemplarId() { return ejemplarId; }
    public void setEjemplarId(Integer ejemplarId) { this.ejemplarId = ejemplarId; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucionPrevista() { return fechaDevolucionPrevista; }
    public void setFechaDevolucionPrevista(LocalDate fechaDevolucionPrevista) { this.fechaDevolucionPrevista = fechaDevolucionPrevista; }

    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) { this.fechaDevolucionReal = fechaDevolucionReal; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getMulta() { return multa; }
    public void setMulta(BigDecimal multa) { this.multa = multa; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}