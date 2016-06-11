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
package ve.zoonosis.controller.seguridad;

import com.megagroup.Application;
import com.megagroup.componentes.MGrowl;
import com.megagroup.model.enums.MGrowlState;
import com.megagroup.utilidades.Logger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.logging.Level;
import javax.swing.JMenuBar;
import org.apache.commons.lang3.StringUtils;
import org.jdesktop.xswingx.PromptSupport;
import ve.zoonosis.model.entidades.administracion.Usuario;
import ve.zoonosis.vistas.Index;
import ve.zoonosis.vistas.Template;
import ve.zoonosis.vistas.seguridad.Login;
import windows.Recursos;
import windows.RequestBuilder;
import windows.ScanWeek;
import windows.webservices.utilidades.MetodosDeEnvio;

/**
 *
 * @author angel.colina
 */
public class LoginController extends Login implements Runnable {

    private static final Logger LOG = Logger.getLogger(LoginController.class);
    private static Usuario usuario;
    private RequestBuilder rb;

    public LoginController() {
        this((Index) Application.getAPLICATION_FRAME());
    }

    public LoginController(Index index) {
       nombreUsuario.setPlaceHolder("Usuario");
        PromptSupport.setPrompt("Contraseña", contrasena);
        JMenuBar m = index.getJMenuBar();
        m.setVisible(false);
        iniciar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
        contrasena.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                iniciarSesion(e);
            }

        });
        invitado.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                usuario = null;
                LOG.LOGGER.log(Level.INFO, "Usuario : Sesion de invitado");
                Index.cambiarPagina(new Template());
                new ScanWeek().start();
                JMenuBar m = Application.getAPLICATION_FRAME().getJMenuBar();
                m.setVisible(true);
            }
        });
        nombreUsuario.requestFocus();
    }

    //METODOS PUBLICOS
    public static void cerrarSesion() {
//        try {
            System.gc();
//            Boolean b = new RequestBuilder("rest/seguridad/logout",
//                    new HashMap<String, Object>() {
//                        {
//                            put("idUsuario", usuario.getId());
//                        }
//                    }, MetodosDeEnvio.POST)
//                    .ejecutarJson(Boolean.class);
            if (true) {
                LOG.LOGGER.log(Level.INFO, "Usuario : cerro sesion");
                usuario = null;
                Index.cambiarPagina(new LoginController());
            }
//        } catch (URISyntaxException ex) {
//            LOG.LOGGER.log(Level.SEVERE, null, ex);
//        }
        System.gc();
    }

    //METODOS PRIVADOS
    private void iniciarSesion(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            evt.consume();
            iniciarSesion();
        }
    }

    private void iniciarSesion() {
        iniciar.setCursor(Recursos.WAIT_CURSOR);
        iniciar.setEnabled(false);
        nombreUsuario.setEnabled(false);
        contrasena.setEnabled(false);
        new Thread(this).start();
    }

    private void alerta(String msj) {
        nombreUsuario.setEnabled(true);
        contrasena.setEnabled(true);
        if (!StringUtils.isEmpty(msj)) {
            MGrowl.showGrowl(MGrowlState.ERROR, msj);
        }
        iniciar.setCursor(Recursos.HAND_CURSOR);
        iniciar.setEnabled(true);
        Application.getAPLICATION_FRAME().setCursor(Recursos.DEFAULT_CURSOR);
    }

    @Override
    public void run() {
        usuario = null;

        if (StringUtils.isEmpty(nombreUsuario.getText())) {
            alerta("Por favor ingrese nombre de usuario.");
            return;
        } else if (StringUtils.isEmpty(contrasena.getText())) {
            alerta("Por favor ingrese la contraseña.");
            return;
        }
        String descripcion = "Error de Conexion";
        try {

            rb = new RequestBuilder("services/administracion/PersonaWs/BuscarUsuario.php",
                    new HashMap<String, Object>() {
                        {
                            put("usr", nombreUsuario.getText());
                            put("pass", contrasena.getText());
                        }
                    });

            usuario = (Usuario) rb.ejecutarJson(Usuario.class);
        } catch (URISyntaxException ex) {
            descripcion = "Caracteres inválidos";
        } catch (RuntimeException ex) {
            LOG.LOGGER.log(Level.WARNING, null, ex);
            alerta(null);
        }

        if (usuario == null && !StringUtils.isEmpty(descripcion)) {
            alerta(descripcion);
            return;
        }
        if (usuario != null && !usuario.getNombre().equalsIgnoreCase("error")) {
            LOG.LOGGER.log(Level.INFO, "Usuario : {0} inicia sesion", usuario.getNombre());
             new ScanWeek().start();
            validarPermisos();
            Index.cambiarPagina(new Template());
            Application.getAPLICATION_FRAME().setCursor(Recursos.DEFAULT_CURSOR);
            return;
        }
        if (usuario != null) {
            alerta(usuario.getContrasena());
        }
    }

    private void validarPermisos() {
        JMenuBar m = Application.getAPLICATION_FRAME().getJMenuBar();
        m.setVisible(true);
        
    }

    public static Usuario getUsuario() {
        return usuario;
    }

}
