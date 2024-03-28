package com.closure13k.aaronfmpt2.persistence;

import com.closure13k.aaronfmpt2.logic.model.Procedure;
import com.closure13k.aaronfmpt2.persistence.exceptions.NonexistentEntityException;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class ProcedureJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public ProcedureJpaController() {
        emf = Persistence.createEntityManagerFactory("TurneroPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public List<Procedure> findProcedureEntities() {
        return findProcedureEntities(true, -1, -1);
    }


    private List<Procedure> findProcedureEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Procedure.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Procedure findProcedure(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Procedure.class, id);
        } finally {
            em.close();
        }
    }


}
