/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.zoonosis.vistas.componente.piechart;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.text.StyleConstants;

/**
 *
 * @author Kelvin López
 */
public class PieChartPanel extends javax.swing.JPanel {

    private List<ChartObject> listaChartObject;
    private int maxLeft = 0;
    private int maxRigth = 0;

    /**
     * Creates new form PieChartPanel
     */
    {
        initComponents();
    }

    public PieChartPanel() {
    }

    public PieChartPanel(List<ChartObject> listaChartObject) {
        this.listaChartObject = listaChartObject;
        cargarPie();

    }

    private void cargarPie() {

        pieChartGraphic.cargarPie(listaChartObject);
        generarLeyenda(100);
    }

    private void generarLeyenda(double percent) {
        java.awt.GridBagConstraints gridBagConstraints;
        //  jScrollPane1 = new javax.swing.JScrollPane();
        System.out.println("generar leyenda");
        panelLeyenda = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelLeyenda.setLayout(new java.awt.GridBagLayout());

        GridBagLayout gb = (GridBagLayout) panelLeyenda.getLayout();
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14));
        jLabel1.setText("Leyenda");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(-200, 0, 20, 0);
        panelLeyenda.add(jLabel1, gridBagConstraints);

        jScrollPane1.setViewportView(panelLeyenda);

        int position = 1;
        int pos = -300;
        for (int i = 0; i < listaChartObject.size(); i += 2) {
            System.out.println("entre");
            JLabel l = createLabelLeyend(listaChartObject.get(i).getEtiqueta(), listaChartObject.get(i).getColor(), listaChartObject.get(i).getValor(), percent);
            panelLeyenda.add(l, addGrid(0, position, new java.awt.Insets(pos, 10, 0, 0)));
            if (l.getPreferredSize().width > maxLeft) {
                maxLeft = l.getPreferredSize().width;
            }
            if (i + 1 < listaChartObject.size()) {
                JLabel l2 = createLabelLeyend(listaChartObject.get(i + 1).getEtiqueta(), listaChartObject.get(i + 1).getColor(), listaChartObject.get(i + 1).getValor(), percent);
                panelLeyenda.add(l2, addGrid(1, position, new java.awt.Insets(pos, 0, 0, 10)));
                if (l2.getPreferredSize().width > maxRigth) {
                    maxRigth = l2.getPreferredSize().width;
                }
            }
            position++;
            pos = pos + 50;
        }

        for (Component component : panelLeyenda.getComponents()) {

            GridBagConstraints constraints = gb.getConstraints(component);
            switch (constraints.gridx) {
                case 0:
                    if (component.getPreferredSize().width <= maxLeft && constraints.gridwidth < 2) {
                        int padding = maxLeft - component.getPreferredSize().width;
                        constraints.ipadx = padding;
                        Insets s = constraints.insets;
                        s.right = maxLeft / 2;
                        constraints.insets = s;
                        gb.setConstraints(component, constraints);
                    }
                    break;
                case 1:
                    if (component.getPreferredSize().width <= maxRigth && constraints.gridwidth < 2) {
                        int padding = maxRigth - component.getPreferredSize().width;
                        constraints.ipadx = padding;
                        Insets s = constraints.insets;
                        s.left = maxRigth / 2;
                        constraints.insets = s;
                        gb.setConstraints(component, constraints);
                    }
                    break;
            }
        }
    }

    private JLabel createLabelLeyend(String textLabel, Color label, Double valor, double percent) {
        DecimalFormat decimales = new DecimalFormat("0.00");
        JLabel l = new JLabel("<html>" + textLabel + " (" + valor.intValue() + ") <b>" + decimales.format(((valor) * 100) / percent) + "%</b></html>");
        l.setFont(new Font("Calibri", Font.PLAIN, 14));
        FontMetrics fm = l.getFontMetrics(l.getFont());
        BufferedImage bf = new BufferedImage(fm.getHeight(), fm.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D bGr = bf.createGraphics();
        bGr.setColor(label);
        bGr.fillRect(0, 0, bf.getWidth(), bf.getHeight());
        bGr.dispose();

        l.setIcon(new ImageIcon(bf));
        return l;
    }

    private GridBagConstraints addGrid(int positionX, int positionY, Insets insets) {
        GridBagConstraints gb = new java.awt.GridBagConstraints();
        gb.gridx = positionX;
        gb.gridy = positionY;
        gb.ipadx = 50;
        gb.ipady = 10;
        gb.insets = insets;
        gb.anchor = GridBagConstraints.EAST;
        return gb;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        panelLeyenda = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pieChartGraphic = new ve.zoonosis.vistas.componente.piechart.PieChart();

        panelLeyenda.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Leyenda");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(-200, 0, 20, 0);
        panelLeyenda.add(jLabel1, gridBagConstraints);

        jScrollPane1.setViewportView(panelLeyenda);

        javax.swing.GroupLayout pieChartGraphicLayout = new javax.swing.GroupLayout(pieChartGraphic);
        pieChartGraphic.setLayout(pieChartGraphicLayout);
        pieChartGraphicLayout.setHorizontalGroup(
            pieChartGraphicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 469, Short.MAX_VALUE)
        );
        pieChartGraphicLayout.setVerticalGroup(
            pieChartGraphicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(pieChartGraphic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pieChartGraphic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public void cargarPie(List<ChartObject> chartObject) {
        listaChartObject = chartObject;
        pieChartGraphic.cargarPie(chartObject);
        generarLeyenda(100);
        jScrollPane1.repaint();
        panelLeyenda.repaint();
        panelLeyenda.updateUI();
    }

    public void cargarPie(int percent, List<ChartObject> chartObject) {
        listaChartObject = chartObject;
        pieChartGraphic.cargarPie(percent, chartObject);
        generarLeyenda(percent);
        jScrollPane1.repaint();
        panelLeyenda.repaint();
        panelLeyenda.updateUI();
    }

    public Double[] getValues() {
        return pieChartGraphic.getValues();
    }

    public Color[] getColors() {
        return pieChartGraphic.getColors();
    }

    public Double[] getGradingValues() {
        return pieChartGraphic.getGradingValues();
    }

    public Color[] getGradingColors() {
        return pieChartGraphic.getGradingColors();
    }

    public double getPercent() {
        return pieChartGraphic.getPercent();
    }

    public PieChart.Type getType() {
        return pieChartGraphic.getType();
    }

    public void setType(PieChart.Type type) {
        pieChartGraphic.setType(type);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelLeyenda;
    private ve.zoonosis.vistas.componente.piechart.PieChart pieChartGraphic;
    // End of variables declaration//GEN-END:variables
}
