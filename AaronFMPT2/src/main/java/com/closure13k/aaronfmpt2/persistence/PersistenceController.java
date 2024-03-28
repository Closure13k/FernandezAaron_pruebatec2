package com.closure13k.aaronfmpt2.persistence;

import com.closure13k.aaronfmpt2.logic.model.Citizen;
import com.closure13k.aaronfmpt2.logic.model.Procedure;
import com.closure13k.aaronfmpt2.logic.model.Turn;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum PersistenceController {
    INSTANCE;   //Singleton

    private final CitizenJpaController citizenJpa = new CitizenJpaController();
    private final ProcedureJpaController procJpa = new ProcedureJpaController();
    private final TurnJpaController turnJpa = new TurnJpaController();

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

    public List<Turn> fetchTurnsByDate(LocalDate date) {
        return turnJpa.findAllByDate(date);
    }

    public Optional<Turn> fetchTurn(Long id) {
        return Optional.ofNullable(turnJpa.findTurn(id));
    }

    public void updateTurn(Turn turn) {
        try {
            turnJpa.edit(turn);
        } catch (Exception ex) {
            Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
