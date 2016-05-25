/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import com.megagroup.utilidades.Logger;
import java.awt.Cursor;
import java.io.File;

/**
 *
 * @author angel.colina
 */
public class Recursos {

    public static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    public static final Cursor WAIT_CURSOR = new Cursor(Cursor.WAIT_CURSOR);
    public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
    public static final String CONFIG_FOLDER = new File("").getAbsolutePath() + "\\Setting";
    private static final Logger LOG = Logger.getLogger(Recursos.class);

    public static void inicializarRecursos() {
        
        //CREANDO CARPETA DE CONFIGURACIONES
        File f2 = new File(CONFIG_FOLDER);
        if (!f2.exists() || !f2.isDirectory()) {
            f2.mkdir();
        }
    }


}
