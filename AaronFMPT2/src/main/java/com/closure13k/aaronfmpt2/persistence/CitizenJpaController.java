package com.closure13k.aaronfmpt2.persistence;

import com.closure13k.aaronfmpt2.logic.model.Citizen;
import com.closure13k.aaronfmpt2.persistence.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CitizenJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public CitizenJpaController() {
        emf = Persistence.createEntityManagerFactory("TurneroPU");
    }

    //<editor-fold defaultstate="collapsed" desc="Codigo generado por Netbeans">
    public CitizenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

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

    public void edit(Citizen citizen) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            citizen = em.merge(citizen);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = citizen.getId();
                if (findCitizen(id) == null) {
                    throw new NonexistentEntityException("The citizen with id " + id + " no longer exists.");
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
            Citizen citizen;
            try {
                citizen = em.getReference(Citizen.class, id);
                citizen.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The citizen with id " + id + " no longer exists.", enfe);
            }
            em.remove(citizen);
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
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
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

    public Citizen findCitizen(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Citizen.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitizenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Citizen> rt = cq.from(Citizen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
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
