package com.closure13k.aaronfmpt2.persistence;

import com.closure13k.aaronfmpt2.logic.Citizen;
import java.util.Optional;

public class PersistenceController {

    CitizenJpaController citizenJpaController = new CitizenJpaController();
    ProcedureJpaController procedureJpaController = new ProcedureJpaController();
    TurnJpaController turnJpaController = new TurnJpaController();

    public Optional<Citizen> fetchCitizen(String nif) {
        return Optional.ofNullable(citizenJpaController.findCitizenByNif(nif));
    }

}
