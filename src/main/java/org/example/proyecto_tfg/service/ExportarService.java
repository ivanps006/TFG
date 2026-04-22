package org.example.proyecto_tfg.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.example.proyecto_tfg.model.Factura;
import org.example.proyecto_tfg.model.Cliente;
import org.example.proyecto_tfg.model.Bicicleta;
import org.example.proyecto_tfg.model.Mantenimiento;
import org.example.proyecto_tfg.utils.Utils;
import jakarta.persistence.EntityManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.util.List;

public class ExportarService {

    private static final String FACTURA_TEMPLATE_PATH = "/templates/factura.html";
    private static final BigDecimal PRECIO_HORA = new BigDecimal("5.00");

    public boolean generarPdfFactura(Factura factura, String rutaDestino) {
        try {
            String html = generarHtmlFactura(factura);

            File archivo = new File(rutaDestino);
            File parent = archivo.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }

            try (OutputStream os = new FileOutputStream(archivo)) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.useFastMode();
                builder.withHtmlContent(html, null);
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

    private String cargarTemplateFactura() throws Exception {
        try (InputStream is = getClass().getResourceAsStream(FACTURA_TEMPLATE_PATH)) {
            if (is == null) {
                throw new IllegalStateException("No se encontró la plantilla: " + FACTURA_TEMPLATE_PATH);
            }
            byte[] bytes = is.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }

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

        // Obtener mantenimientos de la bicicleta
        String filasMantenimientos = "";
        double totalHoras = 0;

        if (bicicleta != null) {
            List<Mantenimiento> mantenimientos = obtenerMantenimientosDeBicicleta(bicicleta.getId_referencia());

            for (Mantenimiento m : mantenimientos) {
                double horas = m.getHoras_trabajadas();
                totalHoras += horas;
                String fecha_mant = m.getFecha() != null ? m.getFecha().format(formatter) : "N/A";
                filasMantenimientos += String.format(
                        "<tr><td>Mantenimiento</td><td>%s - %s horas</td><td>%.2f EUR</td></tr>",
                        fecha_mant,
                        horas,
                        horas * 5.0
                );
            }
        }

        // Mano de obra = horas * 5€
        BigDecimal manoObraTotal = BigDecimal.valueOf(totalHoras).multiply(PRECIO_HORA);

// Precio inicial = lo que ya tiene la factura guardado en BD
        BigDecimal precioInicial = (factura.getTotal() != null) ? factura.getTotal() : BigDecimal.ZERO;

// Subtotal = precio inicial + mano de obra
        BigDecimal subtotal = precioInicial.add(manoObraTotal);

// IVA y total final
        BigDecimal ivaBD = subtotal.multiply(BigDecimal.valueOf(0.21));
        BigDecimal totalConIvaBD = subtotal.add(ivaBD);

// Strings formateados
        String precioInicialStr = String.format("%.2f", precioInicial);
        String manoObraStr = String.format("%.2f", manoObraTotal);
        String subtotalStr = String.format("%.2f", subtotal);
        String ivaStr = String.format("%.2f", ivaBD);
        String totalConIvaStr = String.format("%.2f", totalConIvaBD);


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
                .replace("{{FILAS_MANTENIMIENTOS}}", filasMantenimientos)
                .replace("{{TOTAL_HORAS}}", String.format("%.2f", totalHoras))
                .replace("{{MANO_OBRA}}", manoObraStr)
                .replace("{{PRECIO_INICIAL}}", precioInicialStr)
                .replace("{{MANO_OBRA}}", manoObraStr)
                .replace("{{SUBTOTAL}}", subtotalStr)
                .replace("{{IVA}}", ivaStr)
                .replace("{{TOTAL_CON_IVA}}", totalConIvaStr);

    }

    private List<Mantenimiento> obtenerMantenimientosDeBicicleta(String idBicicleta) {
        EntityManager em = Utils.em();
        try {
            return em.createQuery(
                            "SELECT m FROM Mantenimiento m WHERE m.id_bicicleta = :id ORDER BY m.fecha DESC",
                            Mantenimiento.class
                    )
                    .setParameter("id", idBicicleta)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
