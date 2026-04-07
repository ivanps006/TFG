package org.example.proyecto_tfg.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.proyecto_tfg.HelloApplication;
import org.example.proyecto_tfg.model.*;
import org.example.proyecto_tfg.service.*;
import org.example.proyecto_tfg.utils.InitializeUtils;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class Controller {

    private final loginService loginService = new loginService();
    private final HomeService homeService = new HomeService();
    private final AddService addService = new AddService();
    private final ClienteService clienteService = new ClienteService();
    private final BuscarService buscarService = new BuscarService();
    private final DeleteService deleteService = new DeleteService();

    @FXML private BorderPane root;
    @FXML private VBox dashboardView;

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasenia;
    @FXML private Label ErrorLogin;

    @FXML private Label lblFecha;
    @FXML private Label lblNumeroBicisSinReparar;
    @FXML private TextField tfCorreo;


    // Campos para añadir clientes
    @FXML private TextField tfUsuario;
    @FXML private PasswordField pfContrasenia;
    @FXML private TextField tfNombre;
    @FXML private TextField tfApellido;
    @FXML private TextField tfDni;
    @FXML private TextField tfTelefono;
    @FXML private TextField tfDireccion;
    @FXML private Label lblMensajeRegistro;

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

    //Metodo Initialize
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

            // Enviar email de bienvenida
            if (mecanico.getCorreo() != null && !mecanico.getCorreo().isEmpty()) {
                new EmailService().enviarEmailBienvenida(mecanico.getNombre(), mecanico.getCorreo());
            }

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

    //Metodo para cerrar el programa y volver al login
    @FXML
    private void cerrarPrograma(ActionEvent event) {
        //Lo que hace es cerrar la escena actual
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    //Metodo para Registrar un nuevo usuario (mecanico) en la BD
    @FXML
    private void registrarUsuario(ActionEvent event) {
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
        limpiarCamposRegistro();
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

    //Metodo para volver al Dashboard desde cualquer sitio
    @FXML
    private void volverPaginaPrincipal(ActionEvent event) {
        NavigationService.getInstance().goBack();
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
        cerrarPrograma(event);
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

    //Metodo que muestra el inventario
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
        try {
            Parent contenido = loadFxmlTry("/view/proyecto_tfg/eliminarBicicleta.fxml");
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
        try {
            Parent contenido = loadFxmlTry("/view/proyecto_tfg/eliminarCliente.fxml");
            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void eliminarBicicleta(ActionEvent event){
        deleteService.eliminarBicicleta(txtBuscarRef.getText().trim());
        showAlert("Éxito", "Bicicleta eliminada correctamente.");
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
        String dni = lblDniCliente != null ? lblDniCliente.getText() : "—";

        if (dni.equals("—")) {
            showAlert("Aviso", "Primero debes buscar un cliente para eliminarlo.");
            return;
        }

        deleteService.eliminarCliente(dni);
        showAlert("Éxito", "Cliente eliminado correctamente.");
        limpiarDatosCliente();
        txtBuscarDniCliente.clear();
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

    // Método para editar mantenimiento
    @FXML
    private void editarMantenimiento(ActionEvent event) {
        Mantenimiento seleccionado = tablaMantenimientos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            showAlert("Aviso", "Selecciona un mantenimiento para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/añadirMantenimiento.fxml"));
            Parent contenido = loader.load();

            // Pasar el mantenimiento seleccionado al controller de la vista
            // (Necesitarás agregar un método setDatos en el controller de añadirMantenimiento)

            NavigationService.getInstance().openInCenter(contenido);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir el formulario de editar mantenimiento.");
        }
    }

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



}
