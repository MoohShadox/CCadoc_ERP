package Presentation.Controller;

import Composants_Visuels.WindowButtons;
import POJO.Inventaire;
import POJO.Stock;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public abstract class Correction_Panel extends WindowButtons {
    protected String stock;
    protected String inventaire;
    protected HashMap<String,String> titres_stock = new HashMap<>();
    protected HashMap<String,Integer> qte_stock = new HashMap<>();
    protected HashMap<String,Integer> qte_inventaire = new HashMap<>();
    protected HashMap<String,Integer> resultat = new HashMap<>();
    protected ObservableList<SimpleStringProperty> list = FXCollections.observableArrayList();
    protected TableColumn<SimpleStringProperty,String> colonne_rez = new TableColumn<>("");
    protected TableView<SimpleStringProperty> TableComparaison;

    public abstract void remplir_resultats() throws SQLException;

    public abstract void setColonne_rez(TableColumn<SimpleStringProperty,String> colonne);


    public abstract TableView<SimpleStringProperty> provideTable();

    public void setStockEtInventaire(String S, String i) throws SQLException {
        stock = S;
        inventaire = i;
        setStockEtInventaire();
    }

    //Fonction qui rempli les différentes collections necessaires aux calculs
    protected abstract void remplissageDonnee() throws SQLException;


    private void setStockEtInventaire() throws SQLException {

        remplir_resultats();
        TableComparaison = provideTable();
        remplissageDonnee();
        TableComparaison.setItems(list);
        TableColumn<SimpleStringProperty,String> C = new TableColumn<>("Titre ");
        C.setCellValueFactory(cell -> new SimpleStringProperty(""+titres_stock.get(cell.getValue().get())) );
        TableComparaison.getColumns().add(C);
        //Création colonnes stock
        C = new TableColumn<>("Quantité Stock");
        C.setCellValueFactory(cell -> new SimpleStringProperty(""+qte_stock.get(cell.getValue().get())) );
        TableComparaison.getColumns().add(C);
        //Création colonnes inventaire
        C = new TableColumn<>("Quantité Inventaire");
        C.setCellValueFactory(cell -> new SimpleStringProperty(""+qte_inventaire.get(cell.getValue().get())) );
        TableComparaison.getColumns().add(C);
        C = new TableColumn<>("Comparaison");
        C.setCellValueFactory(cell -> cell.getValue());
        setColonne_rez(C);
        TableComparaison.getColumns().add(C);

    }
}
