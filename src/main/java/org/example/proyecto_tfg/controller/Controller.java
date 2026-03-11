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
import org.example.proyecto_tfg.model.Pieza;
import org.example.proyecto_tfg.service.*;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class Controller {

    private final loginService loginService = new loginService();
    private final HomeService homeService = new HomeService();
    private final AddService addService = new AddService();
    private final ClienteService clienteService = new ClienteService();
    private final InventarioService inventarioService = new InventarioService();
    private final BuscarService buscarService = new BuscarService();
    private final DeleteService deleteService = new DeleteService();

    @FXML private BorderPane root;
    @FXML private VBox dashboardView;
    @FXML private Button btnAddBicicleta;
    @FXML private Button btnClientes;

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasenia;
    @FXML private Label ErrorLogin;

    //Campos para añadir clientes
    @FXML private TextField tfUsuario;
    @FXML private PasswordField pfContrasenia;
    @FXML private TextField tfNombre;
    @FXML private TextField tfApellido;
    @FXML private TextField tfDni;
    @FXML private TextField tfTelefono;
    @FXML private TextField tfDireccion;
    @FXML private Label lblMensajeRegistro;

    //Datos de la vista MAntenimiento (Cogemos los del dashboard y le añadimos estos)
    @FXML private TextField txtReferencia;
    @FXML private TextField txtMarca;
    @FXML private TextField txtModelo;
    @FXML private TextField txtFrenos;
    @FXML private TextField txtSuspDel;
    @FXML private TextField txtSuspTras;
    @FXML private TextField txtTransmision;
    @FXML private TextField txtRuedas;
    @FXML private TextField txtClienteDni;

    //Campos de la tabla de Mantenimiento
    @FXML private TableColumn<Bicicleta, String> colFrenosBici;
    @FXML private TableColumn<Bicicleta, String> colSuspDel;
    @FXML private TableColumn<Bicicleta, String> colSuspTras;
    @FXML private TableColumn<Bicicleta, String> colTransmision;
    @FXML private TableColumn<Bicicleta, String> colRuedas;
    @FXML private TextField tfBuscarBicicleta;
    @FXML private Label lblTotalBicicletas;
    @FXML private TextField txtBuscarRef;

    //Datos Clientes
    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;

    //Datos del Dashboard
    @FXML private TableView<Bicicleta> tablaBicicletas;
    @FXML private TableColumn<Bicicleta, String> colRef;
    @FXML private TableColumn<Bicicleta, String> colMarca;
    @FXML private TableColumn<Bicicleta, String> colModelo;
    @FXML private TableColumn<Bicicleta, String> colCliente;
    @FXML private TableColumn<Bicicleta, String> colEstado;

    //Campos datos de cliente
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colDni;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colApellidos;
    @FXML private TableColumn<Cliente, String> colTelefono;
    @FXML private TextField tfBuscarCliente;
    @FXML private Label lblTotalClientes;

    @FXML private ImageView logoEmpresa;

    // Campos FXML para inventario
    @FXML private TableView<Pieza> tablaPiezas;
    @FXML private TableColumn<Pieza, String> colIdPieza;
    @FXML private TableColumn<Pieza, String> colModeloPieza;
    @FXML private TableColumn<Pieza, String> colMarcaPieza;
    @FXML private TableColumn<Pieza, String> colTipoPieza;
    @FXML private TableColumn<Pieza, String> colStockPieza;
    @FXML private TableColumn<Pieza, String> colPrecioPieza;
    @FXML private TextField tfBuscarPieza;
    @FXML private Label lblTotalPiezas;

    // Labels de eliminarBicicleta
    @FXML private Label lblRefBici;
    @FXML private Label lblEstadoBici;
    @FXML private Label lblMarcaBici;
    @FXML private Label lblModeloBici;
    @FXML private Label lblFrenosBici;
    @FXML private Label lblTransmisionBici;
    @FXML private Label lblSuspDelBici;
    @FXML private Label lblSuspTrasBici;
    @FXML private Label lblRuedasBici;
    @FXML private Label lblClienteBici;



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

        if (tablaPiezas != null) {
            tablaPiezas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            if (colIdPieza != null)
                colIdPieza.setCellValueFactory(cell ->
                        new SimpleStringProperty(String.valueOf(cell.getValue().getId_pieza())));
            if (colModeloPieza != null)
                colModeloPieza.setCellValueFactory(cell ->
                        new SimpleStringProperty(cell.getValue().getModelo()));
            if (colMarcaPieza != null)
                colMarcaPieza.setCellValueFactory(cell ->
                        new SimpleStringProperty(cell.getValue().getMarca()));
            if (colTipoPieza != null)
                colTipoPieza.setCellValueFactory(cell ->
                        new SimpleStringProperty(cell.getValue().getTipo()));
            if (colStockPieza != null) {
                colStockPieza.setCellValueFactory(cell ->
                        new SimpleStringProperty(String.valueOf(cell.getValue().getStock())));

                // Colorear stock bajo en rojo
                colStockPieza.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null); setStyle("");
                        } else {
                            setText(item);
                            int stock = Integer.parseInt(item);
                            if (stock == 0)
                                setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                            else if (stock <= 5)
                                setStyle("-fx-text-fill: #f59e0b; -fx-font-weight: bold;");
                            else
                                setStyle("-fx-text-fill: #22c55e; -fx-font-weight: bold;");
                        }
                    }
                });
            }

            InventarioService inventarioService = new InventarioService();
            ObservableList<Pieza> piezas = inventarioService.cargarPiezasDesdeBD();
            tablaPiezas.setItems(piezas);

            if (lblTotalPiezas != null)
                lblTotalPiezas.setText("Total: " + piezas.size() + " piezas");
        }
        if (colPrecioPieza != null)
            colPrecioPieza.setCellValueFactory(cell ->
                    new SimpleStringProperty(String.format("%.2f €", cell.getValue().getPrecio())));



// Doble clic en fila → abrir info de bicicleta
        if (tablaBicicletas != null) {
            tablaBicicletas.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Bicicleta seleccionada = tablaBicicletas.getSelectionModel().getSelectedItem();
                    if (seleccionada != null) {
                        abrirInfoBici(seleccionada);
                    }
                }
            });
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
    private void añadirBicicleta(ActionEvent event) {
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


    @FXML
    private void mostrarInventario(ActionEvent event) {
        try {
            Parent contenido = loadFxmlTry("/view/proyecto_tfg/inventario.fxml");
            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir el inventario.");
        }
    }

    @FXML
    private void vistaEliminarBicicleta(ActionEvent event){
        try {
            Parent contenido = loadFxmlTry(
                    "/view/proyecto_tfg/eliminarBicicleta.fxml"
            );
            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void buscarBicicleta(ActionEvent event) {
        if (txtBuscarRef == null || txtBuscarRef.getText().trim().isEmpty()) {
            showAlert("Aviso", "Introduce una referencia para buscar.");
            return;
        }

        Bicicleta bici = buscarService.buscarBicicleta(txtBuscarRef.getText().trim());

        if (bici == null) {
            showAlert("No encontrada", "No existe ninguna bicicleta con esa referencia.");
            // Limpiar labels
            if (lblRefBici != null)        lblRefBici.setText("—");
            if (lblEstadoBici != null)     lblEstadoBici.setText("—");
            if (lblMarcaBici != null)      lblMarcaBici.setText("—");
            if (lblModeloBici != null)     lblModeloBici.setText("—");
            if (lblFrenosBici != null)     lblFrenosBici.setText("—");
            if (lblTransmisionBici != null) lblTransmisionBici.setText("—");
            if (lblSuspDelBici != null)    lblSuspDelBici.setText("—");
            if (lblSuspTrasBici != null)   lblSuspTrasBici.setText("—");
            if (lblRuedasBici != null)     lblRuedasBici.setText("—");
            if (lblClienteBici != null)    lblClienteBici.setText("—");
            return;
        }

        // Rellenar labels con los datos de la bici
        if (lblRefBici != null)        lblRefBici.setText(bici.getId_referencia());
        if (lblEstadoBici != null)     lblEstadoBici.setText(bici.getEstado() != null ? bici.getEstado() : "Sin reparar");
        if (lblMarcaBici != null)      lblMarcaBici.setText(bici.getMarca());
        if (lblModeloBici != null)     lblModeloBici.setText(bici.getModelo());
        if (lblFrenosBici != null)     lblFrenosBici.setText(bici.getFrenos());
        if (lblTransmisionBici != null) lblTransmisionBici.setText(bici.getTransmision());
        if (lblSuspDelBici != null)    lblSuspDelBici.setText(bici.getSuspension_delantera());
        if (lblSuspTrasBici != null)   lblSuspTrasBici.setText(bici.getSuspension_trasera());
        if (lblRuedasBici != null)     lblRuedasBici.setText(bici.getRuedas());
        if (lblClienteBici != null) {
            Cliente c = bici.getId_cliente();
            lblClienteBici.setText(c != null ? c.getDni() : "Sin cliente");
        }
    }

    private void abrirInfoBici(Bicicleta bici) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("/view/proyecto_tfg/mostrarInfoBici.fxml")
            );
            Parent contenido = loader.load();

            MostrarInfoBiciController ctrl = loader.getController();
            ctrl.setDatos(bici);  // ← pasamos la bici al nuevo controlador

            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la información de la bicicleta.");
        }
    }


}
