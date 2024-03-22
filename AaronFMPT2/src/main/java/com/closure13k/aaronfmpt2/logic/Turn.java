package com.closure13k.aaronfmpt2.logic;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity(name = "Turnos")
@NamedQueries({
    @NamedQuery(name = "Turnos.listarPorFecha",
            query = "SELECT t FROM Turnos AS t WHERE t.date = :date"),
    @NamedQuery(name = "Turnos.listarPorFechaYTramite",
            query = "SELECT t FROM Turnos AS t WHERE t.date = :date AND t.procedure.id = :procedureId"),})
public class Turn implements Serializable {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ciudadanos_id")
    private Citizen citizen;

    @ManyToOne
    @JoinColumn(name = "tramites_id")
    private Procedure procedure;

    @Column(name = "fecha_hora")
    private LocalDate date;

    @Column(name = "pendiente")
    private Boolean pending;

    //<editor-fold defaultstate="collapsed" desc="Constructor, Getters y Setters...">
    public Turn() {
    }

    public Turn(Citizen citizen, Procedure procedure, Boolean status) {
        this.citizen = citizen;
        this.procedure = procedure;
        this.date = LocalDate.now();
        this.pending = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDateTime(LocalDate dateTime) {
        this.date = dateTime;
    }

    public Boolean getStatus() {
        return pending;
    }

    public void setStatus(Boolean status) {
        this.pending = status;
    }
    //</editor-fold>

}
