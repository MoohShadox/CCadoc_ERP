package Presentation.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeTableView;

public class VisualisationContactController extends Gestionnaire_Repertoire {

    @FXML
    private TreeTableView<SimpleStringProperty> TableContact =new TreeTableView<>();

    @FXML
    private void fermerFenetre() {
        this.fermerFenetre(TableContact);
    }

    @FXML
    private void Iconify() {
        this.Iconify(TableContact);
    }

}
