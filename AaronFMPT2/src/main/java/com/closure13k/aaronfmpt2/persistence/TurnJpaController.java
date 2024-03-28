package com.closure13k.aaronfmpt2.persistence;

import com.closure13k.aaronfmpt2.logic.model.Turn;
import com.closure13k.aaronfmpt2.persistence.exceptions.NonexistentEntityException;
import com.closure13k.aaronfmpt2.persistence.exceptions.PreexistingEntityException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class TurnJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public TurnJpaController() {
        emf = Persistence.createEntityManagerFactory("TurneroPU");
    }

    //<editor-fold defaultstate="collapsed" desc="Generado por NetBeans">

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Turn turn) throws PreexistingEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(turn);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTurn(turn.getId()) != null) {
                throw new PreexistingEntityException("Turn " + turn + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Turn turn) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            turn = em.merge(turn);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.isEmpty()) {
                Long id = turn.getId();
                if (findTurn(id) == null) {
                    throw new NonexistentEntityException("The turn with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public List<Turn> findTurnEntities() {
        return findTurnEntities(true, -1, -1);
    }

    public List<Turn> findTurnEntities(int maxResults, int firstResult) {
        return findTurnEntities(false, maxResults, firstResult);
    }

    private List<Turn> findTurnEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Object> query = criteriaBuilder.createQuery();
            Root<Turn> fromTurn = query.from(Turn.class);

            query.select(fromTurn);
            query.orderBy(criteriaBuilder.asc(fromTurn.get("date")));

            Query q = em.createQuery(query);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Turn findTurn(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Turn.class, id);
        } finally {
            em.close();
        }
    }

    //</editor-fold>    

    /**
     * Busca turnos en base a una fecha especificada.
     *
     * @param date fecha para filtrar.
     * @return lista de turnos.
     */
    public List<Turn> findAllByDate(LocalDate date) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Turnos.listarPorFecha");
            q.setParameter("date", date);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
