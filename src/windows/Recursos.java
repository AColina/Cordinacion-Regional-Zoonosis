/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import com.megagroup.utilidades.Logger;
import java.awt.Cursor;
import java.io.File;
import java.text.SimpleDateFormat;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;
import org.joda.time.Period;
import ve.zoonosis.model.entidades.calendario.Semana;

/**
 *
 * @author angel.colina
 */
public class Recursos {

    //CURSORES
    public static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    public static final Cursor WAIT_CURSOR = new Cursor(Cursor.WAIT_CURSOR);
    public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
    //FORMATOS FECHA
    public static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("dd-MM-yyyy");
    public static Semana SEMANA_ACTUAL;
    
    public static final String CONFIG_FOLDER = new File("").getAbsolutePath() + "/Setting";

    private static final Logger LOG = Logger.getLogger(Recursos.class);

    /**
     * Inicializa los recursos del sistema
     */
    public static void inicializarRecursos() {

        //CREANDO CARPETA DE CONFIGURACIONES
        File f2 = new File(CONFIG_FOLDER);
        if (!f2.exists() || !f2.isDirectory()) {
            f2.mkdir();
        }
    }

    /**
     * Este metodo se encarga de buscar todas las semnas en el año actual
     *
     * @return numero de semanas
     */
    public static int generateWeeks() {
//        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        DateTime d = new DateTime();
        Period weekPeriod = new Period().withWeeks(1);
        DateTime startDate = new DateTime(d.getYear(), 1, 1, 0, 0, 0, 0);
        while (startDate.getDayOfWeek() != DateTimeConstants.MONDAY) {
            startDate = startDate.plusDays(1);
        }

        DateTime endDate = new DateTime(d.getYear() + 1, 1, 1, 0, 0, 0, 0);
        Interval i = new Interval(startDate, weekPeriod);
        int ct = 0;
        while (i.getStart().isBefore(endDate)) {

            i = new Interval(i.getStart().plus(weekPeriod), weekPeriod);
            ct++;
        }
        return ct;
    }
    public static int getActualweek() {
//        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        DateTime d = new DateTime();
        Period weekPeriod = new Period().withWeeks(1);
        DateTime startDate = new DateTime(d.getYear(), 1, 1, 0, 0, 0, 0);
        while (startDate.getDayOfWeek() != DateTimeConstants.MONDAY) {
            startDate = startDate.plusDays(1);
        }                                                                                       

        DateTime endDate = new DateTime(d);
        Interval i = new Interval(startDate, weekPeriod);
        int ct = 0;
        while (i.getStart().isBefore(endDate)) {

            i = new Interval(i.getStart().plus(weekPeriod), weekPeriod);
            ct++;
        }
        return ct;
    }

}
