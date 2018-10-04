package Presentation.Controller;

import Composants_Visuels.WindowButtons;
import Interfaces.Controllable;
import POJO.Contact;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Gestionnaire_Repertoire extends WindowButtons implements Controllable<Contact> {

    public void  customResize(TreeTableView view) {

        AtomicLong width = new AtomicLong();
        Iterator<TreeTableColumn> i=view.getColumns().iterator();
        while (i.hasNext()){
            width.addAndGet((long) i.next().getWidth());
        }

        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            i=view.getColumns().iterator();
            while (i.hasNext()){
                TreeTableColumn t=i.next();
                t.setPrefWidth(t.getWidth()+(tableWidth - width.get()) / (view.getColumns().size()));
            }
        }
    }
}
