/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows.webservices.JsonDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import windows.webservices.JsonDeserializer.proceso.CasoDeserializer;
import windows.webservices.JsonDeserializer.administracion.MunicipioDeserializer;
import windows.webservices.JsonDeserializer.administracion.ParroquiaDeserializer;
import windows.webservices.JsonDeserializer.administracion.UsuarioDeserializer;
import windows.webservices.JsonDeserializer.administracion.PersonaDeserializer;
import windows.webservices.JsonDeserializer.calendario.SemanaDeserializer;
import windows.webservices.JsonDeserializer.funcionales.AnimalDeserializer;
import windows.webservices.JsonDeserializer.funcionales.EspecieDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.megagroup.reflection.ReflectionUtils;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.administracion.Parroquia;
import ve.zoonosis.model.entidades.administracion.Permiso;
import ve.zoonosis.model.entidades.administracion.Persona;
import ve.zoonosis.model.entidades.administracion.Usuario;
import ve.zoonosis.model.entidades.calendario.Semana;
import ve.zoonosis.model.entidades.funcionales.Animal;
import ve.zoonosis.model.entidades.funcionales.Especie;
import ve.zoonosis.model.entidades.proceso.Animal_has_Caso;
import ve.zoonosis.model.entidades.proceso.Caso;
import ve.zoonosis.model.entidades.proceso.Novedades;
import ve.zoonosis.model.entidades.proceso.RegistroVacunacion;
import ve.zoonosis.model.entidades.proceso.RegistroVacunacion_has_Animal;
import ve.zoonosis.model.entidades.proceso.Vacunacion;
import windows.webservices.JsonDeserializer.administracion.PermisoDeserializer;
import windows.webservices.JsonDeserializer.proceso.Animal_has_CasoDeserializer;
import windows.webservices.JsonDeserializer.proceso.NovedadesDeserializer;
import windows.webservices.JsonDeserializer.proceso.RegistroVacunacionDeserializer;
import windows.webservices.JsonDeserializer.proceso.RegistroVacunacion_has_AnimalDeserializer;
import windows.webservices.JsonDeserializer.proceso.VacunacionDeserializer;

/**
 *
 * @author angel.colina
 * @param <E>
 */
public abstract class Deserializer<E extends Object> extends JsonDeserializer<E> {

    public static final SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy",Locale.UK);
    private final Class<E> classChild;

    public Deserializer(Class<E> classChild) {
        this.classChild = classChild;
    }

    @Override
    public E deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        try {

            JsonNode json = jp.getCodec().readTree(jp);

            if (StringUtils.isEmpty(json.toString()) || json.toString().length() < 4) {
                return getNullValue(dc);
            }
            E instance = classChild.newInstance();
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule modul = new SimpleModule("parroquia")
                    .addDeserializer(Parroquia.class, new ParroquiaDeserializer())
                    .addDeserializer(Semana.class, new SemanaDeserializer())
                    .addDeserializer(Animal.class, new AnimalDeserializer())
                    .addDeserializer(Semana.class, new SemanaDeserializer())
                    .addDeserializer(Especie.class, new EspecieDeserializer())
                    .addDeserializer(Persona.class, new PersonaDeserializer())
                    .addDeserializer(Permiso.class, new PermisoDeserializer())
                    .addDeserializer(Usuario.class, new UsuarioDeserializer())
                    .addDeserializer(Municipio.class, new MunicipioDeserializer())
                    .addDeserializer(Animal_has_Caso.class, new Animal_has_CasoDeserializer())
                    .addDeserializer(Caso.class, new CasoDeserializer())
                    .addDeserializer(Novedades.class, new NovedadesDeserializer())
                    .addDeserializer(RegistroVacunacion.class, new RegistroVacunacionDeserializer())
                    .addDeserializer(RegistroVacunacion_has_Animal.class, new RegistroVacunacion_has_AnimalDeserializer())
                    .addDeserializer(Vacunacion.class, new VacunacionDeserializer());
            mapper.registerModule(modul);

            for (Field field : ReflectionUtils.getAllFields(classChild)) {
                Object value = null;
                Iterator<String> it = json.fieldNames();
                String column = null;
                String fieldName = field.getName();
                JsonProperty property = field.getAnnotation(JsonProperty.class);
                if (property != null) {
                    fieldName = property.value();
                }
                while (it.hasNext()) {
                    String name = it.next();
                    if (Objects.equals(name.trim(), fieldName)) {
                        column = name;
                        break;
                    }
                }
                if (column == null) {
                    System.out.println("No se reconoce la siguente columna : " + fieldName);
                    continue;
                }
                if (field.getType().equals(String.class)) {
                    value = json.get(column).asText();
                } else if (Collection.class.isAssignableFrom(field.getType())) {
                    if (StringUtils.isNotEmpty(json.get(column).toString())) {
                        ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
                        Class<?> stringListClass = (Class<?>) stringListType.getActualTypeArguments()[0];
                        value = mapper.readValue(json.get(column).toString(), mapper.getTypeFactory().constructCollectionType(List.class, stringListClass));
                    } else {
                        value = null;
                    }
                } else if (!field.getType().equals(Date.class)) {
                    try {
                        value = mapper.convertValue(json.get(column), field.getType());
                    } catch (IllegalArgumentException ex) {
                        value = null;
                    }
                } else {
                    String date = json.get(column).textValue();
                    try {
                    if (date != null) {
                            value = d.parse(date.replace("-", "/"));
                        }
                        } catch (ParseException ex) {
                            Logger.getLogger(Deserializer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                ReflectionUtils.runSetter(field, instance, value);
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Deserializer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getNullValue(dc);
    }

}
