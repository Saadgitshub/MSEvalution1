package com.project.evaljava.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import com.project.evaljava.classes.LigneCommandeProduit;
import com.project.evaljava.dao.IDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LigneCommandeService implements IDao<LigneCommandeProduit> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public LigneCommandeProduit add(LigneCommandeProduit l) {
        // ensure associations are managed
        if (l.getCommande() != null && l.getCommande().getId() == null) {
            em.persist(l.getCommande());
        }
        if (l.getProduit() != null && l.getProduit().getId() == null) {
            em.persist(l.getProduit());
        }
        em.persist(l);
        return l;
    }

    @Override
    public LigneCommandeProduit update(LigneCommandeProduit l) {
        return em.merge(l);
    }

    @Override
    public void delete(LigneCommandeProduit l) {
        em.remove(em.contains(l) ? l : em.merge(l));
    }

    @Override
    public LigneCommandeProduit findById(int id) {
        return em.find(LigneCommandeProduit.class, id);
    }

    @Override
    public List<LigneCommandeProduit> findAll() {
        return em.createQuery("SELECT l FROM LigneCommandeProduit l", LigneCommandeProduit.class).getResultList();
    }
}

