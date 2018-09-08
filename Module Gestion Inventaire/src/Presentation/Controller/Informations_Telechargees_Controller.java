package Presentation.Controller;

import Composants_Visuels.VisuelCreator;
import Interfaces.Controller;
import Interfaces.Modele;
import POJO.Inventorier;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

public class Informations_Telechargees_Controller extends Controller<Inventorier, Modele<Inventorier>> {

    @FXML
    private Pane pane;

    @FXML
    private TableView<LinkedList<String>> InformationTable = new TableView<>();

    @FXML
    private ImageView jacquette;

    private Inventorier I;


    @FXML
    private void fermerFenetre(javafx.event.ActionEvent e) {
        this.fermerFenetre(pane);
    }

    @Override
    public void RefreshCollection(Collection<Inventorier> T) {
        if (T.size() == 1) {
            for (Inventorier II : T)
                I = II;
        }
        Afficher_Apercu();

    }

    @Override
    public void setVisuel() {

    }


    void Afficher_Apercu() {
        VisuelCreator VC = new VisuelCreator(I.getRenseignements());
        VC.setVisuel(InformationTable);
        InformationTable.getColumns().get(1).editableProperty().setValue(true);
        InformationTable.getStyleClass().add("noheader");
        InformationTable.setColumnResizePolicy((param) -> true);
        Platform.runLater(() -> customResize(InformationTable,33));
        jacquette.setImage(new Image("file:///" +  I.getRenseignements().get("URL")));
        jacquette.setLayoutX(InformationTable.getWidth() + 50);
        //redimensionnement du pane
        if (InformationTable.getItems().size() > 10) {
            pane.setPrefHeight(pane.getHeight() + (InformationTable.getItems().size() - 10) * 30);
        }
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


}
