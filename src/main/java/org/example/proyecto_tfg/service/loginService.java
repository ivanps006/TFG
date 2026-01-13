package org.example.proyecto_tfg.service;

import jakarta.persistence.EntityManager;
import org.example.proyecto_tfg.model.Mecanico;
import org.example.proyecto_tfg.utils.Utils;

public class loginService {

    public void añadirMecanico(Mecanico mecanico) {
        // Lógica para añadirun mecánico
        EntityManager em = Utils.em();
        try {
            em.getTransaction().begin();
            em.persist(mecanico);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public boolean consultarMecanico(String usuario, String contrasena) {
        // Lógica para consultar un mecánico por su usuario

        EntityManager em = Utils.em();
        try {
            Mecanico mecanico = em.find(Mecanico.class, contrasena);
            if (mecanico != null && mecanico.getUsuario().equals(usuario)) {
                System.out.println("Mecánico encontrado: " + mecanico.getNombre());
                return true;
            } else {
                System.out.println("Mecánico no encontrado.");
                return false;
            }
        } finally {
            em.close();
        }
    }

}
