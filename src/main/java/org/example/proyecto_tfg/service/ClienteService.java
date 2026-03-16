package org.example.proyecto_tfg.service;

import jakarta.persistence.EntityManager;
import javafx.collections.ObservableList;
import org.example.proyecto_tfg.model.Cliente;
import org.example.proyecto_tfg.utils.Utils;

import java.util.List;

public class ClienteService {

    public ObservableList<Cliente> cargarClientesDesdeBD(){
        EntityManager em = Utils.em();
        try{
            List<Cliente> listaClientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class)
                    .getResultList();
            System.out.println("Cargando clientes desde la base de datos: " + listaClientes.size() + " clientes encontrados.");
            System.out.println("\n"+listaClientes);
            return javafx.collections.FXCollections.observableArrayList(listaClientes);
        }catch (Exception e){
            e.printStackTrace();
            return javafx.collections.FXCollections.observableArrayList();
        }
    }
}
