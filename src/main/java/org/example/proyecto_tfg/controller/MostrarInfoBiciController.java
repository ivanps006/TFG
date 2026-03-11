package org.example.proyecto_tfg.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.proyecto_tfg.model.Bicicleta;
import org.example.proyecto_tfg.model.Cliente;
import org.example.proyecto_tfg.service.NavigationService;

public class MostrarInfoBiciController {

    @FXML
    private Label lblTitulo;
    @FXML private Label lblNombreBici;
    @FXML private Label lblInfoRef;
    @FXML private Label lblInfoEstado;
    @FXML private Label lblInfoFrenos;
    @FXML private Label lblInfoTransmision;
    @FXML private Label lblInfoSuspDel;
    @FXML private Label lblInfoSuspTras;
    @FXML private Label lblInfoRuedas;
    @FXML private Label lblInfoCliente;
    @FXML private Label lblFotoInfo;
    @FXML private ImageView imgBici;

    public void setDatos(Bicicleta bici) {
        lblTitulo.setText("🚲  " + bici.getMarca() + " " + bici.getModelo());
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

        // Foto desde internet en background
        String url = "https://loremflickr.com/500/350/bicycle,"
                + bici.getMarca().replace(" ", ",") + ","
                + bici.getModelo().replace(" ", ",");
        cargarImagen(url);
    }

    private void cargarImagen(String url) {
        lblFotoInfo.setText("⏳ Cargando imagen...");
        Image imagen = new Image(url, 500, 350, true, true, true); // true = background thread

        imagen.progressProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() >= 1.0) {
                Platform.runLater(() -> {
                    if (!imagen.isError()) {
                        imgBici.setImage(imagen);
                        lblFotoInfo.setText("📷 Foto referencial (internet)");
                    } else {
                        lblFotoInfo.setText("⚠ Sin conexión o imagen no disponible");
                    }
                });
            }
        });

        imagen.errorProperty().addListener((obs, oldVal, isError) -> {
            if (isError) Platform.runLater(() ->
                    lblFotoInfo.setText("⚠ Sin conexión o imagen no disponible"));
        });
    }

    private String safe(String v) {
        return (v != null && !v.isEmpty()) ? v : "—";
    }

    @FXML
    private void volver() {
        NavigationService.getInstance().goBack();
    }
}
