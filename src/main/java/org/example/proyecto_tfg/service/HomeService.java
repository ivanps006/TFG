package org.example.proyecto_tfg.service;

import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.proyecto_tfg.model.Bicicleta;
import org.example.proyecto_tfg.utils.Utils;

import java.util.List;

public class HomeService {

    public ObservableList<Bicicleta> cargarBicicletasDesdeBD() {
        EntityManager em = Utils.em();
        try {
            List<Bicicleta> lista = em.createQuery("SELECT b FROM Bicicleta b", Bicicleta.class)
                    .getResultList();
            System.out.println("Cargando bicicletas desde la base de datos: " + lista.size() + " bicicletas encontradas.");
            System.out.println("\n"+lista);
            return FXCollections.observableArrayList(lista);
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
