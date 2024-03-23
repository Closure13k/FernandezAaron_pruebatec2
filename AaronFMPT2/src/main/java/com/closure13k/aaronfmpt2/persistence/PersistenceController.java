package com.closure13k.aaronfmpt2.persistence;

import com.closure13k.aaronfmpt2.logic.model.Citizen;
import com.closure13k.aaronfmpt2.logic.model.Procedure;
import com.closure13k.aaronfmpt2.logic.model.Turn;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenceController {

    private static PersistenceController instance;

    CitizenJpaController citizenJpa = new CitizenJpaController();
    ProcedureJpaController procJpa = new ProcedureJpaController();
    TurnJpaController turnJpa = new TurnJpaController();

    //<editor-fold defaultstate="collapsed" desc="Singleton">    
    public static PersistenceController getInstance() {
        if (instance == null) {
            instance = new PersistenceController();
        }
        return instance;
    }

    private PersistenceController() {
        if (instance != null) {
            throw new IllegalStateException();
        }
    }
    //</editor-fold>

    public Optional<Citizen> fetchCitizen(String nif) {
        return Optional.ofNullable(citizenJpa.findCitizenByNif(nif));
    }

    public List<Procedure> fetchAllProcedures() {
        return procJpa.findProcedureEntities();
    }

    public Optional<Procedure> fetchProcedure(Long id) {
        return Optional.ofNullable(procJpa.findProcedure(id));
    }

    public void createTurn(Turn turn) {
        try {
            turnJpa.create(turn);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void createCitizen(Citizen citizen) {
        citizenJpa.create(citizen);
    }

    public List<Turn> fetchAllTurns() {
        return turnJpa.findTurnEntities();
    }

}
