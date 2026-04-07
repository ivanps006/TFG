package org.example.proyecto_tfg.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
@Table(name = "mecanico")
public class Mecanico {

    @Id
    @Column(name = "dni", length = 50)
    private String dni;

    @Column(name = "nombre", length = 150)
    private String nombre;

    @Column(name = "apellidos", length = 255)
    private String apellidos;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "usuario", length = 100)
    private String usuario;

    @Column(name = "contrasena", length = 255)
    private String contrasena;

    @Column(name = "telefono", length = 50)
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    // Constructor vacío
    public Mecanico() {
    }

    // Constructor completo
    public Mecanico(String dni, String nombre, String apellidos, String email, String usuario, String contrasena, String telefono, String direccion) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.direccion = direccion;
    }


    // Getters y Setters
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Mantener getCorreo() para compatibilidad
    public String getCorreo() {
        return email;
    }

    public void setCorreo(String correo) {
        this.email = correo;
    }

    @Override
    public String toString() {
        return "Mecanico{" +
                "contrasena='" + contrasena + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", usuario='" + usuario + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
