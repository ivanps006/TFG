package org.example.proyecto_tfg;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private Label ErrorLogin;


    public void validarCredenciales(){
        if (txtUsuario.getText().equals("admin") && txtContrasenia.getText().equals("admin1234")){
           ErrorLogin.setText("Acceso concedido. ¡Bienvenido!");
           ErrorLogin.setStyle("-fx-text-fill: green;");
        } else {
            ErrorLogin.setText("Credenciales incorrectas. Inténtalo de nuevo.");
            ErrorLogin.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void onRegisterButtonClick(ActionEvent event) {
        try {
            //Cargamos la nueva ventana
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("register-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            //Le añadimos este titulo a la ventana
            stage.setTitle("Registrarse");
            //bloquea la ventana padre
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 400, 300));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
