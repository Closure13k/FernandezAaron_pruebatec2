package com.closure13k.aaronfmpt2.logic;

import com.closure13k.aaronfmpt2.logic.model.Citizen;
import com.closure13k.aaronfmpt2.logic.model.Procedure;
import com.closure13k.aaronfmpt2.logic.model.Turn;
import com.closure13k.aaronfmpt2.persistence.PersistenceController;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Controller {

    private static Controller instance;

    private final PersistenceController pCon = PersistenceController.getInstance();

    //<editor-fold defaultstate="collapsed" desc="Singleton">
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    private Controller() {
        if (instance != null) {
            throw new IllegalStateException();
        }
    }
    //</editor-fold>

    public Citizen fetchCitizen(String nif) {
        return pCon.fetchCitizen(nif)
                .orElse(new Citizen(nif));
    }

    public Procedure fetchProcedure(Long id) {
        return pCon.fetchProcedure(id)
                .orElseThrow();
    }

    public List<Procedure> fetchAllProcedures() {
        return pCon.fetchAllProcedures();
    }

    public void createCitizen(Citizen citizen) {
        pCon.createCitizen(citizen);
    }

    public void createTurn(Turn turn) {
        final Citizen citizen = turn.getCitizen();
        if (Objects.isNull(citizen.getId())) {
            pCon.createCitizen(citizen);
        }
        pCon.createTurn(turn);
    }

    public List<Turn> fetchAllTurns() {
        return pCon.fetchAllTurns();
    }

    public List<Turn> fetchTurnsByDate(LocalDate date) {
        return pCon.fetchTurnsByDate(date);
    }

}
