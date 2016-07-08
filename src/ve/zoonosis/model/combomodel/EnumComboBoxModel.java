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
package ve.zoonosis.model.combomodel;

import java.io.Serializable;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import org.apache.commons.lang3.EnumUtils;
import ve.zoonosis.model.enums.IEnum;

/**
 *
 * @author angel.colina
 */
public class EnumComboBoxModel extends AbstractListModel implements ComboBoxModel, Serializable {

    private int index;
    private final List<? extends IEnum> enumItems;

    public EnumComboBoxModel(Class<? extends Enum> enumClass) {
        this.enumItems = EnumUtils.getEnumList(enumClass);
        index = -1;
    }

    @Override
    public int getSize() {
        return enumItems.size();
    }

    @Override
    public Object getElementAt(int index) {
        return enumItems.get(index).getValue();
    }

    public List<? extends IEnum> getEnumItems() {
        return enumItems;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if (anItem == null) {
            index = -1;
            return;
        }
        for (int i = 0; i < getSize(); i++) {
            if (enumItems.get(i).getValue().equals(anItem)) {
                index = i;
                break;
            }
        }
    }

    public <E extends IEnum> E getEnumItem() {
        return (E) enumItems.get(index);
    }

    @Override
    public Object getSelectedItem() {
        return index == -1 ? null : enumItems.get(index).getValue();
    }

}
