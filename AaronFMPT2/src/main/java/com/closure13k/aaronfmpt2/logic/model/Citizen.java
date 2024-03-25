package com.closure13k.aaronfmpt2.logic.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "Ciudadanos")
@Table(uniqueConstraints = {
    @UniqueConstraint(name = "UQ_Ciudadanos_dninif", columnNames = {"dni_nif"})
})
@NamedQueries({
    @NamedQuery(name = "Ciudadanos.buscarPorNif",
            query = "SELECT c FROM Ciudadanos AS c WHERE c.nif = :nif"
    ),})
public class Citizen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dni_nif",
            columnDefinition = "CHAR(9) NOT NULL")
    private String nif;

    //<editor-fold defaultstate="collapsed" desc="Constructor, Getters y Setters...">
    public Citizen() {
    }

    public Citizen(String nif) {
        this.nif = nif.trim().toUpperCase();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    //</editor-fold>
}
