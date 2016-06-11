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
package ve.zoonosis.model.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author angel.colina
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CrearSemanasPojo {

    int year;
    int semanas;

    public CrearSemanasPojo() {
    }

    public CrearSemanasPojo(int year, int semanas) {
        this.year = year;
        this.semanas = semanas;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemanas() {
        return semanas;
    }

    public void setSemanas(int semanas) {
        this.semanas = semanas;
    }

}
