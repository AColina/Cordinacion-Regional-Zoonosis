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
package ve.zoonosis.model.exports;

import com.megagroup.componentes.MGrowl;
import com.megagroup.model.enums.MGrowlState;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.administracion.Parroquia;
import ve.zoonosis.model.entidades.funcionales.Especie;
import ve.zoonosis.utils.CeldaCustomizer;
import ve.zoonosis.utils.XLSCreator;
import windows.RequestBuilder;

/**
 *
 * @author Gustavo
 */
public class ExportacionJornada extends Thread {

    private XLSCreator xlsc = new XLSCreator();
    private RequestBuilder rb;
    private Municipio municipio;
    private HashMap<String, Object[]> parroquias = new HashMap();
    private HashMap<String, Object[]> meses = new HashMap();
    private int year;
    private String path;

    public ExportacionJornada(final Municipio mun, final int year, String path) throws URISyntaxException {
        municipio = mun;
        this.year = year;
        if (path.endsWith(".xlsx")) {
            this.path = path;
        } else {
            this.path = path+".xlsx";
        }
    }

    @Override
    public void run() {
        try {
            rb = new RequestBuilder("services/funcionales/EspecieWS/ListaEspecies.php");
        } catch (URISyntaxException ex) {
            Logger.getLogger(ExportacionJornada.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Especie> valores = rb.ejecutarJson(List.class, Especie.class);
        try {
            rb = new RequestBuilder("services/funcionales/AnimalWs/ExportarJornada.php", new HashMap() {
                {
                    put("nombreMunicipio", municipio.getNombre());
                    put("year", year);
                }
            });
        } catch (URISyntaxException ex) {
            Logger.getLogger(ExportacionJornada.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<HashMap> v = rb.ejecutarJson(List.class, HashMap.class);

        for (int i = 0; i < 12; i++) {
            xlsc.addHoja(nombreDelMes(i));
            int dias = diasDelMes(i, year);
            CeldaCustomizer[] ccc = new CeldaCustomizer[dias + 1];
            ccc[0] = new CeldaCustomizer("Vacunacion del Mes de " + nombreDelMes(i),true);
            for (int j = 1; j < ccc.length; j++) {
                ccc[j] = new CeldaCustomizer("" + j, valores.size());
            }
            xlsc.addRow(ccc);
            ccc = new CeldaCustomizer[ccc.length * valores.size()];
            ccc[0] = new CeldaCustomizer("Municipio/parroquias",true);
            for (int j = 1; j < ccc.length; j++) {
                ccc[j] = new CeldaCustomizer(valores.get(j % valores.size()).getNombre());
            }

            xlsc.addRow(ccc);

            for (final Parroquia p : municipio.getParroquiasAsociadas()) {
                int x = 0;
                ccc = new CeldaCustomizer[ccc.length];
                ccc[x++] = new CeldaCustomizer(p.getNombre());
                for (int j = 1; j <= dias; j++) {
                    for (int k = 0; k < valores.size(); k++) {
                        String mes = ((i + 1) < 10) ? "0" + (i + 1) : "" + (i + 1);
                        String dia = ((j) < 10) ? "0" + (j) : "" + (j);
                        ccc[x++] = new CeldaCustomizer(getColumnValue(p, year + "-" + mes + "-" + dia, valores.get(k), v));
                    }
                }
                xlsc.addRow(ccc);
            }
            int x = 0;
            ccc = new CeldaCustomizer[ccc.length];
            ccc[x++] = new CeldaCustomizer("Total",true);
            for (int j = 1; j <= dias; j++) {
                for (int k = 0; k < valores.size(); k++) {
                    String mes = ((i + 1) < 10) ? "0" + (i + 1) : "" + (i + 1);
                    String dia = ((j) < 10) ? "0" + (j) : "" + (j);
                    ccc[x++] = new CeldaCustomizer(getColumnTotal(year + "-" + mes + "-" + dia, valores.get(k), v),true);
                }
            }
            xlsc.addRow(ccc);
        }
        try {
            xlsc.save(path);
            MGrowl.showGrowl(MGrowlState.SUCCESS, "Exportación finalizada");
        } catch (IOException ex) {
            Logger.getLogger(ExportacionJornada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int diasDelMes(int mes, int año) {

        switch (mes) {
            case 0:  // Enero
            case 2:  // Marzo
            case 4:  // Mayo
            case 6:  // Julio
            case 7:  // Agosto
            case 9:  // Octubre
            case 11: // Diciembre
                return 31;
            case 3:  // Abril
            case 5:  // Junio
            case 8:  // Septiembre
            case 10: // Noviembre
                return 30;
            case 1:  // Febrero
                if (((año % 100 == 0) && (año % 400 == 0))
                        || ((año % 100 != 0) && (año % 4 == 0))) {
                    return 29;  // Año Bisiesto
                } else {
                    return 28;
                }
            default:
                throw new java.lang.IllegalArgumentException(
                        "El mes debe estar entre 0 y 11");
        }
    }

    public String nombreDelMes(int mes) {

        switch (mes) {
            case 0:  // Enero
                return "Enero";
            case 2:  // Marzo
                return "Marzo";
            case 4:  // Mayo
                return "Mayo";
            case 6:  // Julio
                return "Julio";
            case 7:  // Agosto
                return "Agosto";
            case 9:  // Octubre
                return "Octubre";
            case 11: // Diciembre
                return "Diciembre";
            case 3:  // Abril
                return "Abril";
            case 5:  // Junio
                return "Junio";
            case 8:  // Septiembre
                return "Septiembre";
            case 10: // Noviembre
                return "Noviembre";
            case 1:  // Febrero
                return "Febrero";
            default:
                throw new java.lang.IllegalArgumentException(
                        "El mes debe estar entre 0 y 11");
        }
    }

    public String getColumnValue(Parroquia p, String fecha, Especie e, List<HashMap> rows) {
        String s = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(fecha);
            for (HashMap row : rows) {
                String d2 = ((String) row.get("fechaElaboracion"));
                String name = (String) row.get("nombreparroquia");
                String especie = (String) row.get("nombre");
//                
//                System.out.println(d == d2);
//                System.out.println(fecha);
//                System.out.println(d2);
//                System.out.println(d == d2);
//                System.out.println(p.getNombre());
//                System.out.println(name);
//                System.out.println(name.equals(p.getNombre()));
//                System.out.println(especie);
//                System.out.println(e.getNombre());
//                System.out.println(especie.equals(e.getNombre()));
//                System.out.println("------------------------------------");
                if (fecha.equals(d2) && name.equals(p.getNombre()) && especie.equals(e.getNombre())) {
                    if (s.equals("")) {
                        s = (String) row.get("cantidad");
                    } else {
                        s = "" + (Integer.valueOf(s) + Integer.valueOf((String) row.get("cantidad")));
                    }
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(ExportacionJornada.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public int getColumnTotal(String fecha, Especie e, List<HashMap> rows) {
        int s = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(fecha);
            for (HashMap row : rows) {
                String d2 = ((String) row.get("fechaElaboracion"));
                String especie = (String) row.get("nombre");
                if (fecha.equals(d2) && especie.equals(e.getNombre())) {
                    s += Integer.valueOf((String) row.get("cantidad"));
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(ExportacionJornada.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

}
