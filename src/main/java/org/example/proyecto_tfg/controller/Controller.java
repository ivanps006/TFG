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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.proyecto_tfg.HelloApplication;
import org.example.proyecto_tfg.model.Bicicleta;
import org.example.proyecto_tfg.model.Cliente;
import org.example.proyecto_tfg.model.Mecanico;
import org.example.proyecto_tfg.service.*;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class Controller {

    private final loginService loginService = new loginService();
    private final HomeService homeService = new HomeService();
    private final AddService addService = new AddService();
    private final ClienteService clienteService = new ClienteService();

    @FXML private BorderPane root;
    @FXML private VBox dashboardView;
    @FXML private Button btnAddBicicleta;
    @FXML private Button btnClientes;

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


    @FXML private TableColumn<Bicicleta, String> colFrenosBici;
    @FXML private TableColumn<Bicicleta, String> colSuspDel;
    @FXML private TableColumn<Bicicleta, String> colSuspTras;
    @FXML private TableColumn<Bicicleta, String> colTransmision;
    @FXML private TableColumn<Bicicleta, String> colRuedas;
    @FXML private TextField tfBuscarBicicleta;
    @FXML private Label lblTotalBicicletas;

    //Datos Clientes
    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;

    @FXML private TableView<Bicicleta> tablaBicicletas;
    @FXML private TableColumn<Bicicleta, String> colRef;
    @FXML private TableColumn<Bicicleta, String> colMarca;
    @FXML private TableColumn<Bicicleta, String> colModelo;
    @FXML private TableColumn<Bicicleta, String> colCliente;
    @FXML private TableColumn<Bicicleta, String> colEstado;

    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colDni;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colApellidos;
    @FXML private TableColumn<Cliente, String> colTelefono;
    @FXML private TextField tfBuscarCliente;
    @FXML private Label lblTotalClientes;

    @FXML private ImageView logoEmpresa;

    @FXML
    public void initialize() {
        if (tablaBicicletas != null) {
            tablaBicicletas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }

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
            colEstado.setCellValueFactory(cell -> new SimpleStringProperty(""));
        }

        if (root != null) {
            NavigationService.getInstance().setRoot(root);
        }

        ObservableList<Bicicleta> bicicletas = homeService.cargarBicicletasDesdeBD();
        if (tablaBicicletas != null && bicicletas != null) {
            tablaBicicletas.setItems(bicicletas);
        }

        if (tablaClientes != null) {
            tablaClientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            if (colDni != null)       colDni.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDni()));
            if (colNombre != null)    colNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
            if (colApellidos != null) colApellidos.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getApellidos()));
            if (colTelefono != null)  colTelefono.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTelefono()));

            ObservableList<Cliente> clientes = clienteService.cargarClientesDesdeBD();
            tablaClientes.setItems(clientes);

            if (lblTotalClientes != null) {
                lblTotalClientes.setText("Total: " + clientes.size() + " clientes");
            }
        }

        if (colFrenosBici != null)    colFrenosBici.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFrenos()));
        if (colSuspDel != null)   colSuspDel.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSuspension_delantera()));
        if (colSuspTras != null)  colSuspTras.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSuspension_trasera()));
        if (colTransmision != null) colTransmision.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTransmision()));
        if (colRuedas != null)    colRuedas.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getRuedas()));

        if (colEstado != null) {
            colEstado.setCellValueFactory(cell ->
                    new SimpleStringProperty(cell.getValue().getEstado() != null
                            ? cell.getValue().getEstado() : "Sin reparar"));

            // Colorear la celda según el estado
            colEstado.setCellFactory(col -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        switch (item) {
                            case "Sin reparar" -> setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                            case "En reparacion" -> setStyle("-fx-text-fill: #f59e0b; -fx-font-weight: bold;");
                            case "Reparada"     -> setStyle("-fx-text-fill: #22c55e; -fx-font-weight: bold;");
                            default             -> setStyle("");
                        }
                    }
                }
            });
        }
        if (lblTotalBicicletas != null && tablaBicicletas != null) {
            lblTotalBicicletas.setText("Total: " + tablaBicicletas.getItems().size() + " bicicletas");
        }

        if (logoEmpresa != null) {
            Image img = new Image(getClass().getResourceAsStream("/view/img/logo.png"));
            logoEmpresa.setImage(img);
        }
    }

    public void validarCredenciales() throws IOException {
        if (txtUsuario.getText().isEmpty() || txtContrasenia.getText().isEmpty()) {
            ErrorLogin.setText("Por favor, complete todos los campos.");
            ErrorLogin.setStyle("-fx-text-fill: red;");
            return;
        }

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
            showAlert("Error", "No se pudo abrir el formulario de registro.");
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

    // Nuevo helper: intenta varias rutas (incluye la variante percent-encoded)
    private Parent loadFxmlTry(String... paths) throws IOException {
        for (String p : paths) {
            URL url = HelloApplication.class.getResource(p);
            if (url != null) {
                return new FXMLLoader(url).load();
            }
        }
        throw new IOException("No se encontró el recurso FXML entre: " + Arrays.toString(paths));
    }

    @FXML
    private void añadirCliente(ActionEvent event){
        try{
            Parent contenido = loadFxmlTry(
                    "/view/proyecto_tfg/añadirCliente.fxml"
            );
            NavigationService.getInstance().openInCenter(contenido);
        } catch (Exception e){
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir el formulario de añadir cliente.");
        }
    }

    // método de cancelar/volver genérico para cualquier vista
    @FXML
    private void volverPaginaPrincipal(ActionEvent event){
        NavigationService.getInstance().goBack();
    }

    @FXML
    private void guardarBicicleta(ActionEvent event) {
        String ref = txtReferencia != null ? txtReferencia.getText().trim() : "";
        String dni = txtClienteDni != null ? txtClienteDni.getText().trim() : "";
        if (ref.isEmpty() || dni.isEmpty()) {
            showAlert("Aviso", "Referencia y DNI del cliente son obligatorios.");
            return;
        }
        Cliente cliente = addService.buscarClientePorDni(dni);
        if (cliente == null) {
            showAlert("Aviso", "Cliente no encontrado con DNI: " + dni);
            return;
        }
        Bicicleta bici = new Bicicleta(
                ref, getTextSafe(txtMarca), getTextSafe(txtModelo),
                getTextSafe(txtFrenos), getTextSafe(txtSuspDel), getTextSafe(txtSuspTras),
                getTextSafe(txtTransmision), getTextSafe(txtRuedas), cliente
        );
        addService.añadirBicicleta(bici);
        NavigationService.getInstance().goBack();
    }

    private String getTextSafe(TextField tf) {
        return tf == null ? "" : tf.getText();
    }

    @FXML
    private void volverLogin(ActionEvent event){
        cerrarPrograma(event);
    }

    // ASCII method for opening añadirBicicleta view (usarlo en FXML: onAction="#anadirBicicleta" si se desea)
    @FXML
    private void anadirBicicleta(ActionEvent event) {
        try {
            Parent contenido = loadFxmlTry(
                    "/view/proyecto_tfg/añadirBicicleta-view.fxml"
            );
            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir añadir bicicleta.");
        }
    }

    // compatibilidad si el FXML usa 'añadirBicicleta' (con ñ)
    @FXML
    private void añadirBicicleta(ActionEvent event) {
        anadirBicicleta(event);
    }

    private void showAlert(String title, String message) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        } catch (Exception ignored) { }
    }

    @FXML
    private void mostrarClientes(ActionEvent event){
        try {
            clienteService.cargarClientesDesdeBD();
            Parent contenido = loadFxmlTry(
                    "/view/proyecto_tfg/clientes.fxml"
            );
            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la vista de clientes.");
        }
    }

    @FXML
    private void mostrarDashboard(ActionEvent event) {
        root.setCenter(dashboardView);
    }

    @FXML
    private void mostrarBicicleta(ActionEvent event) {
        try{
            String texto = tfBuscarBicicleta != null ? tfBuscarBicicleta.getText().trim().toLowerCase() : "";
            ObservableList<Bicicleta> todas = homeService.cargarBicicletasDesdeBD();
            if (texto.isEmpty()) {
                tablaBicicletas.setItems(todas);
            } else {
                ObservableList<Bicicleta> filtradas = todas.filtered(b ->
                        b.getId_referencia().toLowerCase().contains(texto) ||
                                b.getMarca().toLowerCase().contains(texto) ||
                                b.getModelo().toLowerCase().contains(texto)
                );
                tablaBicicletas.setItems(filtradas);
            }
            Parent contenido = loadFxmlTry(
                    "/view/proyecto_tfg/bicicletas.fxml"
            );
            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e){
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la vista de bicicletas.");
        }
    }




}
