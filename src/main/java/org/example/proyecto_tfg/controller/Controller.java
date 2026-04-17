package org.example.proyecto_tfg.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.proyecto_tfg.HelloApplication;
import org.example.proyecto_tfg.model.*;
import org.example.proyecto_tfg.service.*;
import org.example.proyecto_tfg.utils.InitializeUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Controller {

    private final loginService loginService = new loginService();
    private final HomeService homeService = new HomeService();
    private final AddService addService = new AddService();
    private final ClienteService clienteService = new ClienteService();
    private final BuscarService buscarService = new BuscarService();
    private final DeleteService deleteService = new DeleteService();
    private final InventarioService inventarioService = new InventarioService();
    private final ExportarService exportarService = new ExportarService();
    private final EmailService emailService = new EmailService();

    @FXML private BorderPane root;
    @FXML private VBox dashboardView;

    // editarPieza.fxml
    @FXML private Label lblPiezaId;
    @FXML private TextField tfPiezaModelo;
    @FXML private TextField tfPiezaMarca;
    @FXML private TextField tfPiezaTipo;
    @FXML private Spinner<Integer> spPiezaStock;
    @FXML private TextField tfPiezaPrecio;

    @FXML private TextField tfBicicletaRefFacturaFiltro;
    @FXML private TextField tfClienteDniFacturaFiltro;

    // Pieza que se está editando (no es @FXML)
    private Pieza piezaEnEdicion;


    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasenia;
    @FXML private Label ErrorLogin;

    @FXML private Label lblFecha;
    @FXML private Label lblNumeroBicisSinReparar;

    // eliminarMantenimiento.fxml
    @FXML private TextField txtBuscarIdMantenimientoEliminar;

    @FXML private Label lblIdMantenimientoEliminar;
    @FXML private Label lblIdMecanicoEliminar;
    @FXML private Label lblIdBicicletaEliminar;
    @FXML private Label lblFechaEliminar;
    @FXML private Label lblHorasEliminar;
    @FXML private Label lblObservacionesEliminar;
    private Mantenimiento mantenimientoAEliminar;
    private Cliente clienteAEliminar;
    private Bicicleta bicicletaAEliminar;


    // Facturas - Campos para añadir
    @FXML private TextField tfClienteDniAnadirFactura;
    @FXML private Label lblClienteInfoFactura;
    @FXML private TextField tfRefBicicletaAnadirFactura;
    @FXML private Label lblBicicletaInfoFactura;
    @FXML private DatePicker dpFechaFactura;
    @FXML private TextField tfTotalFactura;
    @FXML private Label lblErrorTotalFactura;

    // Facturas - Cliente y Bicicleta en edición
    private Cliente clienteParaFactura;
    private Bicicleta bicicletaParaFactura;

    // Facturas - Campos para eliminar
    @FXML private Label lblIdFacturaEliminar;
    @FXML private Label lblFechaFacturaEliminar;
    @FXML private Label lblClienteFacturaEliminar;
    @FXML private Label lblBicicletaFacturaEliminar;
    @FXML private Label lblTotalFacturaEliminar;

    // Factura siendo eliminada
    private Factura facturaAEliminar;

    // Instancia del servicio
    private final FacturaService facturaService = new FacturaService();


    // Campos para registrar mecanicos
    @FXML private TextField tfUsuario;
    @FXML private PasswordField pfContrasenia;
    @FXML private TextField tfNombre;
    @FXML private TextField tfApellido;
    @FXML private TextField tfCorreo;
    @FXML private TextField tfDni;
    @FXML private TextField tfTelefono;
    @FXML private TextField tfDireccion;
    @FXML private Label lblMensajeRegistro;
    @FXML private Label lblErrorNombre;
    @FXML private Label lblErrorApellido;
    @FXML private Label lblErrorUsuario;
    @FXML private Label lblErrorContarsenia;
    @FXML private Label lblErrorCorreo;
    @FXML private Label lblErrorDNI;
    @FXML private Label lblErrorTelefono;
    @FXML private Label lblErrorDireccion;

    //Campos para eliminar Clientes
// ========== ELIMINAR CLIENTE ==========
    @FXML private TextField txtBuscarDniCliente;
    @FXML private Label lblDniCliente;
    @FXML private Label lblNombreCliente;
    @FXML private Label lblApellidoCliente;
    @FXML private Label lblTelefonoCliente;
    @FXML private Label lblDireccionCliente;


    // Datos de la vista Mantenimiento (campos de bici)
    @FXML private TextField txtReferencia;
    @FXML private TextField txtMarca;
    @FXML private TextField txtModelo;
    @FXML private TextField txtFrenos;
    @FXML private TextField txtSuspDel;
    @FXML private TextField txtSuspTras;
    @FXML private TextField txtTransmision;
    @FXML private TextField txtRuedas;
    @FXML private TextField txtClienteDni;

    // Tabla de bicis (extra columns)
    @FXML private TableColumn<Bicicleta, String> colFrenosBici;
    @FXML private TableColumn<Bicicleta, String> colSuspDel;
    @FXML private TableColumn<Bicicleta, String> colSuspTras;
    @FXML private TableColumn<Bicicleta, String> colTransmision;
    @FXML private TableColumn<Bicicleta, String> colRuedas;
    @FXML private TextField tfBuscarBicicleta;
    @FXML private Label lblTotalBicicletas;
    @FXML private TextField txtBuscarRef;

    // Datos Clientes
    @FXML private TextField txtDni;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;

    // Dashboard bicis
    @FXML private TableView<Bicicleta> tablaBicicletas;
    @FXML private TableColumn<Bicicleta, String> colRef;
    @FXML private TableColumn<Bicicleta, String> colMarca;
    @FXML private TableColumn<Bicicleta, String> colModelo;
    @FXML private TableColumn<Bicicleta, String> colCliente;
    @FXML private TableColumn<Bicicleta, String> colEstado;

    // Tabla clientes
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colDni;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colApellidos;
    @FXML private TableColumn<Cliente, String> colTelefono;
    @FXML private Label lblTotalClientes;

    @FXML private ImageView logoEmpresa;

    // Inventario
    @FXML private TableView<Pieza> tablaPiezas;
    @FXML private TableColumn<Pieza, String> colIdPieza;
    @FXML private TableColumn<Pieza, String> colModeloPieza;
    @FXML private TableColumn<Pieza, String> colMarcaPieza;
    @FXML private TableColumn<Pieza, String> colTipoPieza;
    @FXML private TableColumn<Pieza, String> colStockPieza;
    @FXML private TableColumn<Pieza, String> colPrecioPieza;
    @FXML private Label lblTotalPiezas;

    // FILTRO DE PIEZAS
    @FXML private TextField tfBuscarPieza;
    private String tipoFiltroActual = null;  // Almacena el tipo seleccionado



    // Labels eliminar bici
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

    // Facturas
    @FXML private TableView<Factura> tablaFacturas;
    @FXML private TableColumn<Factura, String> colIdFactura;
    @FXML private TableColumn<Factura, String> colFechaFactura;
    @FXML private TableColumn<Factura, String> colClienteFactura;
    @FXML private TableColumn<Factura, String> colBiciFactura;
    @FXML private TableColumn<Factura, String> colTotalFactura;

    // Mantenimientos
    @FXML private TableView<Mantenimiento> tablaMantenimientos;
    @FXML private TableColumn<Mantenimiento, String> colIdMantenimiento;
    @FXML private TableColumn<Mantenimiento, String> colIdMecanico;
    @FXML private TableColumn<Mantenimiento, String> colIdBicicleta;
    @FXML private TableColumn<Mantenimiento, String> colFecha;
    @FXML private TableColumn<Mantenimiento, String> colHoras;
    @FXML private TableColumn<Mantenimiento, String> colObservaciones;

    @FXML private Label lblTotalMantenimientos;
    @FXML private Label lblNumeroBicisReparadas;
    @FXML private Label lblNumeroBicisEnReparacion;
    @FXML private Label lblNumeroClientes;

    @FXML
    public void initialize() {
        if (root != null) {
            NavigationService.getInstance().setRoot(root);
        }

        // Doble clic en bici -> info
        if (tablaBicicletas != null) {
            tablaBicicletas.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Bicicleta seleccionada = tablaBicicletas.getSelectionModel().getSelectedItem();
                    if (seleccionada != null) abrirInfoBici(seleccionada);
                }
            });
        }

        if (tablaPiezas != null) {
            tablaPiezas.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Pieza p = tablaPiezas.getSelectionModel().getSelectedItem();
                    if (p != null) {
                        abrirEditarPiezaDesdeSeleccion(p);
                    }
                }
            });
        }

        // Doble clic en factura -> eliminar
        if (tablaFacturas != null) {
            tablaFacturas.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Factura seleccionada = tablaFacturas.getSelectionModel().getSelectedItem();
                    if (seleccionada != null) {
                        abrirEliminarFacturaDirecto(seleccionada);
                    }
                }
            });
        }

        // Una sola línea que lo inicializa TODO
        new InitializeUtils().initializeAllViews(
                tablaBicicletas, colRef, colMarca, colModelo, colCliente, colEstado,
                colFrenosBici, colSuspDel, colSuspTras, colTransmision, colRuedas,
                lblTotalBicicletas, lblFecha, lblNumeroBicisSinReparar,
                tablaClientes, colDni, colNombre, colApellidos, colTelefono, lblTotalClientes,
                logoEmpresa,
                tablaPiezas, colIdPieza, colModeloPieza, colMarcaPieza, colTipoPieza,
                colStockPieza, colPrecioPieza, lblTotalPiezas,
                tablaFacturas, colIdFactura, colFechaFactura, colClienteFactura, colBiciFactura, colTotalFactura,
                tablaMantenimientos, colIdMantenimiento, colIdMecanico, colIdBicicleta,
                colFecha, colHoras, colObservaciones, lblTotalMantenimientos,
                lblNumeroBicisEnReparacion, lblNumeroBicisReparadas, lblNumeroClientes
        );
    }



    //Metodo para validar las credenciales cuando Inicias Sesion
    @FXML
    private void validarCredenciales() throws IOException {
        if (txtUsuario.getText().isEmpty() || txtContrasenia.getText().isEmpty()) {
            ErrorLogin.setText("Por favor, complete todos los campos.");
            ErrorLogin.setStyle("-fx-text-fill: red;");
            return;
        }

        Mecanico mecanico = loginService.consultarMecanico(txtUsuario.getText(), txtContrasenia.getText());

        if (mecanico != null) {
            ErrorLogin.setText("Inicio de sesión exitoso.");
            ErrorLogin.setStyle("-fx-text-fill: green;");
            limpiarCamposLogin();

            // En lugar de abrir una nueva ventana, cargamos la vista principal
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/principalWindows-view.fxml"));
                Parent mainView = fxmlLoader.load();

                // Reemplazamos la escena actual con la vista principal
                StackPane stackPrincipal = (StackPane) HelloApplication.mainStage.getScene().getRoot();
                stackPrincipal.getChildren().clear();
                stackPrincipal.getChildren().add(mainView);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "No se pudo cargar la ventana principal.");
            }
        } else {
            ErrorLogin.setText("Credenciales incorrectas. Inténtelo de nuevo.");
            ErrorLogin.setStyle("-fx-text-fill: red;");
            limpiarCamposLogin();
        }
    }



    //Metodo para guardar clientes(Añadirlo a la BD)
    @FXML
    private void guardarCliente(ActionEvent event) {
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

    //Este metodo te abre la vista para registrarte
    @FXML
    private void onRegisterButtonClick(ActionEvent event) {
        try {
            HelloApplication.cargarVistaRegister();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir el formulario de registro.");
        }
    }


    //Metodo para cerrar el programa y volver al login
    @FXML
    private void cerrarRegistro(ActionEvent event) {
        //Lo que hace es cerrar la escena actual
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    //Metodo para Registrar un nuevo usuario (mecanico) en la BD
    @FXML
    private void registrarUsuario(ActionEvent event) {
        if (!validarRegistro()) return;
        loginService.añadirMecanico(new Mecanico(
                tfDni.getText(),              // dni
                tfNombre.getText(),           // nombre
                tfApellido.getText(),         // apellidos
                tfCorreo.getText(),           // email ✓
                tfUsuario.getText(),          // usuario
                pfContrasenia.getText(),      // contrasena
                tfTelefono.getText(),         // telefono
                tfDireccion.getText()         // direccion
        ));
        lblMensajeRegistro.setText("Registro exitoso.");
        lblMensajeRegistro.setStyle("-fx-text-fill: green;");
        emailService.enviarEmailBienvenida(tfNombre.getText(), tfCorreo.getText());
        System.out.println("Email enviado correctamente");
        limpiarCamposRegistro();
    }

    private boolean validarRegistro() {
        boolean ok = true;

        // Limpia errores previos
        lblErrorDNI.setText("");
        lblErrorUsuario.setText("");
        lblErrorContarsenia.setText("");
        lblErrorNombre.setText("");

        String dni = tfDni.getText() == null ? "" : tfDni.getText().trim();
        String usuario = tfUsuario.getText() == null ? "" : tfUsuario.getText().trim();
        String pass = pfContrasenia.getText() == null ? "" : pfContrasenia.getText().trim();
        String nombre = tfNombre.getText() == null ? "" : tfNombre.getText().trim();

        if (dni.isEmpty()) {
            lblErrorDNI.setText("Debes poner tu DNI");
            lblErrorDNI.setStyle("-fx-text-fill: red;");
            ok = false;
        }
        if (usuario.isEmpty()) {
            lblErrorUsuario.setText("Debes poner un usuario");
            lblErrorUsuario.setStyle("-fx-text-fill: red;");
            ok = false;
        }
        if (pass.isEmpty()) {
            lblErrorContarsenia.setText("Debes poner una contraseña");
            lblErrorContarsenia.setStyle("-fx-text-fill: red;");
            ok = false;
        }
        if (nombre.isEmpty()) {
            lblErrorNombre.setText("Debes poner tu nombre");
            lblErrorNombre.setStyle("-fx-text-fill: red;");
            ok = false;
        }

        if (!pass.matches("^[a-zA-Z0-9]{8,15}$")){
            lblErrorContarsenia.setText("Contraseña no válida (8-15 caracteres, solo letras y números)");
            lblErrorContarsenia.setStyle("-fx-text-fill: red;");
            ok = false;
        }

        if (tfCorreo.getText().isEmpty()) {
            lblErrorCorreo.setText("Debes poner un correo");
            lblErrorCorreo.setStyle("-fx-text-fill: red;");
            ok = false;
        } else if (!tfCorreo.getText().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            lblErrorCorreo.setText("Correo no válido");
            lblErrorCorreo.setStyle("-fx-text-fill: red;");
            ok = false;
        }

        return ok;
    }


    //Metodo para limpiar los campos una vez le has dado a registrar
    private void limpiarCamposRegistro() {
        tfUsuario.clear();
        pfContrasenia.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfDni.clear();
        tfTelefono.clear();
        tfDireccion.clear();
        tfCorreo.clear();
    }


    //Metodo para limpiar los campos del registro
    private void limpiarCamposLogin() {
        txtUsuario.clear();
        txtContrasenia.clear();
    }

    /*
        Metodo para cargar las distintas vistas dentro del mismo programa
        Busca las distintas rutas por si se le pasa mas de una vista
        Por ejemplo añadirClientes.fxml o anadirClientes.fxml
        Si añadirClientes.fxml no existe intentara anadirClientes.fxml, lo que
        nos ahorrara errores por pequeñas diferencias de letras
     */
    private Parent loadFxmlTry(String... paths) throws IOException {
        for (String p : paths) {
            URL url = HelloApplication.class.getResource(p);
            if (url != null) {
                return new FXMLLoader(url).load();
            }
        }
        throw new IOException("No se encontró el recurso FXML entre: " + Arrays.toString(paths));
    }

    //Metodo que abre la vista para añadir clientes
    @FXML
    private void añadirCliente(ActionEvent event) {
        try {
            //Intenta cargar la vista
            Parent contenido = loadFxmlTry("/view/proyecto_tfg/añadirCliente.fxml");
            //Usa el servicio de navegacion para cargar esta vista en el centro
            NavigationService.getInstance().openInCenter(contenido);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir el formulario de añadir cliente.");
        }
    }

    //Metodo que guarda una bicicleta nieva en la BD
    @FXML
    private void guardarBicicleta(ActionEvent event) {
        String ref = txtReferencia != null ? txtReferencia.getText().trim() : "";
        String dni = txtClienteDni != null ? txtClienteDni.getText().trim() : "";
        if (ref.isEmpty() || dni.isEmpty()) {
            showAlert("Aviso", "Referencia y DNI del cliente son obligatorios.");
            return;
        }

        //Debe buscar un Cliente ya que la bicicleta esta relacionada
        //Con un cliente
        Cliente cliente = addService.buscarClientePorDni(dni);
        if (cliente == null) {
            showAlert("Aviso", "Cliente no encontrado con DNI: " + dni);
            return;
        }

        Bicicleta bici = new Bicicleta(
                ref,
                txtMarca != null ? txtMarca.getText() : "",
                txtModelo != null ? txtModelo.getText() : "",
                txtFrenos != null ? txtFrenos.getText() : "",
                txtSuspDel != null ? txtSuspDel.getText() : "",
                txtSuspTras != null ? txtSuspTras.getText() : "",
                txtTransmision != null ? txtTransmision.getText() : "",
                txtRuedas != null ? txtRuedas.getText() : "",
                cliente
        );
        addService.añadirBicicleta(bici);
        NavigationService.getInstance().goBack();
    }


    //Metodo que cierra el programa desde cualquier sitio
    @FXML
    private void volverLogin(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/hello-view.fxml"));
            Parent loginView = fxmlLoader.load();

            StackPane stackPrincipal = (StackPane) HelloApplication.mainStage.getScene().getRoot();
            stackPrincipal.getChildren().clear();
            stackPrincipal.getChildren().add(loginView);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la vista de login.");
        }
    }


    //Metodo para abrir la vista para añadir bicicletas
    @FXML
    private void añadirBicicleta(ActionEvent event) {
        try {
            Parent contenido = loadFxmlTry("/view/proyecto_tfg/añadirBicicleta-view.fxml");
            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir añadir bicicleta.");
        }
    }

    //Metodo para mostrar alertas
    private void showAlert(String title, String message) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        } catch (Exception ignored) { }
    }

    //Metodo que muestra la vista clientes
    @FXML
    private void mostrarClientes(ActionEvent event) {
        try {
            clienteService.cargarClientesDesdeBD();
            Parent contenido = loadFxmlTry("/view/proyecto_tfg/clientes.fxml");
            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la vista de clientes.");
        }
    }

    //Metodo que vuelve a la pagina principal
    @FXML
    private void mostrarDashboard(ActionEvent event) {
        root.setCenter(dashboardView);
    }

    //Metodo para volver a la pagina principal
    @FXML
    private void volverPaginaPrincipal(ActionEvent event) {
        if (root != null && dashboardView != null) {
            root.setCenter(dashboardView);
        } else {
            NavigationService.getInstance().goBack();
        }
    }



    //Metodo que muestra la vista de bicicletas
    @FXML
    private void mostrarBicicleta(ActionEvent event) {
        try {
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

            Parent contenido = loadFxmlTry("/view/proyecto_tfg/bicicletas.fxml");
            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
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
    private void vistaEliminarBicicleta(ActionEvent event) {
        Bicicleta seleccionada = tablaBicicletas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            showAlert("Aviso", "Selecciona una bicicleta en la tabla.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/eliminarBicicleta.fxml"));
            Parent contenido = loader.load();

            Controller ctrl = loader.getController();
            ctrl.setBicicletaAEliminar(seleccionada);

            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la vista de eliminar bicicleta.");
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
            if (lblRefBici != null) lblRefBici.setText("—");
            if (lblEstadoBici != null) lblEstadoBici.setText("—");
            if (lblMarcaBici != null) lblMarcaBici.setText("—");
            if (lblModeloBici != null) lblModeloBici.setText("—");
            if (lblFrenosBici != null) lblFrenosBici.setText("—");
            if (lblTransmisionBici != null) lblTransmisionBici.setText("—");
            if (lblSuspDelBici != null) lblSuspDelBici.setText("—");
            if (lblSuspTrasBici != null) lblSuspTrasBici.setText("—");
            if (lblRuedasBici != null) lblRuedasBici.setText("—");
            if (lblClienteBici != null) lblClienteBici.setText("—");
            return;
        }

        if (lblRefBici != null) lblRefBici.setText(bici.getId_referencia());
        if (lblEstadoBici != null) lblEstadoBici.setText(bici.getEstado() != null ? bici.getEstado() : "Sin reparar");
        if (lblMarcaBici != null) lblMarcaBici.setText(bici.getMarca());
        if (lblModeloBici != null) lblModeloBici.setText(bici.getModelo());
        if (lblFrenosBici != null) lblFrenosBici.setText(bici.getFrenos());
        if (lblTransmisionBici != null) lblTransmisionBici.setText(bici.getTransmision());
        if (lblSuspDelBici != null) lblSuspDelBici.setText(bici.getSuspension_delantera());
        if (lblSuspTrasBici != null) lblSuspTrasBici.setText(bici.getSuspension_trasera());
        if (lblRuedasBici != null) lblRuedasBici.setText(bici.getRuedas());
        if (lblClienteBici != null) {
            Cliente c = bici.getId_cliente();
            lblClienteBici.setText(c != null ? c.getDni() : "Sin cliente");
        }
    }

    private void abrirInfoBici(Bicicleta bici) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/mostrarInfoBici.fxml"));
            Parent contenido = loader.load();

            MostrarInfoBiciController ctrl = loader.getController();
            ctrl.setDatos(bici);

            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la información de la bicicleta.");
        }
    }

    @FXML
    private void abrirVistaFactura(ActionEvent event) {
        try {
            Parent contenido = loadFxmlTry("/view/proyecto_tfg/factura.fxml");
            NavigationService.getInstance().openInCenter(contenido);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void abrirVistaMantenimiento(ActionEvent event) {
        try {
            Parent contenido = loadFxmlTry("/view/proyecto_tfg/mantenimiento.fxml");
            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void abrirVistaEliminarCliente(ActionEvent event) {
        Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            showAlert("Aviso", "Selecciona un cliente en la tabla.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/eliminarCliente.fxml"));
            Parent contenido = loader.load();

            Controller ctrl = loader.getController();
            ctrl.setClienteAEliminar(seleccionado);

            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la vista de eliminar cliente.");
        }
    }


    @FXML
    private void eliminarBicicleta(ActionEvent event) {
        if (bicicletaAEliminar == null) {
            showAlert("Aviso", "No hay bicicleta seleccionada para eliminar.");
            return;
        }

        // Confirmación antes de eliminar
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de que deseas eliminar esta bicicleta? Esta acción no se puede deshacer.");

        if (confirmacion.showAndWait().isPresent() && confirmacion.getResult().equals(ButtonType.OK)) {
            deleteService.eliminarBicicleta(bicicletaAEliminar.getId_referencia());
            showAlert("Éxito", "Bicicleta eliminada correctamente.");
            NavigationService.getInstance().goBack();
        }
    }


    private void limpiarDatosCliente() {
        if (lblDniCliente != null) lblDniCliente.setText("—");
        if (lblNombreCliente != null) lblNombreCliente.setText("—");
        if (lblApellidoCliente != null) lblApellidoCliente.setText("—");
        if (lblTelefonoCliente != null) lblTelefonoCliente.setText("—");
        if (lblDireccionCliente != null) lblDireccionCliente.setText("—");
    }

    @FXML
    private void eliminarCliente(ActionEvent event) {
        if (clienteAEliminar == null) {
            showAlert("Aviso", "No hay cliente seleccionado para eliminar.");
            return;
        }
        deleteService.eliminarCliente(clienteAEliminar.getDni());
        showAlert("Éxito", "Cliente eliminado correctamente.");
        NavigationService.getInstance().goBack();
    }


    // Agregar este campo en la sección de declaración de @FXML (alrededor de la línea 79-81)
    @FXML private ComboBox<String> cmbEstadoBici;

    // Agregar este método después de otros métodos de bicicletas (alrededor de la línea 560)
    @FXML
    private void guardarEstadoBici(ActionEvent event) {
        Bicicleta seleccionada = tablaBicicletas.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            showAlert("Aviso", "Selecciona una bicicleta primero.");
            return;
        }

        String estadoSeleccionado = cmbEstadoBici.getValue();
        if (estadoSeleccionado == null) {
            showAlert("Aviso", "Selecciona un estado.");
            return;
        }

        seleccionada.setEstado(estadoSeleccionado);
        addService.actualizarBicicleta(seleccionada);
        tablaBicicletas.refresh();

        showAlert("Éxito", "Estado actualizado a: " + estadoSeleccionado);
    }

    @FXML
    private void buscarBicicletaPorMarca(ActionEvent event) {
        if (tablaBicicletas == null) {
            showAlert("Aviso", "La tabla no está disponible.");
            return;
        }

        String marca = tfBuscarBicicleta != null ? tfBuscarBicicleta.getText().trim() : "";

        if (marca.equalsIgnoreCase("todas") || marca.isEmpty()) {
            // Si el campo está vacío, mostrar todas las bicicletas
            ObservableList<Bicicleta> todas = homeService.cargarBicicletasDesdeBD();
            tablaBicicletas.setItems(todas);
            return;
        }

        // Filtrar bicicletas por marca
        ObservableList<Bicicleta> todas = homeService.cargarBicicletasDesdeBD();
        ObservableList<Bicicleta> filtradas = todas.filtered(b ->
                b.getMarca().toLowerCase().contains(marca.toLowerCase())
        );

        if (filtradas.isEmpty()) {
            showAlert("Sin resultados", "No se encontraron bicicletas con marca: " + marca);
            tablaBicicletas.setItems(todas); // Mostrar todas si no hay resultados
        } else {
            tablaBicicletas.setItems(filtradas);
        }
    }


    // Campo para búsqueda de clientes
    @FXML private TextField tfBuscarCliente;

    // Método para buscar clientes
    @FXML
    private void buscarClientes(ActionEvent event) {
        if (tablaClientes == null) {
            showAlert("Aviso", "La tabla no está disponible.");
            return;
        }

        String texto = tfBuscarCliente != null ? tfBuscarCliente.getText().trim() : "";

        ObservableList<Cliente> clientesAMostrar;

        if (texto.isEmpty()) {
            // Si el campo está vacío, mostrar todos los clientes
            clientesAMostrar = clienteService.cargarClientesDesdeBD();
        } else {
            // Buscar por nombre o DNI
            clientesAMostrar = buscarService.buscarClientesPorTexto(texto);

            if (clientesAMostrar.isEmpty()) {
                showAlert("Sin resultados", "No se encontraron clientes con: " + texto);
                clientesAMostrar = clienteService.cargarClientesDesdeBD();
            }
        }

        tablaClientes.setItems(clientesAMostrar);
    }

    // Campo para búsqueda de mantenimientos
    @FXML private TextField tfBuscarMantenimiento;

    // Instancia del servicio
    private final MantenimientoService mantenimientoService = new MantenimientoService();

    // Método para buscar mantenimientos
    @FXML
    private void buscarMantenimientos(ActionEvent event) {
        if (tablaMantenimientos == null) {
            showAlert("Aviso", "La tabla no está disponible.");
            return;
        }

        String texto = tfBuscarMantenimiento != null ? tfBuscarMantenimiento.getText().trim() : "";

        ObservableList<Mantenimiento> mantenimientosAMostrar;

        if (texto.isEmpty()) {
            mantenimientosAMostrar = mantenimientoService.cargarMantenimientos();
        } else {
            mantenimientosAMostrar = mantenimientoService.buscarMantenimientosPorTexto(texto);

            if (mantenimientosAMostrar.isEmpty()) {
                showAlert("Sin resultados", "No se encontraron mantenimientos con: " + texto);
                mantenimientosAMostrar = mantenimientoService.cargarMantenimientos();
            }
        }

        tablaMantenimientos.setItems(mantenimientosAMostrar);
    }

    // Método para añadir mantenimiento
    @FXML
    private void añadirMantenimiento(ActionEvent event) {
        try {
            Parent contenido = loadFxmlTry("/view/proyecto_tfg/añadirMantenimiento.fxml");
            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir el formulario de añadir mantenimiento.");
        }
    }

//    // Método para editar mantenimiento
//    // Método para editar mantenimiento (ACTUALIZADO)
//    @FXML
//    private void editarMantenimiento(ActionEvent event) {
//        Mantenimiento seleccionado = tablaMantenimientos.getSelectionModel().getSelectedItem();
//
//        if (seleccionado == null) {
//            showAlert("Aviso", "Selecciona un mantenimiento para editar.");
//            return;
//        }
//
//        try {
//            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/editarMantenimiento.fxml"));
//            Parent contenido = loader.load();
//
//            // Pasar el mantenimiento seleccionado al controller
//            Controller ctrl = loader.getController();
//            ctrl.setMantenimientoEnEdicion(seleccionado);
//
//            NavigationService.getInstance().openInCenter(contenido);
//        } catch (IOException e) {
//            e.printStackTrace();
//            showAlert("Error", "No se pudo abrir el formulario de editar mantenimiento.");
//        }
//    }


    // Método para eliminar mantenimiento
    @FXML
    private void eliminarMantenimiento(ActionEvent event) {
        Mantenimiento seleccionado = tablaMantenimientos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            showAlert("Aviso", "Selecciona un mantenimiento para eliminar.");
            return;
        }

        // Confirmación antes de eliminar
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de que deseas eliminar este mantenimiento?");

        if (confirmacion.showAndWait().isPresent() && confirmacion.getResult().equals(ButtonType.OK)) {
            mantenimientoService.eliminarMantenimiento(seleccionado.getId_mantenimiento());
            showAlert("Éxito", "Mantenimiento eliminado correctamente.");

            // Recargar la tabla
            ObservableList<Mantenimiento> actualizados = mantenimientoService.cargarMantenimientos();
            tablaMantenimientos.setItems(actualizados);
        }
    }

    @FXML
    private void abrirEditarPieza(ActionEvent event) {
        if (tablaPiezas == null) {
            showAlert("Aviso", "La tabla de piezas no está disponible.");
            return;
        }

        Pieza seleccionada = tablaPiezas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            showAlert("Aviso", "Selecciona una pieza para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/editarPieza.fxml"));
            Parent contenido = loader.load();

            Controller ctrl = loader.getController();
            ctrl.setPiezaEnEdicion(seleccionada);

            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la vista de editar pieza.");
        }
    }

    public void setPiezaEnEdicion(Pieza pieza) {
        this.piezaEnEdicion = pieza;

        if (lblPiezaId != null) lblPiezaId.setText(String.valueOf(pieza.getId_pieza()));
        if (tfPiezaModelo != null) tfPiezaModelo.setText(pieza.getModelo());
        if (tfPiezaMarca != null) tfPiezaMarca.setText(pieza.getMarca());
        if (tfPiezaTipo != null) tfPiezaTipo.setText(pieza.getTipo());
        if (tfPiezaPrecio != null) tfPiezaPrecio.setText(String.valueOf(pieza.getPrecio()));

        // Spinner stock (muy importante inicializarlo)
        if (spPiezaStock != null) {
            int stock = pieza.getStock();
            spPiezaStock.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, Math.max(stock, 0))
            );
        }
    }

    @FXML
    private void guardarCambiosPieza(ActionEvent event) {
        if (piezaEnEdicion == null) {
            showAlert("Error", "No hay ninguna pieza cargada para editar.");
            return;
        }

        String modelo = tfPiezaModelo != null ? tfPiezaModelo.getText().trim() : "";
        String marca  = tfPiezaMarca  != null ? tfPiezaMarca.getText().trim()  : "";
        String tipo   = tfPiezaTipo   != null ? tfPiezaTipo.getText().trim()   : "";
        String precioTxt = tfPiezaPrecio != null ? tfPiezaPrecio.getText().trim() : "";

        if (modelo.isEmpty() || marca.isEmpty() || tipo.isEmpty() || precioTxt.isEmpty()) {
            showAlert("Aviso", "Rellena modelo, marca, tipo y precio.");
            return;
        }

        int stock = 0;
        if (spPiezaStock != null && spPiezaStock.getValue() != null) {
            stock = spPiezaStock.getValue();
        }

        float precio;
        try {
            // admite "19,99" o "19.99"
            precio = Float.parseFloat(precioTxt.replace(",", "."));
            if (precio < 0) throw new NumberFormatException();
        } catch (Exception ex) {
            showAlert("Aviso", "Precio no válido. Ejemplo: 19.99");
            return;
        }

        // Actualizar entidad
        piezaEnEdicion.setModelo(modelo);
        piezaEnEdicion.setMarca(marca);
        piezaEnEdicion.setTipo(tipo);
        piezaEnEdicion.setStock(stock);
        piezaEnEdicion.setPrecio(precio);

        try {
            inventarioService.actualizarPieza(piezaEnEdicion);
            showAlert("OK", "Pieza actualizada correctamente.");

            // Refrescar inventario si existe tabla (depende de si al volver se re-inicializa)
            if (tablaPiezas != null) {
                tablaPiezas.setItems(inventarioService.cargarPiezasDesdeBD());
            }

            NavigationService.getInstance().goBack();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo actualizar la pieza en la base de datos.");
        }
    }

    private void abrirEditarPiezaDesdeSeleccion(Pieza pieza) {
        if (pieza == null) {
            showAlert("Aviso", "No hay pieza seleccionada.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("/view/proyecto_tfg/editarPieza.fxml")
            );
            Parent contenido = loader.load();

            Controller ctrl = loader.getController();
            ctrl.setPiezaEnEdicion(pieza);

            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la vista de editar pieza.");
        }
    }

    @FXML
    private void guardarPieza(ActionEvent event) {
        String modelo = tfPiezaModelo != null ? tfPiezaModelo.getText().trim() : "";
        String marca  = tfPiezaMarca  != null ? tfPiezaMarca.getText().trim()  : "";
        String tipo   = tfPiezaTipo   != null ? tfPiezaTipo.getText().trim()   : "";
        String precioTxt = tfPiezaPrecio != null ? tfPiezaPrecio.getText().trim() : "";

        if (modelo.isEmpty() || marca.isEmpty() || tipo.isEmpty() || precioTxt.isEmpty()) {
            showAlert("Aviso", "Rellena modelo, marca, tipo y precio.");
            return;
        }

        int stock = 0;
        if (spPiezaStock != null && spPiezaStock.getValue() != null) {
            stock = spPiezaStock.getValue();
        }

        float precio;
        try {
            // admite "19,99" o "19.99"
            precio = Float.parseFloat(precioTxt.replace(",", "."));
            if (precio < 0) throw new NumberFormatException();
        } catch (Exception ex) {
            showAlert("Aviso", "Precio no válido. Ejemplo: 19.99");
            return;
        }

        // id_pieza es autogenerado -> se deja null
        Pieza nueva = new Pieza(null, modelo, marca, stock, tipo, precio);

        try {
            inventarioService.añadirPieza(nueva);
            showAlert("OK", "Pieza añadida correctamente.");
            NavigationService.getInstance().goBack();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo añadir la pieza en la base de datos.");
        }
    }

    private void prepararFormularioNuevaPieza() {
        if (spPiezaStock != null) {
            spPiezaStock.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 0)
            );
        }
    }

    @FXML
    private void abrirAñadirPieza(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/añadirPieza.fxml"));
            Parent contenido = loader.load();

            Controller ctrl = loader.getController();
            ctrl.prepararFormularioNuevaPieza();

            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la vista de añadir pieza.");
        }
    }

    @FXML
    private void exportarFacturaPDF(ActionEvent event) {
        if (tablaFacturas == null) {
            showAlert("Aviso", "La tabla de facturas no está disponible.");
            return;
        }

        Factura seleccionada = tablaFacturas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            showAlert("Aviso", "Selecciona una factura para exportar.");
            return;
        }

        Factura facturaCompleta =
                new FacturaService().obtenerFacturaConDetalles(seleccionada.getId_factura());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar factura como PDF");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos PDF (*.pdf)", "*.pdf")
        );
        fileChooser.setInitialFileName("factura_" + facturaCompleta.getId_factura() + ".pdf");

        Window ventana = tablaFacturas.getScene().getWindow();
        File destino = fileChooser.showSaveDialog(ventana);

        if (destino == null) {
            return;
        }

        boolean ok = exportarService.generarPdfFactura(facturaCompleta, destino.getAbsolutePath());

        if (ok) {
            showAlert("Éxito", "Factura exportada correctamente en:\n" + destino.getAbsolutePath());
        } else {
            showAlert("Error", "No se pudo exportar la factura a PDF.");
        }
    }

    public void setClienteAEliminar(Cliente c) {
        this.clienteAEliminar = c;

        if (lblDniCliente != null) lblDniCliente.setText(c.getDni());
        if (lblNombreCliente != null) lblNombreCliente.setText(c.getNombre());
        if (lblApellidoCliente != null) lblApellidoCliente.setText(c.getApellidos());
        if (lblTelefonoCliente != null) lblTelefonoCliente.setText(c.getTelefono());
        if (lblDireccionCliente != null) lblDireccionCliente.setText(c.getDireccion());

        // opcional: rellenar el buscador también
        if (txtBuscarDniCliente != null) txtBuscarDniCliente.setText(c.getDni());
    }

    public void setBicicletaAEliminar(Bicicleta b) {
        this.bicicletaAEliminar = b;

        if (lblRefBici != null) lblRefBici.setText(b.getId_referencia());
        if (lblEstadoBici != null) lblEstadoBici.setText(b.getEstado() != null ? b.getEstado() : "Sin reparar");
        if (lblMarcaBici != null) lblMarcaBici.setText(b.getMarca());
        if (lblModeloBici != null) lblModeloBici.setText(b.getModelo());
        if (lblFrenosBici != null) lblFrenosBici.setText(b.getFrenos());
        if (lblTransmisionBici != null) lblTransmisionBici.setText(b.getTransmision());
        if (lblSuspDelBici != null) lblSuspDelBici.setText(b.getSuspension_delantera());
        if (lblSuspTrasBici != null) lblSuspTrasBici.setText(b.getSuspension_trasera());
        if (lblRuedasBici != null) lblRuedasBici.setText(b.getRuedas());

        if (lblClienteBici != null) {
            Cliente c = b.getId_cliente();
            lblClienteBici.setText(c != null ? c.getDni() : "Sin cliente");
        }

        // opcional
        if (txtBuscarRef != null) txtBuscarRef.setText(b.getId_referencia());
    }

    /**
     * Abre un menú con los tipos de piezas disponibles para filtrar
     */
    @FXML
    private void abrirFiltroTipo(ActionEvent event) {
        // Obtener todos los tipos únicos de la BD
        ObservableList<Pieza> todasLasPiezas = inventarioService.cargarPiezasDesdeBD();

        // Extraer tipos únicos
        Set<String> tiposUnicos = todasLasPiezas.stream()
                .map(Pieza::getTipo)
                .filter(tipo -> tipo != null && !tipo.trim().isEmpty())
                .collect(Collectors.toSet());

        // Crear un menú contextual
        ContextMenu menu = new ContextMenu();

        // Opción para "Todos los tipos"
        MenuItem todosItem = new MenuItem("Todos");
        todosItem.setOnAction(e -> {
            tipoFiltroActual = null;
            aplicarFiltrosPiezas();
        });
        menu.getItems().add(todosItem);
        menu.getItems().add(new SeparatorMenuItem());

        // Añadir cada tipo como opción
        tiposUnicos.stream().sorted().forEach(tipo -> {
            MenuItem item = new MenuItem(tipo);
            item.setOnAction(e -> {
                tipoFiltroActual = tipo;
                aplicarFiltrosPiezas();
            });
            menu.getItems().add(item);
        });

        // Mostrar el menú donde está el botón
        Button btnFiltro = (Button) event.getSource();
        menu.show(btnFiltro, Side.BOTTOM, 0, 0);
    }

    /**
     * Aplica los filtros de búsqueda de texto + tipo a la tabla de piezas
     */
    @FXML
    private void aplicarFiltrosPiezas() {
        if (tablaPiezas == null) {
            showAlert("Aviso", "La tabla de piezas no está disponible.");
            return;
        }

        // Obtener todas las piezas de la BD
        ObservableList<Pieza> todasLasPiezas = inventarioService.cargarPiezasDesdeBD();

        // Obtener valor de búsqueda
        String textosBusqueda = tfBuscarPieza != null ? tfBuscarPieza.getText().trim().toLowerCase() : "";

        // Aplicar filtros
        ObservableList<Pieza> piezasFiltradas = todasLasPiezas.filtered(pieza -> {
            // Filtro por tipo
            boolean cumpleTipo = tipoFiltroActual == null ||
                    (pieza.getTipo() != null && pieza.getTipo().equalsIgnoreCase(tipoFiltroActual));

            // Filtro por búsqueda de texto
            boolean cumpleTexto = textosBusqueda.isEmpty() ||
                    (pieza.getModelo() != null && pieza.getModelo().toLowerCase().contains(textosBusqueda)) ||
                    (pieza.getMarca() != null && pieza.getMarca().toLowerCase().contains(textosBusqueda)) ||
                    (pieza.getTipo() != null && pieza.getTipo().toLowerCase().contains(textosBusqueda));

            return cumpleTipo && cumpleTexto;
        });

        tablaPiezas.setItems(piezasFiltradas);
    }

    /**
     * Busca piezas por el texto ingresado en el campo de búsqueda
     */
    @FXML
    private void buscarPiezas(ActionEvent event) {
        aplicarFiltrosPiezas();
    }

    /**
     * Limpia todos los filtros y muestra todas las piezas
     */
    @FXML
    private void limpiarFiltrosPiezas(ActionEvent event) {
        if (tfBuscarPieza != null) tfBuscarPieza.clear();
        tipoFiltroActual = null;
        aplicarFiltrosPiezas();
    }

// ==================== AÑADIR FACTURA ====================

    @FXML
    private void abrirAnadirFactura(ActionEvent event) {
        try {
            Parent contenido = loadFxmlTry("/view/proyecto_tfg/añadirFactura.fxml");
            NavigationService.getInstance().openInCenter(contenido);

            // Inicializar fecha con la de hoy
            if (dpFechaFactura != null) {
                dpFechaFactura.setValue(LocalDate.now());
            }

            // Limpiar campos
            clienteParaFactura = null;
            bicicletaParaFactura = null;
            if (tfClienteDniAnadirFactura != null) tfClienteDniAnadirFactura.clear();
            if (tfRefBicicletaAnadirFactura != null) tfRefBicicletaAnadirFactura.clear();
            if (tfTotalFactura != null) tfTotalFactura.clear();
            if (lblErrorTotalFactura != null) lblErrorTotalFactura.setText("");

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir el formulario de añadir factura.");
        }
    }

    @FXML
    private void buscarClienteParaFactura(ActionEvent event) {
        String dni = tfClienteDniAnadirFactura != null ? tfClienteDniAnadirFactura.getText().trim() : "";

        if (dni.isEmpty()) {
            showAlert("Aviso", "Introduce un DNI de cliente.");
            return;
        }

        Cliente cliente = addService.buscarClientePorDni(dni);

        if (cliente == null) {
            showAlert("No encontrado", "No existe cliente con DNI: " + dni);
            clienteParaFactura = null;
            if (lblClienteInfoFactura != null) lblClienteInfoFactura.setText("—");
        } else {
            clienteParaFactura = cliente;
            if (lblClienteInfoFactura != null) {
                lblClienteInfoFactura.setText(cliente.getNombre() + " " + cliente.getApellidos());
            }
        }
    }

    @FXML
    private void buscarBicicletaParaFactura(ActionEvent event) {
        String ref = tfRefBicicletaAnadirFactura != null ? tfRefBicicletaAnadirFactura.getText().trim() : "";

        if (ref.isEmpty()) {
            // Permitir sin bicicleta
            bicicletaParaFactura = null;
            if (lblBicicletaInfoFactura != null) lblBicicletaInfoFactura.setText("—");
            return;
        }

        Bicicleta bici = buscarService.buscarBicicleta(ref);

        if (bici == null) {
            showAlert("No encontrada", "No existe bicicleta con referencia: " + ref);
            bicicletaParaFactura = null;
            if (lblBicicletaInfoFactura != null) lblBicicletaInfoFactura.setText("—");
        } else {
            bicicletaParaFactura = bici;
            if (lblBicicletaInfoFactura != null) {
                lblBicicletaInfoFactura.setText(bici.getMarca() + " " + bici.getModelo());
            }
        }
    }

    @FXML
    private void guardarFactura(ActionEvent event) {
        // Validar cliente
        if (clienteParaFactura == null) {
            showAlert("Aviso", "Debes buscar y seleccionar un cliente.");
            return;
        }

        // Validar fecha
        LocalDate fecha = dpFechaFactura != null ? dpFechaFactura.getValue() : null;
        if (fecha == null) {
            showAlert("Aviso", "Selecciona una fecha.");
            return;
        }

        // Validar total
        String totalTxt = tfTotalFactura != null ? tfTotalFactura.getText().trim() : "";
        if (totalTxt.isEmpty()) {
            if (lblErrorTotalFactura != null) {
                lblErrorTotalFactura.setText("Debes introducir un total");
                lblErrorTotalFactura.setStyle("-fx-text-fill: red;");
            }
            return;
        }

        BigDecimal total;
        try {
            total = new BigDecimal(totalTxt.replace(",", "."));
            if (total.compareTo(BigDecimal.ZERO) < 0) throw new NumberFormatException();
            if (lblErrorTotalFactura != null) lblErrorTotalFactura.setText("");
        } catch (Exception ex) {
            if (lblErrorTotalFactura != null) {
                lblErrorTotalFactura.setText("Total no válido. Ejemplo: 150.50");
                lblErrorTotalFactura.setStyle("-fx-text-fill: red;");
            }
            return;
        }

        // Crear factura
        Factura nueva = new Factura(fecha, total, clienteParaFactura, bicicletaParaFactura);

        try {
            Factura guardada = facturaService.crearFactura(nueva);
            if (guardada != null) {
                showAlert("Éxito", "Factura creada correctamente con ID: " + guardada.getId_factura());
                NavigationService.getInstance().goBack();
            } else {
                showAlert("Error", "No se pudo guardar la factura.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Error al guardar la factura: " + e.getMessage());
        }
    }

// ==================== ELIMINAR FACTURA ====================

    public void setFacturaAEliminar(Factura f) {
        this.facturaAEliminar = f;

        // Cargar datos completos de la factura
        Factura facturaCompleta = facturaService.obtenerFacturaConDetalles(f.getId_factura());

        if (lblIdFacturaEliminar != null) lblIdFacturaEliminar.setText(String.valueOf(facturaCompleta.getId_factura()));
        if (lblFechaFacturaEliminar != null) lblFechaFacturaEliminar.setText(facturaCompleta.getFecha().toString());
        if (lblTotalFacturaEliminar != null) lblTotalFacturaEliminar.setText(facturaCompleta.getTotal().toString() + " €");

        if (lblClienteFacturaEliminar != null) {
            Cliente cliente = facturaCompleta.getId_cliente();
            lblClienteFacturaEliminar.setText(cliente != null ? cliente.getDni() : "—");
        }

        if (lblBicicletaFacturaEliminar != null) {
            Bicicleta bici = facturaCompleta.getId_bicicleta();
            lblBicicletaFacturaEliminar.setText(bici != null ? bici.getId_referencia() : "—");
        }
    }

    @FXML
    private void eliminarFacturaConfirmada(ActionEvent event) {
        if (facturaAEliminar == null) {
            showAlert("Aviso", "No hay factura seleccionada para eliminar.");
            return;
        }

        // Confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de que deseas eliminar esta factura? Esta acción no se puede deshacer.");

        if (confirmacion.showAndWait().isPresent() && confirmacion.getResult().equals(ButtonType.OK)) {
            try {
                // Usar el DeleteService para eliminar
                deleteService.eliminarFactura(facturaAEliminar.getId_factura());
                showAlert("Éxito", "Factura eliminada correctamente.");
                NavigationService.getInstance().goBack();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "No se pudo eliminar la factura.");
            }
        }
    }

    /**
     * Abre directamente la vista de eliminar con los datos precargados
     */
    @FXML
    private void abrirEliminarFactura(ActionEvent event) {
        Factura seleccionada = tablaFacturas.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            showAlert("Aviso", "Selecciona una factura para eliminar.");
            return;
        }

        abrirEliminarFacturaDirecto(seleccionada);
    }

    /**
     * Abre directamente la vista de eliminar con los datos precargados
     */
    private void abrirEliminarFacturaDirecto(Factura factura) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/eliminarFactura.fxml"));
            Parent contenido = loader.load();

            Controller ctrl = loader.getController();
            ctrl.setFacturaAEliminar(factura);

            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la vista de eliminar factura.");
        }
    }

// ==================== FILTRO DE FACTURAS POR FECHA ====================

    @FXML private DatePicker dpFechaFacturaFiltro;
    @FXML private TextField tfBuscarFactura;

    @FXML
    private void filtrarFacturasPorClienteYBicicleta(ActionEvent event) {
        if (tablaFacturas == null) return;

        String texto = tfBuscarFactura != null ? tfBuscarFactura.getText().trim().toUpperCase() : "";
        ObservableList<Factura> todas = facturaService.cargarFacturasDesdeBD();

        if (texto.isEmpty()) {
            tablaFacturas.setItems(todas);
            return;
        }

        ObservableList<Factura> filtradas = todas.filtered(f ->
                (f.getId_cliente() != null && f.getId_cliente().getDni().toUpperCase().contains(texto)) ||
                        (f.getId_bicicleta() != null && f.getId_bicicleta().getId_referencia().toUpperCase().contains(texto))
        );

        tablaFacturas.setItems(filtradas);
    }



// ==================== FILTROS AVANZADOS DE FACTURAS ====================

    @FXML
    private void filtrarFacturasPorFecha(ActionEvent event) {
        if (tablaFacturas == null) {
            showAlert("Aviso", "La tabla de facturas no está disponible.");
            return;
        }

        LocalDate fecha = dpFechaFacturaFiltro != null ? dpFechaFacturaFiltro.getValue() : null;
        ObservableList<Factura> todas = facturaService.cargarFacturasDesdeBD();

        if (fecha == null) {
            tablaFacturas.setItems(todas);
            return;
        }

        ObservableList<Factura> filtradas = todas.filtered(f -> f.getFecha().isEqual(fecha));
        tablaFacturas.setItems(filtradas);
    }




}