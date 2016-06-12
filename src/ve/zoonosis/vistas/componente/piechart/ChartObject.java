/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.zoonosis.vistas.componente.piechart;

import java.awt.Color;

/**
 *
 * @author Xea
 */
public class ChartObject {

    private String etiqueta;
    private Double valor;
    private Color color;

    public ChartObject() {
    }

    public ChartObject(String etiqueta, Double valor, Color color) {
        this.etiqueta = etiqueta;
        this.valor = valor;
        this.color = color;
    }

    public void set(String etiqueta, Double valor, Color color) {
        this.etiqueta = etiqueta;
        this.valor = valor;
        this.color = color;

    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public Double getValor() {
        return valor;
    }

    public Color getColor() {
        return color;
    }

}
