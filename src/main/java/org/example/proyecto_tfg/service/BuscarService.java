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

    public int buscarBicisSinReparar(){
        EntityManager em = Utils.em();
        try {
            long bicicletaSinReparar = em.createQuery("SELECT count(b) FROM Bicicleta b WHERE b.estado = 'Sin reparar'", long.class)
                    .getSingleResult();
            return (int) bicicletaSinReparar;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
             em.close();
        }
    }

    public int buscarBicisEnReparacion(){
        EntityManager em = Utils.em();
        try{
            long bicicletaEnReparacion = em.createQuery("SELECT count(b) FROM Bicicleta b WHERE b.estado = 'En reparación'", long.class)
                    .getSingleResult();
            return (int) bicicletaEnReparacion;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
             em.close();
        }
    }

    public int buscarBicisReparadas(){
        EntityManager em = Utils.em();
        try{
            long bicisReparadas = em.createQuery("SELECT count(b) FROM Bicicleta b WHERE b.estado = 'Reparada'", long.class)
                    .getSingleResult();
            return (int) bicisReparadas;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            em.close();
        }
    }

    public int buscarClientes(){
        EntityManager em = Utils.em();
        try{
            long clientes = em.createQuery("SELECT count(c) FROM Cliente c", long.class).getSingleResult();
            return (int) clientes;
        } catch(Exception e){
            e.printStackTrace();
            return 0;
        } finally {
             em.close();
        }
    }
}
