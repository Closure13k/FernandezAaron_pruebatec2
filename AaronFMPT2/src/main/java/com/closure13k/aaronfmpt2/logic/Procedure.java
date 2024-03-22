package com.closure13k.aaronfmpt2.logic;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "Tramites")
@Table(uniqueConstraints = {
    @UniqueConstraint(name = "UQ_Tramites_descripcion", columnNames = {"descripcion"})
})
public class Procedure implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String description;

    //<editor-fold defaultstate="collapsed" desc="Constructor, Getters y Setters...">
    public Procedure() {
    }

    public Procedure(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //</editor-fold>
}
