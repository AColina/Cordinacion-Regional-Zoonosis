/*
 * Copyright 2016 clases.
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
package ve.zoonosis.model.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author clases
 */
public class InternalFrame extends JInternalFrame {

    {
        setClosable(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        this.setBackground(Color.WHITE);
    }

    public InternalFrame() {
    }

    public InternalFrame(String title) {
        super(title);
    }

    public InternalFrame(String title, boolean resizable) {
        super(title, resizable);
    }

    @Override
    public Component add(Component comp) {
        Component c = super.add(comp);
        this.pack();
        return c;
    }

}
