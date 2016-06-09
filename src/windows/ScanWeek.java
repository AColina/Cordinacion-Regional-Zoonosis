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
package windows;

import com.megagroup.utilidades.Logger;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import org.joda.time.DateTime;
import ve.zoonosis.model.entidades.calendario.Semana;
import ve.zoonosis.model.pojos.CrearSemanasPojo;
import windows.webservices.utilidades.MetodosDeEnvio;

/**
 *
 * @author angel.colina
 */
public class ScanWeek extends Thread {

    private static final Logger LOG = Logger.getLogger(ScanWeek.class);

    @Override
    public void run() {
        try {
            List<Semana> s = new RequestBuilder("services/calendario/WeeksForYear.php",
                    new HashMap<String, Object>() {
                        {
                            put("year", new DateTime().getYear());
                        }

                    }).ejecutarJson(List.class, Semana.class);

            Recursos.SEMANA_ACTUAL = new RequestBuilder("services/calendario/WeekForYearNumber.php",
                    new HashMap<String, Object>() {
                        {
                            put("year", new DateTime().getYear());
                            put("number", Recursos.getActualweek());
                        }

                    }).ejecutarJson(Semana.class);

            if (s != null && !s.isEmpty()) {
                return;
            }

            new RequestBuilder("services/calendario/createWeeks.php")
                    .crearJson(new CrearSemanasPojo(new DateTime().getYear(), Recursos.generateWeeks()))
                    .setMetodo(MetodosDeEnvio.POST).ejecutarJson();

            Recursos.SEMANA_ACTUAL = new RequestBuilder("services/calendario/WeekForYearNumber.php",
                    new HashMap<String, Object>() {
                        {
                            put("year", new DateTime().getYear());
                            put("number", Recursos.getActualweek());
                        }

                    }).ejecutarJson(Semana.class);

        } catch (URISyntaxException | RuntimeException ex) {
            LOG.LOGGER.log(Level.SEVERE, null, ex);
        }
    }

}
