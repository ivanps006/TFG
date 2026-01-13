package org.example.proyecto_tfg.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.proyecto_tfg.HelloApplication;
import org.example.proyecto_tfg.model.Mecanico;
import org.example.proyecto_tfg.service.loginService;

import java.io.IOException;
import java.net.URL;

public class Controller {

    private final loginService loginService = new loginService();

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private Label ErrorLogin;

    @FXML
    private TextField tfUsuario;

    @FXML
    private PasswordField pfContrasenia;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfApellido;

    @FXML
    private TextField tfDni;

    @FXML
    private TextField tfTelefono;

    @FXML
    private TextField tfDireccion;

    @FXML
    private Label lblMensajeRegistro;


    public void validarCredenciales(){
        if (txtUsuario.getText().isEmpty() || txtContrasenia.getText().isEmpty()) {
            ErrorLogin.setText("Por favor, complete todos los campos.");
        } else {
            // Simulación de validación
            if (loginService.consultarMecanico(txtUsuario.getText(), txtContrasenia.getText())) {
                ErrorLogin.setText("Inicio de sesión exitoso.");
                ErrorLogin.setStyle("-fx-text-fill: green;");
                // Lógica para abrir la siguiente ventana o funcionalidad
            } else {
                ErrorLogin.setText("Credenciales incorrectas. Inténtelo de nuevo.");
                ErrorLogin.setStyle("-fx-text-fill: red;");
            }
        }
    }

    @FXML
    private void onRegisterButtonClick(ActionEvent event) {
        try {
            //Cargamos la nueva ventana
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/register-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 500, 500);
            Stage stage = new Stage();
            stage.setTitle("Hello!");

            // Ruta correcta dentro del classpath: /css/stylePrincipalPage.css
            URL cssUrl = getClass().getResource("/view/css/styleRegisterPage.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
            stage.setScene(scene);
            stage.show();

            stage.centerOnScreen();
            stage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void registrarUsuario(ActionEvent event) {
        // Lógica para registrar un nuevo usuario
        loginService.añadirMecanico(new Mecanico(
                pfContrasenia.getText(),
                tfNombre.getText(),
                tfApellido.getText(),
                tfDni.getText(),
                tfTelefono.getText(),
                tfDireccion.getText(),
                tfUsuario.getText()

        ));
        lblMensajeRegistro.setText("Registro exitoso.");
        lblMensajeRegistro.setStyle("-fx-text-fill: green;");
    }
}
