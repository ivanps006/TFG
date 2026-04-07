package org.example.proyecto_tfg.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.proyecto_tfg.model.Mecanico;
import org.example.proyecto_tfg.utils.Utils;

public class loginService {

    public void añadirMecanico(Mecanico mecanico) {
        EntityManager em = Utils.em();
        try {
            em.getTransaction().begin();
            em.persist(mecanico);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Mecanico consultarMecanico(String usuario, String contrasena) {
        EntityManager em = Utils.em();
        try {
            TypedQuery<Mecanico> query = em.createQuery(
                    "SELECT m FROM Mecanico m WHERE m.usuario = :usuario AND m.contrasena = :contrasena",
                    Mecanico.class
            );
            query.setParameter("usuario", usuario);
            query.setParameter("contrasena", contrasena);

            return query.getResultList().stream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }
}
