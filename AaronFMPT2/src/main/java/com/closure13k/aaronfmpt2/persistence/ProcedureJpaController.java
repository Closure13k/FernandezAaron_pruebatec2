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

    public ProcedureJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Procedure procedure) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(procedure);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Procedure procedure) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            procedure = em.merge(procedure);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = procedure.getId();
                if (findProcedure(id) == null) {
                    throw new NonexistentEntityException("The procedure with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Procedure procedure;
            try {
                procedure = em.getReference(Procedure.class, id);
                procedure.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The procedure with id " + id + " no longer exists.", enfe);
            }
            em.remove(procedure);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Procedure> findProcedureEntities() {
        return findProcedureEntities(true, -1, -1);
    }

    public List<Procedure> findProcedureEntities(int maxResults, int firstResult) {
        return findProcedureEntities(false, maxResults, firstResult);
    }

    private List<Procedure> findProcedureEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
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

    public int getProcedureCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Procedure> rt = cq.from(Procedure.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
