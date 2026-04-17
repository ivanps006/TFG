package org.example.proyecto_tfg.service;

import jakarta.persistence.EntityManager;
import org.example.proyecto_tfg.model.Bicicleta;
import org.example.proyecto_tfg.model.Cliente;
import org.example.proyecto_tfg.model.Factura;
import org.example.proyecto_tfg.utils.Utils;

public class DeleteService {

    public void eliminarBicicleta(String idBici){
        EntityManager em = Utils.em();
        try {
            em.getTransaction().begin();
            var bicicleta = em.find(Bicicleta.class, idBici);
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

    public void eliminarCliente(String dni){
        EntityManager em = Utils.em();
        try{
            em.getTransaction().begin();
            var cliente = em.find(Cliente.class, dni);
            if (cliente != null){
                em.remove(cliente);
                System.out.println("Cliente con DNI " + dni + " eliminado.");
            }else {
                System.out.println("No se encontró el cliente con DNI " + dni);
            }
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {            em.close();
        }
    }
    public void eliminarFactura(Integer idFactura){
        EntityManager em = Utils.em();
        try {
            em.getTransaction().begin();
            var factura = em.find(Factura.class, idFactura);
            if (factura != null) {
                em.remove(factura);
                System.out.println("Factura con ID " + idFactura + " eliminada.");
            } else {
                System.out.println("No se encontró la factura con ID " + idFactura);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
