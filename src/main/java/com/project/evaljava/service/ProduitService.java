package com.project.evaljava.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import com.project.evaljava.classes.Produit;
import com.project.evaljava.dao.IDao;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ProduitService implements IDao<Produit> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Produit add(Produit p) {
        em.persist(p);
        return p;
    }

    @Override
    public Produit update(Produit p) {
        return em.merge(p);
    }

    @Override
    public void delete(Produit p) {
        em.remove(em.contains(p) ? p : em.merge(p));
    }

    @Override
    public Produit findById(int id) {
        return em.find(Produit.class, id);
    }

    @Override
    public List<Produit> findAll() {
        return em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
    }

    public List<Produit> findByCategoryId(int categoryId) {
        return em.createQuery("SELECT p FROM Produit p WHERE p.categorie.id = :cid", Produit.class)
                .setParameter("cid", categoryId)
                .getResultList();
    }

    public List<Object[]> productsOrderedBetweenDates(LocalDate start, LocalDate end) {
        // returns: reference, prix, quantite, commandeId, commandeDate
        return em.createQuery("SELECT p.reference, p.prix, l.quantite, c.id, c.date FROM LigneCommandeProduit l JOIN l.produit p JOIN l.commande c WHERE c.date BETWEEN :start AND :end")
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    public List<Object[]> productsInCommande(int commandeId) {
        return em.createQuery("SELECT p.reference, p.prix, l.quantite FROM LigneCommandeProduit l JOIN l.produit p JOIN l.commande c WHERE c.id = :cid")
                .setParameter("cid", commandeId)
                .getResultList();
    }

    public List<Produit> findByPriceGreaterThanNamed(double price) {
        return em.createNamedQuery("Produit.findByPriceGreaterThan", Produit.class)
                .setParameter("price", price)
                .getResultList();
    }
}

