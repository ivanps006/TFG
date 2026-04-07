package org.example.proyecto_tfg.service;

import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
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
            return FXCollections.observableArrayList(lista);
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
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
            return FXCollections.observableArrayList(lista);
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    /**
     * Busca mantenimientos por ID mecánico, ID bicicleta u observaciones
     * @param texto Texto a buscar
     * @return ObservableList con los mantenimientos encontrados
     */
    public ObservableList<Mantenimiento> buscarMantenimientosPorTexto(String texto) {
        EntityManager em = Utils.em();
        try {
            String queryStr = "SELECT m FROM Mantenimiento m WHERE " +
                    "LOWER(m.id_mecanico) LIKE LOWER(:texto) OR " +
                    "LOWER(m.id_bicicleta) LIKE LOWER(:texto) OR " +
                    "LOWER(m.observaciones) LIKE LOWER(:texto)";

            List<Mantenimiento> mantenimientosEncontrados = em.createQuery(queryStr, Mantenimiento.class)
                    .setParameter("texto", "%" + texto + "%")
                    .getResultList();

            return FXCollections.observableArrayList(mantenimientosEncontrados);
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        } finally {
            em.close();
        }
    }

    /**
     * Añade un nuevo mantenimiento a la BD
     * @param mantenimiento Mantenimiento a añadir
     */
    public void añadirMantenimiento(Mantenimiento mantenimiento) {
        EntityManager em = Utils.em();
        try {
            em.getTransaction().begin();
            em.persist(mantenimiento);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza un mantenimiento en la BD
     * @param mantenimiento Mantenimiento a actualizar
     */
    public void actualizarMantenimiento(Mantenimiento mantenimiento) {
        EntityManager em = Utils.em();
        try {
            em.getTransaction().begin();
            em.merge(mantenimiento);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un mantenimiento de la BD
     * @param idMantenimiento ID del mantenimiento a eliminar
     */
    public void eliminarMantenimiento(int idMantenimiento) {
        EntityManager em = Utils.em();
        try {
            em.getTransaction().begin();
            Mantenimiento mantenimiento = em.find(Mantenimiento.class, idMantenimiento);
            if (mantenimiento != null) {
                em.remove(mantenimiento);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene un mantenimiento por ID
     * @param idMantenimiento ID del mantenimiento
     * @return El mantenimiento encontrado o null
     */
    public Mantenimiento obtenerMantenimientoPorId(int idMantenimiento) {
        EntityManager em = Utils.em();
        try {
            return em.find(Mantenimiento.class, idMantenimiento);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
}
