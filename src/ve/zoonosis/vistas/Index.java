/*
 * Copyright 2016 angel.colina.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ve.zoonosis.vistas;

import com.megagroup.Application;
import com.megagroup.utilidades.ComponentUtils;
import ve.zoonosis.controller.seguridad.LoginController;
import ve.zoonosis.model.entidades.administracion.Usuario;
import ve.zoonosis.model.listener.TemplateListeners;
import ve.zoonosis.vistas.seguridad.Login;
import windows.Recursos;

/**
 *
 * @author angel.colina
 */
public class Index extends javax.swing.JFrame {

    private static Template template;

    /**
     * Creates new form Index
     */
    public Index() {
        initComponents();
        addListeners();
        this.setExtendedState(MAXIMIZED_BOTH);
        Recursos.inicializarRecursos();
        this.setContentPane(new LoginController(Index.this));
    }

    private void addListeners() {
        jornadasVacunacion.addActionListener(TemplateListeners.getJornadaVacunacionBandeja());
        cerrarSesion.addActionListener(TemplateListeners.getCerrarSesion());
        casos.addActionListener(TemplateListeners.getCasosBandeja());
        novedades.addActionListener(TemplateListeners.getNovedadesBandeja());
        vacunacionAnimalDiariaPorMunicipio.addActionListener(TemplateListeners.getJornadaAnimalDiarioPorMunicipio());
        vacunacionAnimalSemanalPorMunicipio.addActionListener(TemplateListeners.getJornadaAnimalSemanalPorMunicipio());
        vacunacionAnimalMensualPorMunicipio.addActionListener(TemplateListeners.getJornadaAnimalMensualPorMunicipio());
        vacunacionAnimalDiariaPorParroquia.addActionListener(TemplateListeners.getJornadaAnimalDiarioPorParroquia());
        vacunacionEspecieDiariaPorMunicipio.addActionListener(TemplateListeners.getJornadaEspecieDiarioPorMunicipio());
        vacunacionEspecieDiariaPorParroquia.addActionListener(TemplateListeners.getJornadaEspecieDiarioPorParroquia());
        casosAnimalDiarioPorMunicipio.addActionListener(TemplateListeners.getCasoAnimalDiarioPorMunicipio());
        casosAnimalDiarioPorParroquia.addActionListener(TemplateListeners.getCasoAnimalDiarioPorParroquia());
        casosEspecieDiarioPorMunicipio.addActionListener(TemplateListeners.getCasoEspecieDiarioPorMunicipio());
        casosEspecieDiarioPorParroquia.addActionListener(TemplateListeners.getCasoEspecieDiarioPorParroquia());
        vacunacionAnimalMensualPorParroquia.addActionListener(TemplateListeners.getJornadaAnimalMensualPorParroquia());
        casosAnimalMensualPorMunicipio.addActionListener(TemplateListeners.getCasoAnimalMensualPorMunicipio());
        casosAnimalMensualPorParroquia.addActionListener(TemplateListeners.getCasoAnimalMensualPorParroquia());
        vacunacionEspecieMensualPorMunicipio.addActionListener(TemplateListeners.getJornadaEspecieMensualPorMunicipio());
        vacunacionEspecieMensualPorParroquia.addActionListener(TemplateListeners.getJornadaEspecieMensualPorParroquia());
        casosEspecieMensualPorMunicipio.addActionListener(TemplateListeners.getCasoEspecieMensualPorMunicipio());
        casosEspecieMensualPorParroquia.addActionListener(TemplateListeners.getCasoEspecieMensualPorParroquia());
        vacunacionAnimalSemanalPorParroquia.addActionListener(TemplateListeners.getJornadaAnimalSemanalPorParroquia());
        casosAnimalSemanalPorMunicipio.addActionListener(TemplateListeners.getCasoAnimalSemanalPorMunicipio());
        casosAnimalSemanalPorParroquia.addActionListener(TemplateListeners.getCasoAnimalSemanalPorParroquia());
        vacunacionEspecieSemanalPorMunicipio.addActionListener(TemplateListeners.getJornadaEspecieSemanalPorMunicipio());
        vacunacionEspecieSemanalPorParroquia.addActionListener(TemplateListeners.getJornadaEspecieSemanalPorParroquia());
        casosEspecieSemanalPorMunicipio.addActionListener(TemplateListeners.getCasoEspecieSemanalPorMunicipio());
        casosEspecieSemanalPorParroquia.addActionListener(TemplateListeners.getCasoEspecieSemanalPorParroquia());
        comparativaAnual.addActionListener(TemplateListeners.getComparativaAnual());

    }

    public final void addListeners(Usuario usuario) {
        String permiso = usuario == null ? "" : usuario.getPermiso().getNombre();
        TemplateListeners listener = new TemplateListeners();
        registarUsuario.setVisible(true);
        modificarDatos.setVisible(true);
        importar.setVisible(true);
        registrarEspecie.setVisible(true);
        switch (permiso) {
            default:
                modificarDatos.setVisible(false);
                importar.setVisible(false);
                exportar.setVisible(false);
            case "Usuario":
                registarUsuario.setVisible(false);
                registrarEspecie.setVisible(false);
            case "Coordinador":
        }
        ComponentUtils.removeListener(registarUsuario, TemplateListeners.CrearDialogo.class);
        ComponentUtils.removeListener(modificarDatos, TemplateListeners.CrearDialogo.class);
        ComponentUtils.removeListener(importar, TemplateListeners.CrearDialogo.class);
        ComponentUtils.removeListener(registrarEspecie, TemplateListeners.CrearDialogo.class);
        registarUsuario.addActionListener(listener.getNuevoUsuario());
        modificarDatos.addActionListener(listener.getDatosDeUsuario());
        importar.addActionListener(listener.getImportar());
        acercaDe.addActionListener(listener.getAcercaDe());
        registrarEspecie.addActionListener(listener.getNuevaEspecie());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JMenuBar menu = new javax.swing.JMenuBar();
        javax.swing.JMenu jMenu5 = new javax.swing.JMenu();
        registarUsuario = new javax.swing.JMenuItem();
        registrarEspecie = new javax.swing.JMenuItem();
        modificarDatos = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator2 = new javax.swing.JPopupMenu.Separator();
        cerrarSesion = new javax.swing.JMenuItem();
        javax.swing.JMenu jMenu1 = new javax.swing.JMenu();
        casos = new javax.swing.JMenuItem();
        jornadasVacunacion = new javax.swing.JMenuItem();
        novedades = new javax.swing.JMenuItem();
        javax.swing.JMenu jMenu2 = new javax.swing.JMenu();
        javax.swing.JMenu jMenu4 = new javax.swing.JMenu();
        casosAnimalDiarioPorMunicipio = new javax.swing.JMenuItem();
        casosAnimalSemanalPorMunicipio = new javax.swing.JMenuItem();
        casosAnimalMensualPorMunicipio = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator3 = new javax.swing.JPopupMenu.Separator();
        casosAnimalDiarioPorParroquia = new javax.swing.JMenuItem();
        casosAnimalSemanalPorParroquia = new javax.swing.JMenuItem();
        casosAnimalMensualPorParroquia = new javax.swing.JMenuItem();
        javax.swing.JMenu jMenu13 = new javax.swing.JMenu();
        casosEspecieDiarioPorMunicipio = new javax.swing.JMenuItem();
        casosEspecieSemanalPorMunicipio = new javax.swing.JMenuItem();
        casosEspecieMensualPorMunicipio = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator8 = new javax.swing.JPopupMenu.Separator();
        casosEspecieDiarioPorParroquia = new javax.swing.JMenuItem();
        casosEspecieSemanalPorParroquia = new javax.swing.JMenuItem();
        casosEspecieMensualPorParroquia = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator1 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenu jMenu7 = new javax.swing.JMenu();
        vacunacionAnimalDiariaPorMunicipio = new javax.swing.JMenuItem();
        vacunacionAnimalSemanalPorMunicipio = new javax.swing.JMenuItem();
        vacunacionAnimalMensualPorMunicipio = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator9 = new javax.swing.JPopupMenu.Separator();
        vacunacionAnimalDiariaPorParroquia = new javax.swing.JMenuItem();
        vacunacionAnimalSemanalPorParroquia = new javax.swing.JMenuItem();
        vacunacionAnimalMensualPorParroquia = new javax.swing.JMenuItem();
        javax.swing.JMenu jMenu11 = new javax.swing.JMenu();
        vacunacionEspecieDiariaPorMunicipio = new javax.swing.JMenuItem();
        vacunacionEspecieSemanalPorMunicipio = new javax.swing.JMenuItem();
        vacunacionEspecieMensualPorMunicipio = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator10 = new javax.swing.JPopupMenu.Separator();
        vacunacionEspecieDiariaPorParroquia = new javax.swing.JMenuItem();
        vacunacionEspecieSemanalPorParroquia = new javax.swing.JMenuItem();
        vacunacionEspecieMensualPorParroquia = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator5 = new javax.swing.JPopupMenu.Separator();
        comparativaAnual = new javax.swing.JMenuItem();
        javax.swing.JMenu jMenu3 = new javax.swing.JMenu();
        javax.swing.JMenu opciones = new javax.swing.JMenu();
        importar = new javax.swing.JMenuItem();
        exportar = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator4 = new javax.swing.JPopupMenu.Separator();
        acercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Zoonosis  System");

        jMenu5.setText("Administrar");

        registarUsuario.setText("Registrar Usuario");
        jMenu5.add(registarUsuario);

        registrarEspecie.setText("Registrar Especies");
        jMenu5.add(registrarEspecie);

        modificarDatos.setText("Datos Personales");
        jMenu5.add(modificarDatos);
        jMenu5.add(jSeparator2);

        cerrarSesion.setText("Cerrar Sesion");
        jMenu5.add(cerrarSesion);

        menu.add(jMenu5);

        jMenu1.setText("Control");

        casos.setText("Casos");
        jMenu1.add(casos);

        jornadasVacunacion.setText("Jornadas de Vacunacion");
        jMenu1.add(jornadasVacunacion);

        novedades.setText("Novedades");
        jMenu1.add(novedades);

        menu.add(jMenu1);

        jMenu2.setText("Estadistica");

        jMenu4.setText("Casos por animales");

        casosAnimalDiarioPorMunicipio.setText("Diario por Municipio");
        jMenu4.add(casosAnimalDiarioPorMunicipio);

        casosAnimalSemanalPorMunicipio.setText("Semanal por Municipio");
        jMenu4.add(casosAnimalSemanalPorMunicipio);

        casosAnimalMensualPorMunicipio.setText("Mensual por Municipio");
        jMenu4.add(casosAnimalMensualPorMunicipio);
        jMenu4.add(jSeparator3);

        casosAnimalDiarioPorParroquia.setText("Diario por Parroquia");
        jMenu4.add(casosAnimalDiarioPorParroquia);

        casosAnimalSemanalPorParroquia.setText("Semanal por Parroquia");
        jMenu4.add(casosAnimalSemanalPorParroquia);

        casosAnimalMensualPorParroquia.setText("Mensual por Parroquia");
        jMenu4.add(casosAnimalMensualPorParroquia);

        jMenu2.add(jMenu4);

        jMenu13.setText("Casos por especies");

        casosEspecieDiarioPorMunicipio.setText("Diario por Municipio");
        jMenu13.add(casosEspecieDiarioPorMunicipio);

        casosEspecieSemanalPorMunicipio.setText("Semanal por Municipio");
        jMenu13.add(casosEspecieSemanalPorMunicipio);

        casosEspecieMensualPorMunicipio.setText("Mensual por Municipio");
        jMenu13.add(casosEspecieMensualPorMunicipio);
        jMenu13.add(jSeparator8);

        casosEspecieDiarioPorParroquia.setText("Diario por Parroquia");
        jMenu13.add(casosEspecieDiarioPorParroquia);

        casosEspecieSemanalPorParroquia.setText("Semanal por Parroquia");
        jMenu13.add(casosEspecieSemanalPorParroquia);

        casosEspecieMensualPorParroquia.setText("Mensual por Parroquia");
        jMenu13.add(casosEspecieMensualPorParroquia);

        jMenu2.add(jMenu13);
        jMenu2.add(jSeparator1);

        jMenu7.setText("Jornada de Vacunación por animales");

        vacunacionAnimalDiariaPorMunicipio.setText("Diario por Municipio");
        jMenu7.add(vacunacionAnimalDiariaPorMunicipio);

        vacunacionAnimalSemanalPorMunicipio.setText("Semanal por Municipio");
        jMenu7.add(vacunacionAnimalSemanalPorMunicipio);

        vacunacionAnimalMensualPorMunicipio.setText("Mensual por Municipio");
        jMenu7.add(vacunacionAnimalMensualPorMunicipio);
        jMenu7.add(jSeparator9);

        vacunacionAnimalDiariaPorParroquia.setText("Diario por Parroquia");
        jMenu7.add(vacunacionAnimalDiariaPorParroquia);

        vacunacionAnimalSemanalPorParroquia.setText("Semanal por Parroquia");
        jMenu7.add(vacunacionAnimalSemanalPorParroquia);

        vacunacionAnimalMensualPorParroquia.setText("Mensual por Parroquia");
        jMenu7.add(vacunacionAnimalMensualPorParroquia);

        jMenu2.add(jMenu7);

        jMenu11.setText("Jornada de Vacunación por especies");

        vacunacionEspecieDiariaPorMunicipio.setText("Diario por Municipio");
        jMenu11.add(vacunacionEspecieDiariaPorMunicipio);

        vacunacionEspecieSemanalPorMunicipio.setText("Semanal por Municipio");
        jMenu11.add(vacunacionEspecieSemanalPorMunicipio);

        vacunacionEspecieMensualPorMunicipio.setText("Mensual por Municipio");
        jMenu11.add(vacunacionEspecieMensualPorMunicipio);
        jMenu11.add(jSeparator10);

        vacunacionEspecieDiariaPorParroquia.setText("Diario por Parroquia");
        jMenu11.add(vacunacionEspecieDiariaPorParroquia);

        vacunacionEspecieSemanalPorParroquia.setText("Semanal por Parroquia");
        jMenu11.add(vacunacionEspecieSemanalPorParroquia);

        vacunacionEspecieMensualPorParroquia.setText("Mensual por Parroquia");
        jMenu11.add(vacunacionEspecieMensualPorParroquia);

        jMenu2.add(jMenu11);
        jMenu2.add(jSeparator5);

        comparativaAnual.setText("Comparativa Anual");
        jMenu2.add(comparativaAnual);

        menu.add(jMenu2);

        jMenu3.setText("Informes");
        menu.add(jMenu3);

        opciones.setText("Opciones");

        importar.setText("Importar Excel");
        opciones.add(importar);

        exportar.setText("Exportar a Excel");
        opciones.add(exportar);
        opciones.add(jSeparator4);

        acercaDe.setText("Acerca de");
        opciones.add(acercaDe);

        menu.add(opciones);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void cambiarPagina(Login login) {
        Index frame = Application.getAPLICATION_FRAME();
        frame.setContentPane(login);
        frame.revalidate();
        frame.repaint();
    }

    public static void cambiarPagina(Template template) {
        Index frame = Application.getAPLICATION_FRAME();
        Index.template = template;
        frame.setContentPane(template);
        frame.revalidate();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Application.run(Index.class, args);
    }

    public static Template getTemplate() {
        return template;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem acercaDe;
    private javax.swing.JMenuItem casos;
    private javax.swing.JMenuItem casosAnimalDiarioPorMunicipio;
    private javax.swing.JMenuItem casosAnimalDiarioPorParroquia;
    private javax.swing.JMenuItem casosAnimalMensualPorMunicipio;
    private javax.swing.JMenuItem casosAnimalMensualPorParroquia;
    private javax.swing.JMenuItem casosAnimalSemanalPorMunicipio;
    private javax.swing.JMenuItem casosAnimalSemanalPorParroquia;
    private javax.swing.JMenuItem casosEspecieDiarioPorMunicipio;
    private javax.swing.JMenuItem casosEspecieDiarioPorParroquia;
    private javax.swing.JMenuItem casosEspecieMensualPorMunicipio;
    private javax.swing.JMenuItem casosEspecieMensualPorParroquia;
    private javax.swing.JMenuItem casosEspecieSemanalPorMunicipio;
    private javax.swing.JMenuItem casosEspecieSemanalPorParroquia;
    private javax.swing.JMenuItem cerrarSesion;
    private javax.swing.JMenuItem comparativaAnual;
    private javax.swing.JMenuItem exportar;
    private javax.swing.JMenuItem importar;
    private javax.swing.JMenuItem jornadasVacunacion;
    private javax.swing.JMenuItem modificarDatos;
    private javax.swing.JMenuItem novedades;
    private javax.swing.JMenuItem registarUsuario;
    private javax.swing.JMenuItem registrarEspecie;
    private javax.swing.JMenuItem vacunacionAnimalDiariaPorMunicipio;
    private javax.swing.JMenuItem vacunacionAnimalDiariaPorParroquia;
    private javax.swing.JMenuItem vacunacionAnimalMensualPorMunicipio;
    private javax.swing.JMenuItem vacunacionAnimalMensualPorParroquia;
    private javax.swing.JMenuItem vacunacionAnimalSemanalPorMunicipio;
    private javax.swing.JMenuItem vacunacionAnimalSemanalPorParroquia;
    private javax.swing.JMenuItem vacunacionEspecieDiariaPorMunicipio;
    private javax.swing.JMenuItem vacunacionEspecieDiariaPorParroquia;
    private javax.swing.JMenuItem vacunacionEspecieMensualPorMunicipio;
    private javax.swing.JMenuItem vacunacionEspecieMensualPorParroquia;
    private javax.swing.JMenuItem vacunacionEspecieSemanalPorMunicipio;
    private javax.swing.JMenuItem vacunacionEspecieSemanalPorParroquia;
    // End of variables declaration//GEN-END:variables
}
