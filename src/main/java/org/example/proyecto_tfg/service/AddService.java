package org.example.proyecto_tfg.service;

import jakarta.persistence.EntityManager;
import javafx.fxml.FXML;
import org.example.proyecto_tfg.model.Bicicleta;
import org.example.proyecto_tfg.model.Cliente;
import org.example.proyecto_tfg.utils.Utils;

public class AddService {


    public Cliente añadirCliente(Cliente cliente){
        EntityManager em = Utils.em();
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            return cliente;
        } finally {
            em.close();
        }
    }

    public void añadirBicicleta(Bicicleta bicicleta){
        EntityManager em = Utils.em();
        try {
            em.getTransaction().begin();
            em.persist(bicicleta);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Cliente buscarClientePorDni(String dni) {
        EntityManager em = Utils.em();
        try {
            return em.find(Cliente.class, dni);
        } finally {
            em.close();
        }
    }
}
