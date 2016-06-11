/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.zoonosis.vistas.componente.piechart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JPanel;

public class PieChart
        extends JPanel {

    public static enum Type {
        STANDARD, SIMPLE_INDICATOR, GRADED_INDICATOR;

        private Type() {
        }
    }

    private Type type = null;
    private List<Double> values;
    private List<Color> colors;
    private List<Double> gradingValues;
    private List<Color> gradingColors;
    double percent = 0.0D;

    public PieChart() {
        initComponents();
        this.values = new ArrayList();
        this.colors = new ArrayList();

        this.gradingValues = new ArrayList();
        this.gradingColors = new ArrayList();

        System.out.println("vacio");
        this.type = Type.STANDARD;
    }

    public PieChart(int percent) {
        initComponents();
        this.values = new ArrayList();
        this.colors = new ArrayList();

        this.gradingValues = new ArrayList();
        this.gradingColors = new ArrayList();

        this.type = Type.SIMPLE_INDICATOR;
        this.percent = percent;
    }

    public PieChart(List<ChartObject> chartList) {
        this();
        setValues(chartList);
    }

    private void setValues(List<ChartObject> chartList) {
        List<Color> colores = new ArrayList();
        List<Double> valores = new ArrayList();
        for (ChartObject chartObject : chartList) {
            colores.add(chartObject.getColor());
            valores.add(chartObject.getValor());
        }
        this.values = valores;
        this.colors = colores;
    }

    private void setValues(double percent, List<ChartObject> chartList) {
        List<Color> colores = new ArrayList();
        List<Double> valores = new ArrayList();
        for (ChartObject chartObject : chartList) {
            System.out.println((chartObject.getValor() * 100) / percent);
            colores.add(chartObject.getColor());
            valores.add((chartObject.getValor() * 100) / percent);
        }
        this.values = valores;
        this.colors = colores;
        this.percent = percent;
    }

    public PieChart(int percent, List<ChartObject> chartList) {
        initComponents();
        this.values = new ArrayList();
        this.colors = new ArrayList();

        this.gradingValues = new ArrayList();
        this.gradingColors = new ArrayList();

        this.type = Type.GRADED_INDICATOR;
        setValues(percent, chartList);
    }

    public void cargarPie(List<ChartObject> chartList) {
        System.out.println("hola");
        this.type = Type.STANDARD;
        setValues(chartList);
        repaint();
    }

    public void cargarPie(int percent, List<ChartObject> chartList) {
        this.type = Type.STANDARD;

        setValues(percent, chartList);
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    @SuppressWarnings("UnusedAssignment")
    public void paint(Graphics g) {
        int width = getSize().width;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (this.type == Type.SIMPLE_INDICATOR) {
            Color backgroundColor = Color.WHITE;
            Color mainColor = Color.BLUE;

            g2d.setColor(backgroundColor);
            g2d.fillOval(0, 0, width, width);
            g2d.setColor(mainColor);
            Double angle = (percent / 100) * 360;
            g2d.fillArc(0, 0, width, width, -270, -angle.intValue());

        } else if (this.type == Type.STANDARD) {

            System.out.println("soy estandar");
            int lastPoint = -270;
            Color backgroundColor = Color.WHITE;
            g2d.setColor(backgroundColor);
            g2d.fillOval(0, 0, width, width);
            for (int i = 0; i < this.values.size(); i++) {
                System.out.println("dibujando");
                g2d.setColor(colors.get(i));

                Long lon = Math.round(values.get(i));
                Double val = ((Long)Math.round(lon.doubleValue())).doubleValue();
                Double angle = (val / 100) * 360;
                g2d.fillArc(0, 0, width, width, lastPoint, -angle.intValue());

                lastPoint += -angle.intValue();
            }
        } else if (this.type == Type.GRADED_INDICATOR) {
            int lastPoint = -270;

            double gradingAccum = 0;
            for (int i = 0; i < this.gradingValues.size(); i++) {
                g2d.setColor((Color) this.gradingColors.get(i));
                Double val = (Double) this.gradingValues.get(i);
                gradingAccum += val.doubleValue();
                if (gradingAccum > this.percent) {
                    double gradingAccumMinusOneSegment = gradingAccum - val.doubleValue();

                    Double angle = Double.valueOf((this.percent - gradingAccumMinusOneSegment) / 100 * 360);

                    g2d.fillArc(0, 0, width, width, lastPoint, -angle.intValue());
                    lastPoint += -angle.intValue();
                    break;
                }
                Double angle = Double.valueOf(val.doubleValue() / 100 * 360);

                g2d.fillArc(0, 0, width, width, lastPoint, -angle.intValue());

                lastPoint += -angle.intValue();
            }
        } else {
            System.out.println("no funciona");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    public Double[] getValues() {
        return values.toArray(new Double[values.size()]);
    }

    public Color[] getColors() {
        return colors.toArray(new Color[colors.size()]);
    }

    public Double[] getGradingValues() {
        return gradingValues.toArray(new Double[gradingValues.size()]);
    }

    public Color[] getGradingColors() {
        return gradingColors.toArray(new Color[colors.size()]);
    }

    public double getPercent() {
        return percent;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
