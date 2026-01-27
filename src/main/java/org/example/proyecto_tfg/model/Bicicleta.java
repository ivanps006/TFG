package org.example.proyecto_tfg.model;


import jakarta.persistence.*;

@Entity
@Table (name = "bicicleta")
public class Bicicleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ref")
    private String id_referencia;
    @Column(name = "marca")
    private String marca;
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "frenos")
    private String frenos;
    @Column(name = "susp_delantera")
    private String susp_delantera;
    @Column(name = "susp_trasera")
    private String susp_trasera;
    @Column(name = "transmision")
    private String transmision;
    @Column(name = "ruedas")
    private String ruedas;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "dni", nullable = true)
    private Cliente id_cliente;


    public Bicicleta() {
    }

    public Bicicleta(String id_referencia, String marca, String modelo, String frenos, String susp_delantera, String suspe_trasera, String transmision, String ruedas, Cliente id_cliente) {
        this.id_referencia = id_referencia;
        this.marca = marca;
        this.modelo = modelo;
        this.frenos = frenos;
        this.susp_delantera = susp_delantera;
        this.susp_trasera = suspe_trasera;
        this.transmision = transmision;
        this.ruedas = ruedas;
        this.id_cliente = id_cliente;
    }

    public String getId_referencia() {
        return id_referencia;
    }

    public void setId_referencia(String id_referencia) {
        this.id_referencia = id_referencia;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getRuedas() {
        return ruedas;
    }

    public void setRuedas(String ruedas) {
        this.ruedas = ruedas;
    }
    public String getSuspension_delantera() {
        return susp_delantera;
    }

    public void setSuspension_delantera(String suspension_delantera) {
        this.susp_delantera = suspension_delantera;
    }

    public String getFrenos() {
        return frenos;
    }

    public void setFrenos(String frenos) {
        this.frenos = frenos;
    }

    public String getSuspension_trasera() {
        return susp_trasera;
    }
    public void setSuspension_trasera(String suspension_trasera) {
        this.susp_trasera = suspension_trasera;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public Cliente getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Cliente id_cliente) {
        this.id_cliente = id_cliente;
    }

    @Override
    public String toString() {
        return "Bicicleta{" +
                "id_referencia=" + id_referencia +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", frenos='" + frenos + '\'' +
                ", susp_delantera='" + susp_delantera + '\'' +
                ", susp_trasera='" + susp_trasera + '\'' +
                ", transmision='" + transmision + '\'' +
                ", ruedas='" + ruedas + '\'' +
                ", id_cliente=" + id_cliente +
                '}';
    }
}
