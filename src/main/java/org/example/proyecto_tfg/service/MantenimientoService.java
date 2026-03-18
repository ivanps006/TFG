package org.example.proyecto_tfg.service;

import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;
import org.example.proyecto_tfg.model.Mantenimiento;
import org.example.proyecto_tfg.utils.Utils;

import java.util.List;

public class MantenimientoService {

    public ObservableList<Mantenimiento> cargarMantenimientos() {
        EntityManager em = Utils.em();
        try {
            List<Mantenimiento> lista = em.createQuery("SELECT m FROM Mantenimiento m", Mantenimiento.class)
                    .getResultList();
            return javafx.collections.FXCollections.observableArrayList(lista);
        } catch (Exception e) {
            e.printStackTrace();
            return javafx.collections.FXCollections.observableArrayList();
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    public ObservableList<Mantenimiento> cargarMantenimientosPorBicicleta(String idBicicleta) {
        EntityManager em = Utils.em();
        try {
            List<Mantenimiento> lista = em.createQuery(
                            "SELECT m FROM Mantenimiento m WHERE m.id_bicicleta = :idBici",
                            Mantenimiento.class)
                    .setParameter("idBici", idBicicleta)
                    .getResultList();
            return javafx.collections.FXCollections.observableArrayList(lista);
        } catch (Exception e) {
            e.printStackTrace();
            return javafx.collections.FXCollections.observableArrayList();
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }


}
