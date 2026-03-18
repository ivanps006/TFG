package org.example.proyecto_tfg.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.proyecto_tfg.model.*;
import org.example.proyecto_tfg.service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class InitializeUtils {

    private final HomeService homeService = new HomeService();
    private final ClienteService clienteService = new ClienteService();
    private final InventarioService inventarioService = new InventarioService();
    private final BuscarService buscarService = new BuscarService();
    private final FacturaService facturaService = new FacturaService();
    private final MantenimientoService mantenimientoService = new MantenimientoService();

    public void initializeAllViews(
            TableView<Bicicleta> tablaBicicletas,
            TableColumn<Bicicleta, String> colRef,
            TableColumn<Bicicleta, String> colMarca,
            TableColumn<Bicicleta, String> colModelo,
            TableColumn<Bicicleta, String> colCliente,
            TableColumn<Bicicleta, String> colEstado,
            TableColumn<Bicicleta, String> colFrenosBici,
            TableColumn<Bicicleta, String> colSuspDel,
            TableColumn<Bicicleta, String> colSuspTras,
            TableColumn<Bicicleta, String> colTransmision,
            TableColumn<Bicicleta, String> colRuedas,
            Label lblTotalBicicletas,
            Label lblFecha,
            Label lblNumeroBicisSinReparar,
            TableView<Cliente> tablaClientes,
            TableColumn<Cliente, String> colDni,
            TableColumn<Cliente, String> colNombre,
            TableColumn<Cliente, String> colApellidos,
            TableColumn<Cliente, String> colTelefono,
            Label lblTotalClientes,
            ImageView logoEmpresa,
            TableView<Pieza> tablaPiezas,
            TableColumn<Pieza, String> colIdPieza,
            TableColumn<Pieza, String> colModeloPieza,
            TableColumn<Pieza, String> colMarcaPieza,
            TableColumn<Pieza, String> colTipoPieza,
            TableColumn<Pieza, String> colStockPieza,
            TableColumn<Pieza, String> colPrecioPieza,
            Label lblTotalPiezas,
            TableView<Factura> tablaFacturas,
            TableColumn<Factura, String> colIdFactura,
            TableColumn<Factura, String> colFechaFactura,
            TableColumn<Factura, String> colClienteFactura,
            TableColumn<Factura, String> colBiciFactura,
            TableColumn<Factura, String> colTotalFactura,
            TableView<Mantenimiento> tablaMantenimientos,
            TableColumn<Mantenimiento, String> colIdMantenimiento,
            TableColumn<Mantenimiento, String> colIdMecanico,
            TableColumn<Mantenimiento, String> colIdBicicleta,
            TableColumn<Mantenimiento, String> colFecha,
            TableColumn<Mantenimiento, String> colHoras,
            TableColumn<Mantenimiento, String> colObservaciones,
            Label lblTotalMantenimientos,
            Label lblNumeroBicisEnReparacion,
            Label lblNumeroBicisReparadas,
            Label lblNumeroClientes) {

        initializeDate(lblFecha);
        initializeBicicletas(tablaBicicletas, colRef, colMarca, colModelo, colCliente, colEstado, colFrenosBici, colSuspDel, colSuspTras, colTransmision, colRuedas, lblTotalBicicletas, lblNumeroBicisSinReparar, lblNumeroBicisEnReparacion, lblNumeroBicisReparadas);
        initializeClientes(tablaClientes, colDni, colNombre, colApellidos, colTelefono, lblTotalClientes, lblNumeroClientes);
        initializeLogo(logoEmpresa);
        initializeInventario(tablaPiezas, colIdPieza, colModeloPieza, colMarcaPieza, colTipoPieza, colStockPieza, colPrecioPieza, lblTotalPiezas);
        initializeFacturas(tablaFacturas, colIdFactura, colFechaFactura, colClienteFactura, colBiciFactura, colTotalFactura);
        initializeMantenimientos(tablaMantenimientos, colIdMantenimiento, colIdMecanico, colIdBicicleta, colFecha, colHoras, colObservaciones, lblTotalMantenimientos);
    }


    private void initializeDate(Label lblFecha) {
        if (lblFecha != null) {
            LocalDate hoy = LocalDate.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM", new Locale("es", "ES"));
            String fechaFormato = hoy.format(formato);
            fechaFormato = fechaFormato.substring(0, 1).toUpperCase() + fechaFormato.substring(1);
            lblFecha.setText(fechaFormato);
        }
    }

    private void initializeBicicletas(
            TableView<Bicicleta> tablaBicicletas,
            TableColumn<Bicicleta, String> colRef,
            TableColumn<Bicicleta, String> colMarca,
            TableColumn<Bicicleta, String> colModelo,
            TableColumn<Bicicleta, String> colCliente,
            TableColumn<Bicicleta, String> colEstado,
            TableColumn<Bicicleta, String> colFrenosBici,
            TableColumn<Bicicleta, String> colSuspDel,
            TableColumn<Bicicleta, String> colSuspTras,
            TableColumn<Bicicleta, String> colTransmision,
            TableColumn<Bicicleta, String> colRuedas,
            Label lblTotalBicicletas,
            Label lblNumeroBicisSinReparar,
            Label lblNumeroBicisEnReparacion,
            Label lblNumeroBicisReparadas) {

        if (tablaBicicletas != null) {
            tablaBicicletas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }

        if (colRef != null) colRef.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId_referencia()));
        if (colMarca != null) colMarca.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMarca()));
        if (colModelo != null) colModelo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getModelo()));

        if (colCliente != null) {
            colCliente.setCellValueFactory(cell -> {
                Cliente c = cell.getValue().getId_cliente();
                return new SimpleStringProperty(c != null ? c.getDni() : "");
            });
        }

        if (colFrenosBici != null) colFrenosBici.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFrenos()));
        if (colSuspDel != null) colSuspDel.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSuspension_delantera()));
        if (colSuspTras != null) colSuspTras.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSuspension_trasera()));
        if (colTransmision != null) colTransmision.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTransmision()));
        if (colRuedas != null) colRuedas.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getRuedas()));

        // Estado con colores
        if (colEstado != null) {
            colEstado.setCellValueFactory(cell ->
                    new SimpleStringProperty(cell.getValue().getEstado() != null ? cell.getValue().getEstado() : "Sin reparar"));

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
                            case "Reparada" -> setStyle("-fx-text-fill: #22c55e; -fx-font-weight: bold;");
                            default -> setStyle("");
                        }
                    }
                }
            });
        }

        var bicicletas = homeService.cargarBicicletasDesdeBD();
        if (tablaBicicletas != null && bicicletas != null) {
            tablaBicicletas.setItems(bicicletas);
        }

        if (lblTotalBicicletas != null && tablaBicicletas != null) {
            lblTotalBicicletas.setText("Total: " + tablaBicicletas.getItems().size() + " bicicletas");
        }

        if (lblNumeroBicisSinReparar != null) {
            int bicisSinReparar = buscarService.buscarBicisSinReparar();
            lblNumeroBicisSinReparar.setText(String.valueOf(bicisSinReparar));
        }

        if(lblNumeroBicisEnReparacion != null){
            int bicisEnReparacion = buscarService.buscarBicisEnReparacion();
            lblNumeroBicisEnReparacion.setText(String.valueOf(bicisEnReparacion));
        }

        if (lblNumeroBicisReparadas != null){
            int bicisReparadas = buscarService.buscarBicisReparadas();
            lblNumeroBicisReparadas.setText(String.valueOf(bicisReparadas));
        }
    }

    private void initializeClientes(
            TableView<Cliente> tablaClientes,
            TableColumn<Cliente, String> colDni,
            TableColumn<Cliente, String> colNombre,
            TableColumn<Cliente, String> colApellidos,
            TableColumn<Cliente, String> colTelefono,
            Label lblTotalClientes, Label lblNumeroClientes) {

        if (tablaClientes != null) {
            tablaClientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            if (colDni != null) colDni.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDni()));
            if (colNombre != null) colNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
            if (colApellidos != null) colApellidos.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getApellidos()));
            if (colTelefono != null) colTelefono.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTelefono()));

            var clientes = clienteService.cargarClientesDesdeBD();
            tablaClientes.setItems(clientes);

            if (lblTotalClientes != null) {
                lblTotalClientes.setText("Total: " + clientes.size() + " clientes");
            }
        }

        if (lblNumeroClientes != null){
            lblNumeroClientes.setText(String.valueOf(buscarService.buscarClientes()));
        }
    }

    private void initializeLogo(ImageView logoEmpresa) {
        if (logoEmpresa != null) {
            Image img = new Image(InitializeUtils.class.getResourceAsStream("/view/img/logo.png"));
            logoEmpresa.setImage(img);
        }
    }

    private void initializeInventario(
            TableView<Pieza> tablaPiezas,
            TableColumn<Pieza, String> colIdPieza,
            TableColumn<Pieza, String> colModeloPieza,
            TableColumn<Pieza, String> colMarcaPieza,
            TableColumn<Pieza, String> colTipoPieza,
            TableColumn<Pieza, String> colStockPieza,
            TableColumn<Pieza, String> colPrecioPieza,
            Label lblTotalPiezas) {

        if (tablaPiezas != null) {
            tablaPiezas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            if (colIdPieza != null)
                colIdPieza.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId_pieza())));
            if (colModeloPieza != null)
                colModeloPieza.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getModelo()));
            if (colMarcaPieza != null)
                colMarcaPieza.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMarca()));
            if (colTipoPieza != null)
                colTipoPieza.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTipo()));

            if (colStockPieza != null) {
                colStockPieza.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getStock())));

                colStockPieza.setCellFactory(col -> new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item);
                            int stock = Integer.parseInt(item);
                            if (stock == 0) setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                            else if (stock <= 5) setStyle("-fx-text-fill: #f59e0b; -fx-font-weight: bold;");
                            else setStyle("-fx-text-fill: #22c55e; -fx-font-weight: bold;");
                        }
                    }
                });
            }

            if (colPrecioPieza != null) {
                colPrecioPieza.setCellValueFactory(cell ->
                        new SimpleStringProperty(String.format("%.2f €", cell.getValue().getPrecio())));
            }

            var piezas = inventarioService.cargarPiezasDesdeBD();
            tablaPiezas.setItems(piezas);

            if (lblTotalPiezas != null) {
                lblTotalPiezas.setText("Total: " + piezas.size() + " piezas");
            }
        }
    }

    private void initializeFacturas(
            TableView<Factura> tablaFacturas,
            TableColumn<Factura, String> colIdFactura,
            TableColumn<Factura, String> colFechaFactura,
            TableColumn<Factura, String> colClienteFactura,
            TableColumn<Factura, String> colBiciFactura,
            TableColumn<Factura, String> colTotalFactura) {

        if (tablaFacturas != null) {
            tablaFacturas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            if (colIdFactura != null)
                colIdFactura.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId_factura())));

            if (colFechaFactura != null)
                colFechaFactura.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFecha().toString()));

            if (colClienteFactura != null)
                colClienteFactura.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId_cliente().getDni()));

            if (colBiciFactura != null)
                colBiciFactura.setCellValueFactory(cell ->
                        new SimpleStringProperty(cell.getValue().getId_bicicleta() != null
                                ? cell.getValue().getId_bicicleta().getId_referencia()
                                : "Sin bicicleta"));

            if (colTotalFactura != null)
                colTotalFactura.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.2f €", cell.getValue().getTotal())));

            List<Factura> facturas = facturaService.obtenerTodasFacturas();
            if (facturas != null) {
                tablaFacturas.setItems(FXCollections.observableArrayList(facturas));
            }
        }
    }

    private void initializeMantenimientos(
            TableView<Mantenimiento> tablaMantenimientos,
            TableColumn<Mantenimiento, String> colIdMantenimiento,
            TableColumn<Mantenimiento, String> colIdMecanico,
            TableColumn<Mantenimiento, String> colIdBicicleta,
            TableColumn<Mantenimiento, String> colFecha,
            TableColumn<Mantenimiento, String> colHoras,
            TableColumn<Mantenimiento, String> colObservaciones,
            Label lblTotalMantenimientos) {

        if (tablaMantenimientos == null) return;

        tablaMantenimientos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        if (colIdMantenimiento != null)
            colIdMantenimiento.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId_mantenimiento())));

        if (colIdMecanico != null)
            colIdMecanico.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId_mecanico()));

        if (colIdBicicleta != null)
            colIdBicicleta.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId_bicicleta()));

        if (colFecha != null)
            colFecha.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFecha().toString()));

        if (colHoras != null)
            colHoras.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getHoras_trabajadas())));

        if (colObservaciones != null)
            colObservaciones.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getObservaciones()));

        var mantenimientos = mantenimientoService.cargarMantenimientos();
        tablaMantenimientos.setItems(mantenimientos);

        if (lblTotalMantenimientos != null) {
            lblTotalMantenimientos.setText("Total: " + (mantenimientos != null ? mantenimientos.size() : 0) + " mantenimientos");
        }
    }
}
