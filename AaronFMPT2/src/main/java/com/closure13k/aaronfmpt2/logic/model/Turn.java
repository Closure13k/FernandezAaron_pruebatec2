package com.closure13k.aaronfmpt2.logic.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity(name = "Turnos")
@NamedQueries({
    @NamedQuery(name = "Turnos.listarPorFecha",
            query = "SELECT t FROM Turnos AS t WHERE FUNCTION('DATE', t.date) = :date ORDER BY t.date ASC")
})
public class Turn implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ciudadanos_id")
    private Citizen citizen;

    @ManyToOne
    @JoinColumn(name = "tramites_id")
    private Procedure procedure;

    @Column(name = "fecha_hora")
    private LocalDateTime date;

    @Column(name = "pendiente")
    private Boolean pending;

    //<editor-fold defaultstate="collapsed" desc="Constructor, Getters y Setters...">
    public Turn() {
    }

    public Turn(Citizen citizen, Procedure procedure) {
        this.citizen = citizen;
        this.procedure = procedure;
        this.date = LocalDateTime.now();
        this.pending = Boolean.TRUE;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.date = dateTime;
    }

    public Boolean isPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }
    //</editor-fold>

}
