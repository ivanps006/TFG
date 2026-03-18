package org.example.proyecto_tfg.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.proyecto_tfg.model.Bicicleta;
import org.example.proyecto_tfg.model.Cliente;
import org.example.proyecto_tfg.model.Mantenimiento;
import org.example.proyecto_tfg.service.MantenimientoService;
import org.example.proyecto_tfg.service.NavigationService;

public class MostrarInfoBiciController {

    private final MantenimientoService mantenimientoService = new MantenimientoService();

    @FXML private Label lblTitulo;
    @FXML private Label lblNombreBici;
    @FXML private Label lblInfoRef;
    @FXML private Label lblInfoEstado;
    @FXML private Label lblInfoFrenos;
    @FXML private Label lblInfoTransmision;
    @FXML private Label lblInfoSuspDel;
    @FXML private Label lblInfoSuspTras;
    @FXML private Label lblInfoRuedas;
    @FXML private Label lblInfoCliente;

    @FXML private TableView<Mantenimiento> tablaMantenimientosBici;
    @FXML private TableColumn<Mantenimiento, String> colIdMtto;
    @FXML private TableColumn<Mantenimiento, String> colMecanicoMtto;
    @FXML private TableColumn<Mantenimiento, String> colFechaMtto;
    @FXML private TableColumn<Mantenimiento, String> colHorasMtto;
    @FXML private TableColumn<Mantenimiento, String> colObsMtto;
    @FXML private Label lblTotalMtto;

    public void setDatos(Bicicleta bici) {
        // Información básica de la bicicleta
        lblTitulo.setText("🚲 " + bici.getMarca() + " " + bici.getModelo());
        lblNombreBici.setText(bici.getMarca() + " " + bici.getModelo());
        lblInfoRef.setText(safe(bici.getId_referencia()));
        lblInfoFrenos.setText(safe(bici.getFrenos()));
        lblInfoTransmision.setText(safe(bici.getTransmision()));
        lblInfoSuspDel.setText(safe(bici.getSuspension_delantera()));
        lblInfoSuspTras.setText(safe(bici.getSuspension_trasera()));
        lblInfoRuedas.setText(safe(bici.getRuedas()));

        Cliente c = bici.getId_cliente();
        lblInfoCliente.setText(c != null ? c.getDni() : "Sin cliente");

        // Estado con color
        String estado = bici.getEstado() != null ? bici.getEstado() : "Sin reparar";
        lblInfoEstado.setText(estado);
        switch (estado) {
            case "Sin reparar"   -> lblInfoEstado.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
            case "En reparacion" -> lblInfoEstado.setStyle("-fx-text-fill: #f59e0b; -fx-font-weight: bold;");
            case "Reparada"      -> lblInfoEstado.setStyle("-fx-text-fill: #22c55e; -fx-font-weight: bold;");
        }

        // Configurar tabla de mantenimientos
        configurarTablaMantenimientos();
        cargarMantenimientosDeBicicleta(bici.getId_referencia());
    }

    private void configurarTablaMantenimientos() {
        if (tablaMantenimientosBici == null) return;

        tablaMantenimientosBici.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        if (colIdMtto != null)
            colIdMtto.setCellValueFactory(cell ->
                    new SimpleStringProperty(String.valueOf(cell.getValue().getId_mantenimiento())));

        if (colMecanicoMtto != null)
            colMecanicoMtto.setCellValueFactory(cell ->
                    new SimpleStringProperty(cell.getValue().getId_mecanico()));

        if (colFechaMtto != null)
            colFechaMtto.setCellValueFactory(cell ->
                    new SimpleStringProperty(cell.getValue().getFecha().toString()));

        if (colHorasMtto != null)
            colHorasMtto.setCellValueFactory(cell ->
                    new SimpleStringProperty(String.format("%.2f h", cell.getValue().getHoras_trabajadas())));

        if (colObsMtto != null)
            colObsMtto.setCellValueFactory(cell ->
                    new SimpleStringProperty(cell.getValue().getObservaciones() != null
                            ? cell.getValue().getObservaciones() : "—"));
    }

    private void cargarMantenimientosDeBicicleta(String idBicicleta) {
        var mantenimientos = mantenimientoService.cargarMantenimientosPorBicicleta(idBicicleta);

        if (tablaMantenimientosBici != null) {
            tablaMantenimientosBici.setItems(mantenimientos);
        }

        if (lblTotalMtto != null) {
            lblTotalMtto.setText("Total: " + (mantenimientos != null ? mantenimientos.size() : 0) + " mantenimientos");
        }
    }

    private String safe(String v) {
        return (v != null && !v.isEmpty()) ? v : "—";
    }

    @FXML
    private void volver() {
        NavigationService.getInstance().goBack();
    }
}
