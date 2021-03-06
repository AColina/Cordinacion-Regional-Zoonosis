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
package ve.zoonosis.vistas.modulos.casos;

import com.megagroup.componentes.MLabel;
import com.megagroup.componentes.MTextField;
import javax.swing.JComboBox;
import ve.zoonosis.model.bean.AbstractForm;
import ve.zoonosis.model.entidades.EntidadGlobal;
import ve.zoonosis.model.entidades.administracion.Municipio;
import ve.zoonosis.model.entidades.administracion.Parroquia;
import ve.zoonosis.model.entidades.funcionales.Animal;

/**
 *
 * @author angel.colina
 * @param <E>
 */
public abstract class NuevoCaso<E extends EntidadGlobal> extends AbstractForm<E> {

    {
        initComponents();
    }

    /**
     * Creates new form NuevaJornada
     */
    protected NuevoCaso() {
        super();
    }

    protected NuevoCaso(E entity) {
        super(entity);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({"unchecked", "Convert2Diamond"})
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        municipio = new javax.swing.JComboBox<Municipio>();
        parroquia = new javax.swing.JComboBox<Parroquia>();
        animal = new javax.swing.JComboBox<Animal>();
        cantidadIngresado = new com.megagroup.componentes.MTextField();
        com.megagroup.componentes.MLabel mLabel1 = new com.megagroup.componentes.MLabel();
        com.megagroup.componentes.MLabel mLabel2 = new com.megagroup.componentes.MLabel();
        com.megagroup.componentes.MLabel mLabel4 = new com.megagroup.componentes.MLabel();
        com.megagroup.componentes.MLabel mLabel5 = new com.megagroup.componentes.MLabel();
        com.megagroup.componentes.MLabel mLabel3 = new com.megagroup.componentes.MLabel();
        cantidadPositivos = new com.megagroup.componentes.MTextField();
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        aceptar = new com.megagroup.componentes.MButton();
        cancelar = new com.megagroup.componentes.MButton();
        parroquiaError = new com.megagroup.componentes.MLabel();
        animalError = new com.megagroup.componentes.MLabel();
        cantidadError = new com.megagroup.componentes.MLabel();
        positivosError = new com.megagroup.componentes.MLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        municipio.setBackground(new java.awt.Color(255, 255, 255));
        municipio.setName("municipio"); // NOI18N
        municipio.setPreferredSize(new java.awt.Dimension(150, 24));

        parroquia.setBackground(new java.awt.Color(255, 255, 255));
        parroquia.setName("parroquia"); // NOI18N
        parroquia.setPreferredSize(new java.awt.Dimension(150, 24));

        animal.setBackground(new java.awt.Color(255, 255, 255));
        animal.setName("animal"); // NOI18N
        animal.setPreferredSize(new java.awt.Dimension(150, 24));

        cantidadIngresado.setName("cantidadIngresado"); // NOI18N
        cantidadIngresado.setPreferredSize(new java.awt.Dimension(150, 24));

        mLabel1.setRequired(true);
        mLabel1.setText("Cantidad animales");

        mLabel2.setRequired(true);
        mLabel2.setText("Animal");

        mLabel4.setRequired(true);
        mLabel4.setText("Parroquia");

        mLabel5.setRequired(true);
        mLabel5.setText("Municipio");

        mLabel3.setRequired(true);
        mLabel3.setText("Casos Positivos");

        cantidadPositivos.setName("cantidadPositivos"); // NOI18N
        cantidadPositivos.setPreferredSize(new java.awt.Dimension(150, 24));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        aceptar.setText("Aceptar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 72, 11, 0);
        jPanel1.add(aceptar, gridBagConstraints);

        cancelar.setText("Cancelar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 18, 11, 71);
        jPanel1.add(cancelar, gridBagConstraints);

        parroquiaError.setLabelFor(parroquia);

        animalError.setLabelFor(animal);

        cantidadError.setLabelFor(cantidadIngresado);

        positivosError.setLabelFor(cantidadPositivos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(parroquia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cantidadPositivos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cantidadIngresado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(animal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(municipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cantidadError, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(animalError, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(parroquiaError, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(positivosError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(municipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(parroquia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(parroquiaError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(animal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(animalError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cantidadIngresado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cantidadError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(mLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cantidadPositivos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(positivosError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public JComboBox<Animal> getAnimal() {
        return animal;
    }

    public MLabel getAnimalError() {
        return animalError;
    }

    public MLabel getCantidadError() {
        return cantidadError;
    }

    public MTextField getCantidadIngresado() {
        return cantidadIngresado;
    }

    public MTextField getCantidadPositivos() {
        return cantidadPositivos;
    }

    public JComboBox<Municipio> getMunicipio() {
        return municipio;
    }

    public JComboBox<Parroquia> getParroquia() {
        return parroquia;
    }

    public MLabel getParroquiaError() {
        return parroquiaError;
    }

    public MLabel getPositivosError() {
        return positivosError;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected com.megagroup.componentes.MButton aceptar;
    protected javax.swing.JComboBox<Animal> animal;
    protected com.megagroup.componentes.MLabel animalError;
    protected com.megagroup.componentes.MButton cancelar;
    protected com.megagroup.componentes.MLabel cantidadError;
    protected com.megagroup.componentes.MTextField cantidadIngresado;
    protected com.megagroup.componentes.MTextField cantidadPositivos;
    protected javax.swing.JComboBox<Municipio> municipio;
    protected javax.swing.JComboBox<Parroquia> parroquia;
    protected com.megagroup.componentes.MLabel parroquiaError;
    protected com.megagroup.componentes.MLabel positivosError;
    // End of variables declaration//GEN-END:variables
}
