/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.zoonosis.model.bean;

import com.megagroup.bean.BandejaController;
import com.megagroup.componentes.MDataTable;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import ve.zoonosis.model.entidades.EntidadGlobal;

/**
 *
 * @author angel.colina
 * @param <Entity>
 */
public abstract class AbstractInbox<Entity extends EntidadGlobal> extends JPanel implements BandejaController {

    protected final JPopupMenu popupMenu;

    /**
     * indice de donde esta la columna ver
     */
    protected int botonVer;

    {

        popupMenu = new JPopupMenu();

    }

    protected AbstractInbox() {

    }

    public final int getPopUpSelectIndex(Point p) {
        return getBandeja().getmTable().rowAtPoint(p);
    }

    public void addPopupEvent(MouseEvent e) {
        Point p = e.getPoint();
        p.y += 10;
        int index = getPopUpSelectIndex(p);
        popupMenu.removeAll();
        addItemPopUp(index);
        showPopUp(p, index);
    }

    @Override
    public int getSelectIndex() {
        return getBandeja().getmTable().getSelectedRow();
    }

    protected abstract void addItemPopUp(int index);

    protected final void iniciarBandeja(final boolean createPopUp) {
        final MDataTable bandeja = getBandeja();
        bandeja.addMouseEventToMTable(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() > 1) {
                    int row = bandeja.getmTable().rowAtPoint(e.getPoint());
                    if (row < 0) {
                        return;
                    }
                    abrir(row);
                } else if (createPopUp && e.getButton() == MouseEvent.BUTTON3) {
                    addPopupEvent(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int selectColumn = bandeja.getmTable().getSelectedColumn();
                int selectRow = bandeja.getmTable().getSelectedRow();
                if (selectRow < 0) {
                    return;
                }
                if (selectColumn == botonVer) {
                    abrir(selectRow);

                }
            }
        });

    }

    private void showPopUp(Point p, int index) {

        if (index > -1) {
            getBandeja().getmTable().setRowSelectionInterval(index, index);
        }
        popupMenu.show(this, p.x, p.y + 125);
    }

    protected class BuscarLstener implements ActionListener {

        public BuscarLstener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            buscar();
        }
    }

    protected class AbrirActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            abrir(getSelectIndex());
        }

    }
    //GETTER AND SETTER

    public abstract MDataTable getBandeja();

}
