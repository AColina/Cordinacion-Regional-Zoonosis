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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.regex.Matcher;
import javax.swing.JLabel;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import ve.zoonosis.model.bean.AbstractForm;
import ve.zoonosis.model.entidades.EntidadGlobal;

/**
 *
 * @author angel.colina
 */
public class ValidateEntity {

    private static final Logger LOG = Logger.getLogger(ValidateEntity.class.getName());

    private final EntidadGlobal entidad;

    public ValidateEntity(EntidadGlobal entidad) {
        this.entidad = Objects.requireNonNull(entidad, "La entidad no puede ser nula");
    }

    public boolean validate(AbstractForm form, String... excludeField) {
        return validate(form, null, excludeField);
    }

    public boolean validate(AbstractForm form, String[] fieldsName, String[] excludeField) {
        List<JLabel> lista = new ArrayList<>();
        Field[] fields = ReflectionUtils.getAllFields(form.getClass());
        for (Field field : fields) {
            if (JLabel.class.isAssignableFrom(field.getType())) {
                JLabel l = ReflectionUtils.runGetter(field, form);
                l.setText(null);
                l.setVisible(false);
                lista.add(l);
            }
        }
        ValidationContex fieldError = validateGeneral(fieldsName, excludeField);
        if (fieldError == null) {
            return true;
        }
        for (JLabel label : lista) {
            if (label.getLabelFor().getName().equalsIgnoreCase(fieldError.field().getName())) {
                label.setText("<html><p>" + fieldError.message().replace("\n", "<br>") + "</p></html>");
                label.setVisible(true);
            }
        }
        return false;
    }

    public ValidationContex validFields(String... fields) {
        return validateGeneral(fields, null);
    }

    public boolean validate() {
        return validateGeneral(new String[0], new String[0]) == null;
    }

    public ValidationContex validateNotNull(Field field) {
        ValidateContex contex = new ValidateContex();
        NotNull notnull = field.getAnnotation(NotNull.class);
        if (notnull != null) {
            Object v = ReflectionUtils.runGetter(field, entidad);
            if (v == null || StringUtils.isEmpty(v.toString())) {
                contex.annotation = notnull;
                contex.field = field;
                contex.classError = entidad.getClass();
                contex.mensaje = notnull.message();
                contex.value = null;
                LOG.LOGGER.log(Level.INFO, "El atributo {0} no debe ser nulo", field.getName());
                return contex;
            }
        }
        return null;
    }

    public ValidationContex validateSize(Field field) {
        ValidateContex contex = new ValidateContex();
        Size size = field.getAnnotation(Size.class);
        if (size != null) {
            String v = ReflectionUtils.runGetter(field, entidad);
            if (v.length() < size.min() || v.length() > size.max()) {
                contex.annotation = size;
                contex.field = field;
                contex.classError = entidad.getClass();
                contex.mensaje = size.message();
                contex.value = size.min() + " " + size.max();
                LOG.LOGGER.log(Level.INFO, "El atributo {0} debe contener entre {1} y {2} caracteres",
                        new Object[]{field.getName(), size.min(), size.max()});
                return contex;
            }
        }
        return null;
    }

    public ValidationContex validateMin(Field field) {
        ValidateContex contex = new ValidateContex();
        Min min = field.getAnnotation(Min.class);
        if (min != null) {
            Integer v = ReflectionUtils.runGetter(field, entidad);
            if (v < min.value()) {
                contex.annotation = min;
                contex.field = field;
                contex.classError = entidad.getClass();
                contex.mensaje = min.message();
                contex.value = min.value();
                LOG.LOGGER.log(Level.INFO, "El atributo {0} debe tener un valor minimo de {1}",
                        new Object[]{field.getName(), min.value()});
                return contex;
            }
        }
        return null;
    }

    public ValidationContex validateMax(Field field) {
        ValidateContex contex = new ValidateContex();
        Max max = field.getAnnotation(Max.class);
        if (max != null) {
            Integer v = ReflectionUtils.runGetter(field, entidad);
            if (v > max.value()) {
                contex.annotation = max;
                contex.field = field;
                contex.classError = entidad.getClass();
                contex.mensaje = max.message();
                contex.value = max.value();
                LOG.LOGGER.log(Level.INFO, "El atributo {0} debe tener un valor maximo de {1}",
                        new Object[]{field.getName(), max.value()});
                return contex;
            }
        }
        return null;
    }

    public ValidationContex validatePattern(Field field) {
        ValidateContex contex = new ValidateContex();
        Pattern pattern = field.getAnnotation(Pattern.class);
        if (pattern != null) {
            String v = ReflectionUtils.runGetter(field, entidad);
            if (StringUtils.isEmpty(pattern.regexp()) || StringUtils.isEmpty(v)) {
                return null;
            }

            Matcher m = java.util.regex.Pattern.compile(pattern.regexp()).matcher(v);

            if (!m.find()) {
                contex.annotation = pattern;
                contex.field = field;
                contex.classError = entidad.getClass();
                contex.mensaje = pattern.message();
                contex.value = pattern.regexp();
                LOG.LOGGER.log(Level.INFO, "El atributo {0} no posee concidencia",
                        new Object[]{field.getName()});
                return contex;
            }

        }
        return null;

    }

    private ValidationContex validateGeneral(String[] fieldsName, String[] excludeField) {
        ValidationContex contex;
        Field[] fields;
        if (fieldsName == null || fieldsName.length == 0) {
            fields = ReflectionUtils.getAllFields(entidad.getClass());
        } else {
            fields = new Field[fieldsName.length];
            for (int i = 0; i < fieldsName.length; i++) {
                fields[i] = ReflectionUtils.getField(entidad, fieldsName[i]);
            }
        }
        for (Field field : fields) {
            if (excludeField != null) {
                boolean b = false;
                for (String exField : excludeField) {
                    if (exField.equalsIgnoreCase(field.getName())) {
                        b = true;
                        break;
                    }
                }
                if (b) {
                    continue;
                }
            }
            Annotation[] annotation = field.getAnnotations();
            if (annotation.length == 0) {
                continue;
            }
            contex = validateNotNull(field);
            if (contex != null) {
                return contex;
            }
            contex = validateSize(field);
            if (contex != null) {
                return contex;
            }
            contex = validateMin(field);
            if (contex != null) {
                return contex;
            }
            contex = validateMax(field);
            if (contex != null) {
                return contex;
            }
            contex = validatePattern(field);
            if (contex != null) {
                return contex;
            }
        }
        return null;
    }

    private class ValidateContex implements ValidationContex {

        String mensaje;
        Object value;
        Annotation annotation;
        Field field;
        Class classError;

        @Override
        public String message() {
            return mensaje;
        }

        @Override
        public Object value() {
            return value;
        }

        @Override
        public Annotation annotation() {
            return annotation;
        }

        @Override
        public Field field() {
            return field;
        }

        @Override
        public Class classError() {
            return classError;
        }

    }
}
