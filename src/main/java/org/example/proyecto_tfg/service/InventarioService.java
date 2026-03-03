package org.example.proyecto_tfg.service;

import jakarta.persistence.EntityManager;
import org.example.proyecto_tfg.utils.Utils;

public class InventarioService {

        public void listarInventario(int id) {
            EntityManager em = Utils.em();
            try {
                em.getTransaction().begin();
                var bicicleta = em.find(org.example.proyecto_tfg.model.Bicicleta.class, id);
                if (bicicleta != null) {
                    em.remove(bicicleta);
                }
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
}
