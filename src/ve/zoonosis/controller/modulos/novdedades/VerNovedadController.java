/*
 * Copyright 2016 Gustavo.
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
package ve.zoonosis.controller.modulos.novdedades;

import com.megagroup.Application;
import com.megagroup.componentes.MDialog;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import ve.zoonosis.model.entidades.proceso.Novedades;
import ve.zoonosis.utils.PDFCreator;
import ve.zoonosis.vistas.modulos.novedades.VerNovedad;

/**
 *
 * @author Gustavo
 */
public class VerNovedadController extends VerNovedad<Novedades> {

    private MDialog dialog;
    private JFileChooser archivo;
    private PDFCreator pdfc = new PDFCreator();

    public VerNovedadController(Novedades novedad) {
        super(novedad);
        inicializar();
    }

    @Override
    public JButton getAceptar() {
        return btnImprimir;
    }

    @Override
    public JButton getGuardar() {
        return btnGuardar;
    }

    @Override
    public JButton getCancelar() {
        return btnCancelar;
    }

    @Override
    public boolean validar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void aceptar() {
        try {
            try {
                pdfc.saveDocument("temp.pdf");
            } catch (IOException ex) {
                Logger.getLogger(VerNovedadController.class.getName()).log(Level.SEVERE, null, ex);
            }
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream("temp.pdf");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (inputStream == null) {
                return;
            }

            DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc document = new SimpleDoc(inputStream, docFormat, null);

            PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

            PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

            if (defaultPrintService != null) {
                DocPrintJob printJob = defaultPrintService.createPrintJob();
                try {
                    printJob.print(document, attributeSet);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("No existen impresoras instaladas");
            }

            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(VerNovedadController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void guardar() {
        if (archivo.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                pdfc.saveDocument(archivo.getSelectedFile().getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(VerNovedadController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void cancelar() {
        dialog.close();
    }

    @Override
    public void inicializar() {
        if (entity == null) {
            return;
        }
        archivo = new JFileChooser();
        jLabel1.setText("");
        try {

            pdfc.addLeftText("Nombre y Apellido: " + entity.getCliente().getPersona().getNombre() + " " + entity.getCliente().getPersona().getApellido());
            pdfc.addLeftText("Cedula: " + entity.getCliente().getPersona().getCedula());
            pdfc.addLeftText("Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(entity.getFechaElaboracion()));
            pdfc.addNewLine();
            pdfc.addCenterText(entity.getNombre());
            pdfc.addNewLine();
            pdfc.addLeftText(entity.getDescripcion());
            pdfc.addNewLine();
            pdfc.addNewLine();
            pdfc.addNewLine();
            pdfc.addNewLine();
            pdfc.addNewLine();
//             String[][] s = {{"parroquias", "perros", "gatos","mes","año"},
//            {"Francisco Ochoa", "0", "1"},
//            {"e", "f", "3"},
//            {"g", "h", "4"},
//            {"i", "j", "5"}};
//            pdfc.drawTable(s);
            pdfc.addCenterText("___________________________");
            pdfc.addCenterText(entity.getCliente().getPersona().getNombre() + " " + entity.getCliente().getPersona().getApellido());
           
            ImageIcon ii = new ImageIcon(pdfc.getImagePage(0));
            //Icon icono = new ImageIcon(ii.getImage().getScaledInstance(513, 557, Image.SCALE_DEFAULT));
            jLabel1.setIcon(ii);
            this.repaint();
        } catch (IOException ex) {
            Logger.getLogger(VerNovedadController.class.getName()).log(Level.SEVERE, null, ex);
        }
        iniForm();
        iniciarDialogo();
    }

   

    private void iniciarDialogo() {
        dialog = new MDialog(Application.getAPLICATION_FRAME());
        dialog.setTitle("Ver");
        dialog.setResizable(false);
        dialog.showPanel(this);
    }
}
