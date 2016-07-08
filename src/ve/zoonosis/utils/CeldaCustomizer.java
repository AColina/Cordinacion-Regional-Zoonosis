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
package ve.zoonosis.utils;

/**
 *
 * @author Gustavo
 */
public class CeldaCustomizer {

    private Object obj;
    private int rows;
    private int column;
    private boolean bold;

    public CeldaCustomizer(Object obj) {
        this(obj, 1);
    }

    public CeldaCustomizer(Object obj, int column) {
        this(obj, 1, column,false);
    }
    public CeldaCustomizer(Object obj,boolean bold) {
        this(obj, 1,bold);
    }

    public CeldaCustomizer(Object obj, int column,boolean bold) {
        this(obj, 1, column,bold);
    }

    public CeldaCustomizer(Object obj, int rows, int column,boolean bold) {
        this.obj = obj;
        this.rows = rows;
        this.column = column;
        this.bold = bold;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }
    
}
