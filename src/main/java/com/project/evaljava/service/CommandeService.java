package com.project.evaljava.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import com.project.evaljava.classes.Commande;
import com.project.evaljava.dao.IDao;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CommandeService implements IDao<Commande> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Commande add(Commande c) {
        em.persist(c);
        return c;
    }

    @Override
    public Commande update(Commande c) {
        return em.merge(c);
    }

    @Override
    public void delete(Commande c) {
        em.remove(em.contains(c) ? c : em.merge(c));
    }

    @Override
    public Commande findById(int id) {
        return em.find(Commande.class, id);
    }

    @Override
    public List<Commande> findAll() {
        return em.createQuery("SELECT c FROM Commande c", Commande.class).getResultList();
    }

    public List<Commande> findBetweenDates(LocalDate start, LocalDate end) {
        return em.createQuery("SELECT c FROM Commande c WHERE c.date BETWEEN :start AND :end", Commande.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }
}

