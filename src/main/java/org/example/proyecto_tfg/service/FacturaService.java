package org.example.proyecto_tfg.service;

import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;
import org.example.proyecto_tfg.model.Factura;
import org.example.proyecto_tfg.utils.Utils;

import java.util.List;

public class FacturaService {

    public ObservableList<Factura> cargarFacturasBD(){
        EntityManager em = Utils.em();
        try {
            List<Factura> listaFacturas = em.createQuery("SELECT f FROM Factura f", Factura.class).getResultList();
            System.out.println("Cargando facturas desde la base de datos");
            System.out.println("\n" + listaFacturas);
            return javafx.collections.FXCollections.observableArrayList(listaFacturas);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
