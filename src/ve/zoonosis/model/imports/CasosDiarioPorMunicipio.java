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
package ve.zoonosis.model.imports;

import com.megagroup.componentes.MGrowl;
import com.megagroup.model.enums.MGrowlState;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.administracion.Parroquia;
import ve.zoonosis.model.entidades.calendario.Semana;
import ve.zoonosis.model.entidades.proceso.Caso;
import ve.zoonosis.model.entidades.proceso.Vacunacion;
import windows.RequestBuilder;
import windows.webservices.utilidades.MetodosDeEnvio;

/**
 *
 * @author angel.colina
 */
public class CasosDiarioPorMunicipio implements Runnable {

    private final String[] months = new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo",
        "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    private final Municipio municipio;
    private final File archivo;
    private final HSSFWorkbook workbook;
    private final int year;
    private List<HashMap> listaMeses;
    //map 1 semana, map 2 parroquia
    private HashMap<String, HashMap<String, Caso>> casos;

    public CasosDiarioPorMunicipio(File archivo, Municipio municipio, int year) throws IOException {
        this.archivo = archivo;
        this.municipio = municipio;
        this.year = year;
        workbook = new HSSFWorkbook(new FileInputStream(archivo));
        casos = new HashMap<>();
    }

    public void iniciarLectura() {
        new Thread(this).start();
    }

    private <T> T obtenrValor(Cell c) {
        Object value;
        switch (c.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = c.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                value = ((Double) c.getNumericCellValue()).intValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = c.getBooleanCellValue();
                break;
            default:
                value = null;
        }
        return (T) value;
    }

    @Override
    public void run() {
        /*
	
         * Obtenemos la primera pestaña a la que se quiera procesar indicando el indice.
         * Una vez obtenida la hoja excel con las filas que se quieren leer obtenemos el iterator	
         * que nos permite recorrer cada una de las filas que contiene.	
         */
        HSSFSheet sheet = workbook.getSheetAt(0);

        for (int i = 2; i < sheet.getPhysicalNumberOfRows() - 6; i++) {
            Row r = sheet.getRow(i);
            for (int j = 1; j < r.getPhysicalNumberOfCells(); j++) {

                Cell c = r.getCell(j);

                if (c == null) {
                    continue;
                }

                cargarObjects(sheet, i, j, obtenrValor(c));

            }
        }
        finalizar();
    }

    private void cargarObjects(HSSFSheet sheet, int row, int column, Object value) {

        if (value != null) {

            int monthIndex = Arrays.binarySearch(months, sheet.getSheetName(), new Comparator<String>() {

                @Override
                public int compare(String o1, String o2) {
                    return o1.compareToIgnoreCase(o2);
                }
            });
            if (monthIndex == -1) {
                return;
            }
            int dia = getCellHeaderValue(sheet, 0, column);
            monthIndex++;

            String fecha = dia + "/" + monthIndex;
            HashMap<String, Caso> m = casos.get(fecha);
            if (m == null) {
                m = new HashMap<>();
            }
            String parroquia = obtenrValor(sheet.getRow(row).getCell(0));

            Caso c = m.get(parroquia);
            if (c == null) {
                c = new Caso(new Date(fecha + "/" + year),
                        new Semana((String) getCellHeaderValue(sheet, sheet.getPhysicalNumberOfRows() - 1, column), year));
                c.setParroquia(new Parroquia(parroquia, municipio));
//                v.getRegistroVacunacion().add(new RegistroVacunacion(v, LoginController.getUsuario()));

            }
//            RegistroVacunacion rv = v.getRegistroVacunacion().get(0);
//            rv.getVacunacion_has_Animal().add(new RegistroVacunacion_has_Animal(rv,
//                    new Animal((String) obtenrValor(sheet.getRow(1).getCell(column))),
//                    (Integer) value));
//            m.put(parroquia, v);
            casos.put(fecha, m);
        }

    }

    private void finalizar() {
        try {
            //        for (String keySet : vacunacion.keySet()) {
//            System.out.println("fecha :" + keySet);
//            HashMap<String, Vacunacion> value = vacunacion.get(keySet);
//            for (String key : value.keySet()) {
//                System.out.println("parroquia : " + key);
//                System.out.println(value.get(key).getRegistroVacunacion().get(0).getVacunacion_has_Animal());
//            }
//        }
            List<Vacunacion> lis = new ArrayList<>();
            for (String keySet : casos.keySet()) {
//                HashMap<String, Vacunacion> value = casos.get(keySet);
//                for (String key : value.keySet()) {
//                    lis.add(value.get(key));
//                }
            }

            RequestBuilder builder = new RequestBuilder("/services/proceso/VacunacionWs/Importacion.php");
            List<Vacunacion> v = builder.crearJson(lis)
                    .setMetodo(MetodosDeEnvio.POST)
                    .ejecutarJson(List.class, Vacunacion.class);
            if (v != null && !v.isEmpty()) {
                MGrowl.showGrowl(MGrowlState.SUCCESS, "Importación realizada con exito");
            } else {
                MGrowl.showGrowl(MGrowlState.SUCCESS, "Ha ocurrido un error durante la importación");
            }
        } catch (URISyntaxException | RuntimeException ex) {
            Logger.getLogger(CasosDiarioPorMunicipio.class.getName()).log(Level.SEVERE, null, ex);
            MGrowl.showGrowl(MGrowlState.SUCCESS, "Ha ocurrido un error durante la importación");
        }
    }

    private <T> T getCellHeaderValue(HSSFSheet sheet, int row, int column) {
        Row r = sheet.getRow(row);
        Cell c = r.getCell(column);
        if (c == null) {
            return null;
        }
        Object value = obtenrValor(c);

        return (T) (value == null ? getCellHeaderValue(sheet, row, column - 1) : value);

    }
}
