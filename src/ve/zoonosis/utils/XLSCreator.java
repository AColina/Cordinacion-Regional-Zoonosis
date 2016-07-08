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
package ve.zoonosis.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ve.zoonosis.model.entidades.funcionales.Especie;
import ve.zoonosis.model.exports.ExportacionJornada;
import windows.RequestBuilder;

/**
 *
 * @author Gustavo
 */
public class XLSCreator {

    private XSSFWorkbook wb = new XSSFWorkbook();
    private XSSFSheet sheet;
    private int rowIndex = 0;

    public void addHoja(String name) {
        sheet = wb.createSheet(name);
        rowIndex = 0;
    }

    public void addRow(Object... objs) {
        if (objs instanceof CeldaCustomizer[]) {
            addRow((CeldaCustomizer[]) objs);
        }
        CeldaCustomizer[] cc = new CeldaCustomizer[objs.length];
        for (int i = 0; i < cc.length; i++) {
            cc[i] = new CeldaCustomizer(objs[i]);
        }
        addRow(cc);
    }

    public void addRow(CeldaCustomizer... objs) {
//        int r, w;
//        final int n = r = w = objs.length;
//        while (r > 0) {
//            final CeldaCustomizer s = objs[--r];
//            if (s != null) {
//                objs[--w] = s;
//            }
//        }
//        objs = Arrays.copyOfRange(objs, w, n);
        for (int i = 0; i < objs.length; i++) {
            if (objs[i] == null) {
                objs[i] = new CeldaCustomizer("");
            }
            int inic = i;
            int fin = i;
            if (i > 1) {
                if (objs[i - 1].getColumn() != 1) {
                    inic = i * objs[i - 1].getColumn() - 1;
                }
            }
            if (objs[i].getColumn() != 1) {
                CeldaCustomizer cc = objs[i];
                fin = inic + cc.getColumn() - 1;
            }
            if (inic < fin) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(rowIndex, rowIndex, (inic), (fin));
                sheet.addMergedRegion(cellRangeAddress);
            }
        }
        Row row = sheet.createRow(rowIndex++);
        if (rowIndex == 1) {
            sheet.setColumnWidth(0, 10000);
        }
        for (int i = 0; i < objs.length; i++) {

            int inic = i;

            if (i > 1) {
                if (objs[i - 1].getColumn() != 1) {
                    inic = i * objs[i - 1].getColumn() - 1;
                }
            }
            XSSFFont font = wb.createFont();
            XSSFCellStyle style = wb.createCellStyle();
            font.setBold(objs[i].isBold());
            style.setFont(font);
            Object obj = objs[i].getObj();
            Cell cell = row.createCell(inic);
            cell.setCellStyle(style);
            if (obj instanceof String) {
                cell.setCellValue((String) obj);
            } else if (obj instanceof Boolean) {
                cell.setCellValue((Boolean) obj);
            } else if (obj instanceof Date) {
                cell.setCellValue((Date) obj);
            } else if (obj instanceof Double) {
                cell.setCellValue((Double) obj);
            } else if (obj instanceof Integer) {
                cell.setCellValue(Double.valueOf("" + (int) obj));
            }

        }
    }

// open an OutputStream to save written data into XLSX file
    public void save(String path) throws FileNotFoundException, IOException {
        FileOutputStream os = new FileOutputStream(path);
        wb.write(os);
        System.out.println("Writing on XLSX file Finished ...");
    }

    public static void main(String[] args) throws IOException {
//        XLSCreator c = new XLSCreator();
        XLSCreator xlsc = new XLSCreator();
        RequestBuilder rb = null;
        try {
            rb = new RequestBuilder("services/funcionales/EspecieWS/ListaEspecies.php");
        } catch (URISyntaxException ex) {
            Logger.getLogger(ExportacionJornada.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Especie> valores = rb.ejecutarJson(List.class, Especie.class);
        System.out.println(valores.size());
        System.out.println(valores.get(0));
        for (int i = 0; i < 12; i++) {
            xlsc.addHoja("Enero " + i);
            CeldaCustomizer[] ccc = new CeldaCustomizer[32];
            ccc[0] = new CeldaCustomizer("Vacunacion del Mes de Enero " + i);
            for (int j = 1; j < ccc.length; j++) {
                ccc[j] = new CeldaCustomizer("" + j, valores.size());
            }
            xlsc.addRow(ccc);
            ccc = new CeldaCustomizer[ccc.length * valores.size()];
            ccc[0] = new CeldaCustomizer("Municipio/parroquias");
            for (int j = 1; j < ccc.length; j++) {
                ccc[j] = new CeldaCustomizer(valores.get(j % valores.size()).getNombre());
//                if (j % 2 == 0) {
//                    ccc[j] = new CeldaCustomizer("canino");
//                } else {
//                    ccc[j] = new CeldaCustomizer("felino");
//                }
            }

//            int dias = diasDelMes(i, year);
            xlsc.addRow(ccc);
//            for (final Parroquia p : municipio.getParroquiasAsociadas()) {
//                xlsc.addRow(p.getNombre());
//                for (int j = 1; j <= dias; j++) {
////                    try {
////
////                        final Date d = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + (i + 1) + "-" + j);
////                        rb = new RequestBuilder("services/funcionales/AnimalWs/ExportarJornada.php", new HashMap() {
////                            {
////                                put("dia", d);
////                                put("nombreMunicipio", municipio.getNombre());
////                                put("nombreParroquia", p.getNombre());
////                            }
////                        });
////                    } catch (URISyntaxException ex) {
////                        Logger.getLogger(ExportacionJornada.class.getName()).log(Level.SEVERE, null, ex);
////                    } catch (ParseException ex) {
////                        Logger.getLogger(ExportacionJornada.class.getName()).log(Level.SEVERE, null, ex);
////                    }
////                    List<HashMap> valores = rb.ejecutarJson(List.class, HashMap.class);
////                    for (HashMap valore : valores) {
////                        System.out.println(valore.get("0"));
////                    }
//                }
//            }
//        }
        }
        try {
            xlsc.save("C:\\Users\\Gustavo\\Documents\\NetBeansProjects\\prueba2.xlsx");
        } catch (IOException ex) {
            Logger.getLogger(ExportacionJornada.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
