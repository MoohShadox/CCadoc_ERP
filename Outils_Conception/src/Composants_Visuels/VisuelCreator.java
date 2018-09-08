package Composants_Visuels;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

public class VisuelCreator {


    private TableView<LinkedList<String>>  visuel;
    private HashMap<String,String> informations;
    private ObservableList<LinkedList<String>> items = FXCollections.observableArrayList();

    public VisuelCreator(){}

    public VisuelCreator(HashMap<String,String> informations){
        this.informations = informations;
        for(String k:informations.keySet()){
            LinkedList<String> L = new LinkedList<>();
            L.add(k);
            L.add(informations.get(k));
            items.add(L);
        }


    }

    public void setVisuel(TableView<LinkedList<String>> T){
        TableColumn<LinkedList<String>,String> T1 = new TableColumn<>();
        TableColumn<LinkedList<String>,String> T2 = new TableColumn<>();
        T1.setCellValueFactory(cell -> {
            SimpleStringProperty S = new SimpleStringProperty(cell.getValue().get(0));
            return S;
        });
        T2.setCellValueFactory(cell -> {
            String ss = cell.getValue().get(1);
            if(cell.getValue().get(0).equalsIgnoreCase("Prix Fnac"))
            {
               ss = ss.replace(" ","").replace("\u0080","€");
            }
            SimpleStringProperty S = new SimpleStringProperty(ss);
            return S;
        });
        T2.setEditable(true);
        T.setEditable(true);
        T2.setCellFactory(TextFieldTableCell.forTableColumn());
        T.getColumns().setAll(T1,T2);
        T.setItems(items);
    }


    public void  customResize(TableView<?> view,double cellSize) {

        AtomicLong width = new AtomicLong();
        view.getColumns().forEach(col -> {
            width.addAndGet((long) col.getWidth());
        });
        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            view.getColumns().forEach(col -> {
                col.setPrefWidth(col.getWidth() + ((tableWidth - width.get()) / (view.getColumns().size())));
            });
        }
        view.setFixedCellSize(cellSize);
        view.prefHeightProperty().bind(view.fixedCellSizeProperty().multiply(view.getItems().size()));
        view.minHeightProperty().bind(view.prefHeightProperty());
        view.maxHeightProperty().bind(view.prefHeightProperty());
    }


    public void  customResize(TableView<?> view) {

        AtomicLong width = new AtomicLong();
        view.getColumns().forEach(col -> {
            width.addAndGet((long) col.getWidth());
        });
        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            view.getColumns().forEach(col -> {
                col.setPrefWidth(col.getWidth() + ((tableWidth - width.get()) / (view.getColumns().size())));
            });
        }
    }

}
