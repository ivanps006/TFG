package org.example.proyecto_tfg.service;

import jakarta.persistence.EntityManager;
import org.example.proyecto_tfg.utils.Utils;

public class DeleteService {

    public void eliminarBicicleta(String idBici){
        EntityManager em = Utils.em();
        try {
            em.getTransaction().begin();
            var bicicleta = em.find(org.example.proyecto_tfg.model.Bicicleta.class, idBici);
            if (bicicleta != null) {
                em.remove(bicicleta);
                System.out.println("Bicicleta con ID " + idBici + " eliminada.");
            } else {
                System.out.println("No se encontró la bicicleta con ID " + idBici);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
