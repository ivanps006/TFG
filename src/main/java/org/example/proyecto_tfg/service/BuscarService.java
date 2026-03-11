package org.example.proyecto_tfg.service;

import jakarta.persistence.EntityManager;
import org.example.proyecto_tfg.model.Bicicleta;
import org.example.proyecto_tfg.utils.Utils;

public class BuscarService {

    public Bicicleta buscarBicicleta(String idBici) {
        EntityManager em = Utils.em();
        try {
            Bicicleta bicicleta = em.find(Bicicleta.class, idBici);
            if (bicicleta != null) {
                // Forzar carga del cliente (lazy)
                if (bicicleta.getId_cliente() != null) {
                    bicicleta.getId_cliente().getDni();
                }
                return bicicleta;
            } else {
                System.out.println("No se encontró la bicicleta con ID: " + idBici);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
}
