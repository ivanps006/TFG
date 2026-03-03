// java
package org.example.proyecto_tfg.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.proyecto_tfg.HelloApplication;
import org.example.proyecto_tfg.model.Bicicleta;
import org.example.proyecto_tfg.model.Cliente;
import org.example.proyecto_tfg.service.AddService;
import org.example.proyecto_tfg.service.NavigationService;

import java.io.IOException;

public class AñadirBicicletaController {

    private final AddService addService = new AddService();

    @FXML private TextField txtReferencia;
    @FXML private TextField txtMarca;
    @FXML private TextField txtModelo;
    @FXML private TextField txtFrenos;
    @FXML private TextField txtSuspDel;
    @FXML private TextField txtSuspTras;
    @FXML private TextField txtTransmision;
    @FXML private TextField txtRuedas;
    @FXML private TextField txtClienteDni;

    @FXML
    private void guardarBicicleta(ActionEvent event) {
        String ref = txtReferencia.getText().trim();
        String dni = txtClienteDni.getText().trim();
        if (ref.isEmpty() || dni.isEmpty()) {
            return; // validar y mostrar alerta si se desea
        }
        Cliente cliente = addService.buscarClientePorDni(dni);
        if (cliente == null) {
            // manejar creación de cliente o mostrar alerta
            return;
        }
        Bicicleta bici = new Bicicleta(
                ref, txtMarca.getText(), txtModelo.getText(),
                txtFrenos.getText(), txtSuspDel.getText(), txtSuspTras.getText(),
                txtTransmision.getText(), txtRuedas.getText(), cliente
        );
        addService.añadirBicicleta(bici);
        NavigationService.getInstance().goBack();
    }

    @FXML
    private void volverLogin(ActionEvent event){
        try{
            cerrarPrograma(event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void cerrarPrograma(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    // java
    @FXML
    private void añadirBicicleta(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/añadirBicicleta-view.fxml"));
            Parent contenido = fxmlLoader.load();
            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void volverPaginaPrincipal(ActionEvent event) {
        NavigationService.getInstance().goBack();
    }
}
