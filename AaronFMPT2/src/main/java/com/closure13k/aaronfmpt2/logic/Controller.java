package com.closure13k.aaronfmpt2.logic;

import com.closure13k.aaronfmpt2.persistence.PersistenceController;
import java.util.NoSuchElementException;

public class Controller {

    PersistenceController persistenceController = new PersistenceController();

    public Citizen fetchCitizen(String nif) {
        return persistenceController.fetchCitizen(nif)
                .orElseThrow(() -> new NoSuchElementException());
    }
}
