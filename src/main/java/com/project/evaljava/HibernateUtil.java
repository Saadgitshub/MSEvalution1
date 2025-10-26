package com.project.evaljava;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;

public class HibernateUtil {

    public static Session getSession(EntityManager em) {
        return em.unwrap(Session.class);
    }
}

