package com.project.evaljava.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import com.project.evaljava.classes.Categorie;
import com.project.evaljava.dao.IDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategorieService implements IDao<Categorie> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Categorie add(Categorie c) {
        em.persist(c);
        return c;
    }

    @Override
    public Categorie update(Categorie c) {
        return em.merge(c);
    }

    @Override
    public void delete(Categorie c) {
        em.remove(em.contains(c) ? c : em.merge(c));
    }

    @Override
    public Categorie findById(int id) {
        return em.find(Categorie.class, id);
    }

    @Override
    public List<Categorie> findAll() {
        return em.createQuery("SELECT c FROM Categorie c", Categorie.class).getResultList();
    }
}

