package org.example.proyecto_tfg.service;

import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;
import org.example.proyecto_tfg.model.Pieza;
import org.example.proyecto_tfg.utils.Utils;

public class InventarioService {

      public ObservableList<Pieza> cargarPiezasDesdeBD() {
          EntityManager em = Utils.em();
          try {
              var listaPiezas = em.createQuery("SELECT p FROM Pieza p", Pieza.class)
                      .getResultList();
              System.out.println("Cargando piezas desde la base de datos: " + listaPiezas.size() + " piezas encontradas.");
              System.out.println("\n" + listaPiezas);
              return javafx.collections.FXCollections.observableArrayList(listaPiezas);
          } catch (Exception e) {
              e.printStackTrace();
              return javafx.collections.FXCollections.observableArrayList();
          }
      }

    public void actualizarPieza(Pieza pieza) {
        EntityManager em = Utils.em();
        try {
            em.getTransaction().begin();
            em.merge(pieza);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void añadirPieza(Pieza pieza) {
        EntityManager em = Utils.em();
        try {
            em.getTransaction().begin();
            em.persist(pieza);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


}
