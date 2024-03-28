package com.closure13k.aaronfmpt2.persistence;

import com.closure13k.aaronfmpt2.logic.model.Citizen;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

public class CitizenJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public CitizenJpaController() {
        emf = Persistence.createEntityManagerFactory("TurneroPU");
    }

    //<editor-fold defaultstate="collapsed" desc="Codigo generado por Netbeans">

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Citizen citizen) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(citizen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public List<Citizen> findCitizenEntities() {
        return findCitizenEntities(true, -1, -1);
    }

    public List<Citizen> findCitizenEntities(int maxResults, int firstResult) {
        return findCitizenEntities(false, maxResults, firstResult);
    }

    private List<Citizen> findCitizenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Citizen.class));
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
    //</editor-fold>

    public Citizen findCitizenByNif(String nif) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Ciudadanos.buscarPorNif");
            q.setParameter("nif", nif);
            return (Citizen) q.getSingleResult();
        } catch (NonUniqueResultException | NoResultException nre) {
            return null;
        } finally {
            em.close();
        }
    }
}
