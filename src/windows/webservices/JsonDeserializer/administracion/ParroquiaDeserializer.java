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
package windows.webservices.JsonDeserializer.administracion;

import ve.zoonosis.model.entidades.administracion.Parroquia;
import windows.webservices.JsonDeserializer.Deserializer;

/**
 *
 * @author angel.colina
 */
public class ParroquiaDeserializer extends Deserializer<Parroquia> {

    public ParroquiaDeserializer() {
        super(Parroquia.class);
    }

}
