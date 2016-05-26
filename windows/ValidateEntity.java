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

import com.megagroup.reflection.ReflectionUtils;
import com.megagroup.utilidades.Logger;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.logging.Level;
import java.util.regex.Matcher;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import ve.zoonosis.model.entidades.Entidad;

/**
 *
 * @author angel.colina
 */
public class ValidateEntity {

    private static final Logger LOG = Logger.getLogger(ValidateEntity.class.getName());

    private final Entidad entidad;

    public ValidateEntity(Entidad entidad) {
        this.entidad = Objects.requireNonNull(entidad, "La entidad no puede ser nula");
    }

    public boolean validate() {
        Field[] fields = ReflectionUtils.getAllFields(entidad.getClass());
        for (Field field : fields) {
            Annotation[] annotation = field.getAnnotations();
            if (annotation.length == 0) {
                continue;
            }
            if (!validateNotNull(field)) {
                return false;
            }
            if (!validateSize(field)) {
                return false;
            }
            if (!validateMin(field)) {
                return false;
            }
            if (!validateMax(field)) {
                return false;
            }
        }
        return true;
    }

    public boolean validateNotNull(Field field) {
        NotNull notnull = field.getAnnotation(NotNull.class);
        if (notnull != null) {
            Object v = ReflectionUtils.runGetter(field, entidad);
            if (v == null) {
                LOG.LOGGER.log(Level.INFO, "El atributo {0} no debe ser nulo", field.getName());
                return false;
            }
        }
        return true;
    }

    public boolean validateSize(Field field) {
        Size size = field.getAnnotation(Size.class);
        if (size != null) {
            String v = ReflectionUtils.runGetter(field, entidad);
            if (v.length() < size.min() || v.length() > size.max()) {
                LOG.LOGGER.log(Level.INFO, "El atributo {0} debe contener entre {1} y {2} caracteres",
                        new Object[]{field.getName(), size.min(), size.max()});
                return false;
            }
        }
        return true;
    }

    public boolean validateMin(Field field) {
        Min min = field.getAnnotation(Min.class);
        if (min != null) {
            Integer v = ReflectionUtils.runGetter(field, entidad);
            if (v < min.value()) {
                LOG.LOGGER.log(Level.INFO, "El atributo {0} debe tener un valor minimo de {1}",
                        new Object[]{field.getName(), min.value()});
                return false;
            }
        }
        return true;
    }

    public boolean validateMax(Field field) {
        Max max = field.getAnnotation(Max.class);
        if (max != null) {
            Integer v = ReflectionUtils.runGetter(field, entidad);
            if (v > max.value()) {
                LOG.LOGGER.log(Level.INFO, "El atributo {0} debe tener un valor maximo de {1}",
                        new Object[]{field.getName(), max.value()});
                return false;
            }
        }
        return true;
    }

    public boolean validatePattern(Field field) {
        Pattern pattern = field.getAnnotation(Pattern.class);
        if (pattern != null) {
            String v = ReflectionUtils.runGetter(field, entidad);
            Matcher m = java.util.regex.Pattern.compile(pattern.regexp()).matcher(v);

            if (!m.find()) {
                LOG.LOGGER.log(Level.INFO, "El atributo {0} no posee concidencia",
                        new Object[]{field.getName()});
                return false;
            }
        }
        return true;
    }
}
