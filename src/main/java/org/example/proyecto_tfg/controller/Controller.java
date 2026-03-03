package org.example.proyecto_tfg.controller;


import javafx.beans.property.SimpleStringProperty;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.proyecto_tfg.HelloApplication;
import org.example.proyecto_tfg.model.Bicicleta;
import org.example.proyecto_tfg.model.Cliente;
import org.example.proyecto_tfg.model.Mecanico;
import org.example.proyecto_tfg.service.AddService;
import org.example.proyecto_tfg.service.HomeService;
import org.example.proyecto_tfg.service.NavigationService;
import org.example.proyecto_tfg.service.loginService;

import java.io.IOException;

public class Controller {

    private final loginService loginService = new loginService();
    private final HomeService homeService = new HomeService();
    private final AddService addService = new AddService();

    @FXML private BorderPane root; // root principal del FXML `principalWindows-view.fxml`
    private Node previousCenter;    // para volver a la vista anterior

    @FXML private Button btnAddBicicleta;

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

    @FXML private TextField txtReferencia;
    @FXML private TextField txtMarca;
    @FXML private TextField txtModelo;
    @FXML private TextField txtFrenos;
    @FXML private TextField txtSuspDel;
    @FXML private TextField txtSuspTras;
    @FXML private TextField txtTransmision;
    @FXML private TextField txtRuedas;
    @FXML private TextField txtClienteDni;

    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;

    @FXML private TableView<Bicicleta> tablaBicicletas;
    @FXML private TableColumn<Bicicleta, String> colRef;
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
        // Asegurar política de redimensionado
        if (tablaBicicletas != null) {
            tablaBicicletas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }

        // Configurar factories de columnas
        if (colRef != null) {
            colRef.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId_referencia()));
        }
        if (colMarca != null) {
            colMarca.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMarca()));
        }
        if (colModelo != null) {
            colModelo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getModelo()));
        }

        if (colCliente != null) {
            colCliente.setCellValueFactory(cell -> {
                Cliente c = cell.getValue().getId_cliente();
                return new SimpleStringProperty(c != null ? c.getDni() : "");
            });
        }
        if (colEstado != null) {
            colEstado.setCellValueFactory(cell -> new SimpleStringProperty("")); // ajustar si hay estado real
        }

        NavigationService.getInstance().setRoot(root);

        // cargar bicicletas como antes
        ObservableList<Bicicleta> bicicletas = homeService.cargarBicicletasDesdeBD();
        if (tablaBicicletas != null) {
            tablaBicicletas.setItems(bicicletas);
        }

    }

    public void validarCredenciales() throws IOException {
        if (txtUsuario.getText().isEmpty() || txtContrasenia.getText().isEmpty()) {
            ErrorLogin.setText("Por favor, complete todos los campos.");
            ErrorLogin.setStyle("-fx-text-fill: red;");
        } else {
            if (loginService.consultarMecanico(txtUsuario.getText(), txtContrasenia.getText())) {
                ErrorLogin.setText("Inicio de sesión exitoso.");
                ErrorLogin.setStyle("-fx-text-fill: green;");
                limpiarCamposLogin();

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
    private void guardarCliente(ActionEvent event){
        addService.añadirCliente(
                new Cliente(
                        txtDni.getText(),
                        txtNombre.getText(),
                        txtApellidos.getText(),
                        txtTelefono.getText(),
                        txtDireccion.getText()
                )
        );

        NavigationService.getInstance().goBack();
    }

    @FXML
    private void onRegisterButtonClick(ActionEvent event) {
        try {
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
    private void cerrarPrograma(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void registrarUsuario(ActionEvent event) {
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



    // abrir añadir bicicleta usando NavigationService


    @FXML
    private void añadirCliente(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/añadirCliente.fxml"));
            Parent contenido = fxmlLoader.load();
            NavigationService.getInstance().openInCenter(contenido);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    // Abre el formulario de añadir cliente en la misma escena y prefill DNI
    private void openAddClienteWithDni(String dni) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/añadirCliente.fxml"));
            Parent contenido = loader.load();
            Controller ctrl = loader.getController();
            ctrl.setPrefilledDni(dni);
            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPrefilledDni(String dni) {
        if (txtDni != null) {
            txtDni.setText(dni);
        }
    }


    // método de cancelar/volver genérico para cualquier vista
    @FXML
    private void volverPaginaPrincipal(ActionEvent event){
        NavigationService.getInstance().goBack();
    }
}
