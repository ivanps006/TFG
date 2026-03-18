package org.example.proyecto_tfg.service;

import jakarta.persistence.EntityManager;
import org.example.proyecto_tfg.model.Factura;
import org.example.proyecto_tfg.utils.Utils;

import java.util.List;

public class FacturaService {

    public Factura crearFactura(Factura factura) {
        EntityManager em = Utils.em();
        try {
            em.getTransaction().begin();
            em.persist(factura);
            em.getTransaction().commit();
            return factura;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public Factura obtenerFacturaPorId(Integer id) {
        EntityManager em = Utils.em();
        try {
            return em.find(Factura.class, id);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Factura> obtenerTodasFacturas() {
        EntityManager em = Utils.em();
        try {
            return em.createQuery("SELECT f FROM Factura f", Factura.class).getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public Factura actualizarFactura(Factura factura) {
        EntityManager em = Utils.em();
        try {
            em.getTransaction().begin();
            factura = em.merge(factura);
            em.getTransaction().commit();
            return factura;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}