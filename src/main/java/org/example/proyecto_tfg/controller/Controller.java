package org.example.proyecto_tfg.controller;

import jakarta.persistence.EntityManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.proyecto_tfg.HelloApplication;
import org.example.proyecto_tfg.model.Bicicleta;
import org.example.proyecto_tfg.model.Cliente;
import org.example.proyecto_tfg.model.Mecanico;
import org.example.proyecto_tfg.service.HomeService;
import org.example.proyecto_tfg.service.loginService;
import org.example.proyecto_tfg.utils.Utils;

import java.io.IOException;
import java.util.List;

public class Controller {

    private final loginService loginService = new loginService();
    private final HomeService homeService = new HomeService();

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasenia;
    @FXML private Label ErrorLogin;

    @FXML private TextField tfUsuario;
    @FXML private PasswordField pfContrasenia;
    @FXML private TextField tfNombre;
    @FXML private TextField tfApellido;
    @FXML private TextField tfDni;
    @FXML private TextField tfTelefono;
    @FXML private TextField tfDireccion;
    @FXML private Label lblMensajeRegistro;

    // Tipado y columnas con tipos
    @FXML private TableView<Bicicleta> tablaBicicletas;
    @FXML private TableColumn<Bicicleta, Long> colRef;
    @FXML private TableColumn<Bicicleta, String> colMarca;
    @FXML private TableColumn<Bicicleta, String> colModelo;
    @FXML private TableColumn<Bicicleta, String> colFrenos;
    @FXML private TableColumn<Bicicleta, String> colSuspDelantera;
    @FXML private TableColumn<Bicicleta, String> colSuspTrasera;
    @FXML private TableColumn<Bicicleta, String> colTransmision;
    @FXML private TableColumn<Bicicleta, String> colRuedas;
    @FXML private TableColumn<Bicicleta, String> colCliente;
    @FXML private TableColumn<Bicicleta, String> colEstado;

    private ObservableList<Bicicleta> bicicletas;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            if (tablaBicicletas != null) {
                tablaBicicletas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                // Inicializar lista
                bicicletas = FXCollections.observableArrayList();
                tablaBicicletas.setItems(bicicletas);

                // Configurar cell value factories usando getters
                colRef.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getId_referencia()));
                colMarca.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMarca()));
                colModelo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getModelo()));
                colFrenos.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFrenos()));
                colSuspDelantera.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSuspension_delantera()));
                colSuspTrasera.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSuspension_trasera()));
                colTransmision.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTransmision()));
                colRuedas.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getRuedas()));
                // Mostrar el dni del cliente (verificar null)
                colCliente.setCellValueFactory(cell -> {
                    Cliente c = cell.getValue().getId_cliente();
                    return new SimpleStringProperty(c != null ? c.getDni() : "");
                });
                // Si no hay campo estado en modelo, mostrar vacío o adaptar
                colEstado.setCellValueFactory(cell -> new SimpleStringProperty(""));

                // Cargar datos desde HomeService
                bicicletas = homeService.cargarBicicletasDesdeBD();
                tablaBicicletas.setItems(bicicletas);
            }
        });
    }


    public void validarCredenciales() throws IOException {
        if (txtUsuario.getText().isEmpty() || txtContrasenia.getText().isEmpty()) {
            ErrorLogin.setText("Por favor, complete todos los campos.");
            ErrorLogin.setStyle("-fx-text-fill: red;");
        } else {
            // Simulación de validación
            if (loginService.consultarMecanico(txtUsuario.getText(), txtContrasenia.getText())) {
                ErrorLogin.setText("Inicio de sesión exitoso.");
                ErrorLogin.setStyle("-fx-text-fill: green;");
                limpiarCamposLogin();
                // Lógica para abrir la siguiente ventana o funcionalidad

                //Cargamos la nueva ventana
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/principalWindows-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
                Stage stage = new Stage();
                stage.setTitle("Hello!");
                stage.setScene(scene);
                stage.show();

                stage.centerOnScreen();
                stage.setResizable(false);
            } else {
                ErrorLogin.setText("Credenciales incorrectas. Inténtelo de nuevo.");
                ErrorLogin.setStyle("-fx-text-fill: red;");
                limpiarCamposLogin();
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

        limpiarCamposRegistro();
    }
    private void limpiarCamposRegistro() {
        tfUsuario.clear();
        pfContrasenia.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfDni.clear();
        tfTelefono.clear();
        tfDireccion.clear();
    }

    private void limpiarCamposLogin() {
        txtUsuario.clear();
        txtContrasenia.clear();
    }

    @FXML
    private void volverLogin(ActionEvent event){
        try{
            onClose(event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
