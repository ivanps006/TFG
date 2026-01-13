package org.example.proyecto_tfg.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Utils {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bms");

    public static EntityManager em() {
        return emf.createEntityManager();
    }

    public static void close() {
        emf.close();
    }
}