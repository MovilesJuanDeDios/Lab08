package com.example.casca.productos.Model;

/**
 * Created by casca on 07/05/2018.
 */

public class Producto {

    public Producto(String codigo, String nombreProducto, double precio, int importado, String nombreTipo) {
        this.codigo = codigo;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.importado = importado;
        this.nombreTipo = nombreTipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public int getImportado() {
        return importado;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setImportado(int importado) {
        this.importado = importado;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    @Override
    public String toString() {
        return "Productos{" +
                "codigo='" + codigo + '\'' +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", precio=" + precio +
                ", importado=" + importado +
                ", nombreTipo='" + nombreTipo + '\'' +
                '}';
    }

    String codigo;
    String nombreProducto;
    double precio;
    int importado;
    String nombreTipo;

}
