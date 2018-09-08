package Presentation.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;

public class Transfert_Panel_Controller extends Correction_Panel {

    @FXML
    private TableView<SimpleStringProperty> TableTransfert;


    @Override
    public void remplir_resultats() throws SQLException {

    }

    @Override
    public void setColonne_rez(TableColumn<SimpleStringProperty, String> colonne) {

    }

    @Override
    public TableView<SimpleStringProperty> provideTable() {
        return null;
    }

    @Override
    protected void remplissageDonnee() throws SQLException {

    }

    @FXML
    private void fermerFenetre() {
        this.fermerFenetre(TableTransfert);
    }

    @FXML
    private void Iconify() {
        this.Iconify(TableTransfert);
    }
}
