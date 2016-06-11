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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.MutableComboBoxModel;

/**
 *
 * @author angel.colina
 * @param <E>
 */
public class ListComboBoxModel<E> extends AbstractListModel<E> implements MutableComboBoxModel<E>, Serializable {

    private List<E> items;
    private int selectIndex;

    public ListComboBoxModel() {
        items = new ArrayList<>();
        verificarSelectItem();
    }

    public ListComboBoxModel(List<E> items) {
        this.items = items;
        verificarSelectItem();
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public E getElementAt(int index) {
        return index >= 0 && index < items.size() ? items.get(index) : null;
    }

    @Override
    public void addElement(E item) {
        items.add(item);
        fireIntervalAdded(this, 0, items.size());
    }

    public void addElement(Collection items) {
        this.items.addAll(items);
        fireIntervalAdded(this, 0, this.items.size());
    }

    @Override
    public void removeElement(Object obj) {
        int index = items.indexOf((E) obj);
        if (index != -1) {
            removeElementAt(index);
        }

    }

    @Override
    public void insertElementAt(E item, int index) {
        items.add(index, item);
        fireContentsChanged(this, index, index);
    }

    @Override
    public void removeElementAt(int index) {
        if (index == selectIndex) {
            if (index == 0) {
                setSelectedItem(getSize() == 1 ? null : getElementAt(index + 1));
            } else {
                setSelectedItem(getElementAt(index - 1));
            }
        }
        items.remove(index);
        fireIntervalRemoved(this, 0, items.size());
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selectIndex = items.indexOf(anItem);
        fireContentsChanged(this, -1, -1);
    }

    @Override
    public Object getSelectedItem() {
        return getElementAt(selectIndex);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean contains(E o) {
        return items.contains(o);
    }

    public void clear() {
        items.clear();
        fireContentsChanged(this, 0, items.size());
    }

    //METODOS PRIVADOS
    private void verificarSelectItem() {
        if (getSize() > 0) {
            selectIndex = 0;
        }
    }
    //GETTER AND SETTER

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
        fireContentsChanged(this, 0, items.size());
    }

}
