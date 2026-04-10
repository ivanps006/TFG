package org.example.proyecto_tfg.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.example.proyecto_tfg.model.Factura;
import org.example.proyecto_tfg.model.Cliente;
import org.example.proyecto_tfg.model.Bicicleta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;

public class ExportarService {

    // Ruta dentro de src/main/resources
    private static final String FACTURA_TEMPLATE_PATH = "/templates/factura.html";

    /**
     * Genera un PDF de la factura y lo guarda en la ruta especificada
     * @param factura La factura a exportar
     * @param rutaDestino La ruta donde se guardará el PDF (ej: "C:/descargas/factura_1.pdf")
     * @return true si se genera correctamente, false en caso contrario
     */
    public boolean generarPdfFactura(Factura factura, String rutaDestino) {
        try {
            String html = generarHtmlFactura(factura);

            // Crear directorio si no existe (comprobar null por si no hay directorio padre)
            File archivo = new File(rutaDestino);
            File parent = archivo.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }

            // Convertir HTML a PDF usando OpenHTMLtoPDF
            try (OutputStream os = new FileOutputStream(archivo)) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.useFastMode(); // opcional
                builder.withHtmlContent(html, null); // baseURL = null
                builder.toStream(os);
                builder.run();

                System.out.println("PDF generado exitosamente: " + rutaDestino);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al generar PDF: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lee el fichero de plantilla HTML de recursos y lo devuelve como String
     */
    private String cargarTemplateFactura() throws Exception {
        try (InputStream is = getClass().getResourceAsStream(FACTURA_TEMPLATE_PATH)) {
            if (is == null) {
                throw new IllegalStateException("No se encontró la plantilla: " + FACTURA_TEMPLATE_PATH);
            }
            byte[] bytes = is.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }

    /**
     * Genera el HTML de la factura a partir de la plantilla y los datos
     */
    private String generarHtmlFactura(Factura factura) throws Exception {
        String template = cargarTemplateFactura();

        Cliente cliente = factura.getId_cliente();
        Bicicleta bicicleta = factura.getId_bicicleta();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fecha = (factura.getFecha() != null)
                ? factura.getFecha().format(formatter)
                : "N/A";

        String clienteNombre = (cliente != null)
                ? cliente.getNombre() + " " + cliente.getApellidos()
                : "N/A";

        String clienteDni = (cliente != null) ? cliente.getDni() : "N/A";
        String clienteTelefono = (cliente != null) ? cliente.getTelefono() : "N/A";
        String clienteDireccion = (cliente != null) ? cliente.getDireccion() : "N/A";

        String biciRef = (bicicleta != null) ? bicicleta.getId_referencia() : "N/A";
        String biciMarca = (bicicleta != null) ? bicicleta.getMarca() : "N/A";
        String biciModelo = (bicicleta != null) ? bicicleta.getModelo() : "N/A";

        BigDecimal totalBD = factura.getTotal() != null ? factura.getTotal() : BigDecimal.ZERO;
        String total = String.format("%.2f", totalBD);

        // IVA y total con IVA (opcional; ajusta si quieres otra lógica)
        BigDecimal ivaBD = totalBD.multiply(BigDecimal.valueOf(0.21));
        BigDecimal totalConIvaBD = totalBD.add(ivaBD);

        String iva = String.format("%.2f", ivaBD);
        String totalConIva = String.format("%.2f", totalConIvaBD);

        // Reemplazar placeholders en la plantilla
        return template
                .replace("{{FACTURA_ID}}", String.valueOf(factura.getId_factura()))
                .replace("{{FECHA}}", fecha)
                .replace("{{CLIENTE_NOMBRE}}", clienteNombre)
                .replace("{{CLIENTE_DNI}}", clienteDni)
                .replace("{{CLIENTE_TELEFONO}}", clienteTelefono)
                .replace("{{CLIENTE_DIRECCION}}", clienteDireccion)
                .replace("{{BICI_REF}}", biciRef)
                .replace("{{BICI_MARCA}}", biciMarca)
                .replace("{{BICI_MODELO}}", biciModelo)
                .replace("{{TOTAL}}", total)
                .replace("{{IVA}}", iva)
                .replace("{{TOTAL_CON_IVA}}", totalConIva);
    }
}
