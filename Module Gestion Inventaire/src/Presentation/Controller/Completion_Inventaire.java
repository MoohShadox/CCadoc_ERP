package Presentation.Controller;

import Connections.ConnectionOrcl;
import DAO.DAO;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Exceptions.NonExistantDansLesInfos;
import Interfaces.Controlleur_Visualisable;
import Interfaces.Modele;
import POJO.*;

import Repository.InventaireDAO;
import com.gluonhq.charm.glisten.control.AutoCompleteTextField;
import com.gluonhq.charm.glisten.control.SettingsPane;
import com.gluonhq.charm.glisten.control.settings.DefaultOption;
import com.gluonhq.charm.glisten.control.settings.Option;
import com.gluonhq.charm.glisten.control.settings.OptionBase;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import org.controlsfx.control.textfield.*;


public class Completion_Inventaire extends Controlleur_Visualisable<Inventorier> {


    public void Appliquer_Action(ActionEvent actionEvent) throws SQLException {
        Statement S = ConnectionOrcl.getInstance().createStatement();
        String colonne = null;
        String valeur = null;
        for(Modele<Inventorier> M:Table.getSelectionModel().getSelectedItems())
        {
            S.executeUpdate("UPDATE PRODUIT SET \"DOMAINE\"=\'"+T1.getText()+"\' WHERE ISBN=\'"+M.getProperty("ISBN").get()+"\'");
            S.executeUpdate("UPDATE PRODUIT SET \"DOMAINE2\"=\'"+T2.getText()+"\' WHERE ISBN=\'"+M.getProperty("ISBN").get()+"\'");
            S.executeUpdate("UPDATE PRODUIT SET \"DOMAINE3\"=\'"+T3.getText()+"\' WHERE ISBN=\'"+M.getProperty("ISBN").get()+"\'");
        }
        maj_mapDomaines();

    }

    void maj_mapDomaines() throws SQLException {
        ResultSet RS;
        Statement S = ConnectionOrcl.getInstance().createStatement();
        for(String s:inventaire_courant.getRecensement().getkeys()){
            RS = S.executeQuery("SELECT DOMAINE, DOMAINE2, DOMAINE3,TITRE FROM PRODUIT WHERE ISBN=\'"+s+"\'");
            if(RS.next()){
                map_domaines.put(s,new Trio_Domaines(RS.getString(1),RS.getString(2),RS.getString(3),RS.getString(4)));
            }
            else{
                map_domaines.put(s,new Trio_Domaines("INCONNU","INCONNU","INCONNU","INCONNU"));
            }
        }
        setVisuel();


    }

    public void ENRICHIR_ACTION(ActionEvent actionEvent) {
    }

    class Trio_Domaines{
        String titre;
        String domaine;
        String domaine2;
        String domaine3;

        public Trio_Domaines(String domaine, String domaine2, String domaine3,String titre) {
            this.domaine = domaine;
            this.domaine2 = domaine2;
            this.domaine3 = domaine3;
            this.titre = titre;
        }
    }

    private String nom_inventaire = "Inventaire1";


    @FXML
    private TableView<Modele<Inventorier>> Table;

    @FXML
    private Pane Champ1;

    @FXML
    private ComboBox<String> CBox1;

    @FXML
    private ComboBox<String> CBox2;

    @FXML
    private Pane Champ2;

    @FXML
    private ComboBox<String> CBox3;

    @FXML
    private Pane Champ3;

    private TextField T1,T2,T3;

    private Inventaire inventaire_courant;

    private HashMap<String,Trio_Domaines> map_domaines = new HashMap<>();

    private Collection<String> C = new LinkedList<>();
    private Collection<String> C2 = new LinkedList<>();
    private Collection<String> C3 = new LinkedList<>();
    private ObservableList<String> domaines  = FXCollections.observableArrayList();
    private ObservableList<String> domaines2 = FXCollections.observableArrayList();
    private ObservableList<String> domaines3 = FXCollections.observableArrayList();
    private StringProperty SP1 = new SimpleStringProperty();
    private StringProperty SP2 = new SimpleStringProperty();
    private StringProperty SP3 = new SimpleStringProperty();



    public EventHandler<KeyEvent> getCompleter(String ddomaine,TextField T,Collection C){
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try {
                    C.clear();
                    Statement S = ConnectionOrcl.getInstance().createStatement();
                    ResultSet RS = S.executeQuery("SELECT UNIQUE " + ddomaine+" FROM PRODUIT WHERE "+ddomaine+ " LIKE \'"+T.getText()+"%\'");
                    while(RS.next()){
                        C.add(RS.getString(1));
                    }
                    TextFields.bindAutoCompletion(T,C);
                    RS.close();
                    S.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    @FXML
    void initialize() throws SQLException {

        DAO<Inventaire> D;
        try {
            D = new InventaireDAO(new Inventaire(""));
            inventaire_courant = D.recuperer(nom_inventaire);
        } catch (SQLException | NonExistantDansLaBDD | BuildingException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Statement S = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS;
        maj_mapDomaines();

        domaines.add("DOMAINE");
        domaines.add("DOMAINE2");
        domaines.add("DOMAINE3");
        domaines2.add("DOMAINE");
        domaines2.add("DOMAINE2");
        domaines2.add("DOMAINE3");
        domaines3.add("DOMAINE");
        domaines3.add("DOMAINE2");
        domaines3.add("DOMAINE3");

        T3 = TextFields.createClearableTextField();
        T2 = TextFields.createClearableTextField();
        T1 = TextFields.createClearableTextField();
        Champ1.getChildren().add(T1);
        Champ2.getChildren().add(T2);
        Champ3.getChildren().add(T3);
        CBox2.setVisible(false);
        CBox3.setVisible(false);
        Champ1.setVisible(false);
        Champ2.setVisible(false);
        Champ3.setVisible(false);
        CBox1.setItems(domaines);
        CBox2.setItems(domaines2);
        CBox3.setItems(domaines3);


        CBox1.setOnAction(event -> {
            if(!CBox1.getValue().equalsIgnoreCase(""))
            {
                SP1.setValue(CBox1.getValue());
                CBox2.setVisible(true);
            }
        });
        CBox2.setOnAction(event -> {
            if(!CBox1.getValue().equalsIgnoreCase(""))
            {
                SP2.setValue(CBox2.getValue());
                CBox3.setVisible(true);
            }
        });
        CBox3.setOnAction(event -> {
            if(!CBox1.getValue().equalsIgnoreCase(""))
            {
                SP3.setValue(CBox3.getValue());
            }
        });

        SP1.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(oldValue != null && !oldValue.equalsIgnoreCase(""))
                {
                    domaines2.add(oldValue);
                    domaines3.add(oldValue);
                }
                else
                {
                    Champ1.setVisible(true);
                }
                if(newValue != null && !newValue.equalsIgnoreCase(""))
                {
                    domaines2.remove(newValue);
                    domaines3.remove(newValue);
                    T1.setOnKeyTyped(getCompleter(newValue,T1,C));
                }else
                {
                    Champ1.setVisible(false);
                }
                if(!domaines2.contains(""))
                    domaines2.add("");
                if(!domaines3.contains(""))
                    domaines3.add("");


            }
        });

        SP2.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(oldValue != null && !oldValue.equalsIgnoreCase(""))
                {
                    domaines.add(oldValue);
                    domaines3.add(oldValue);
                }
                else
                {
                    Champ2.setVisible(true);
                }
                if(newValue != null && !newValue.equalsIgnoreCase(""))
                {
                    domaines.remove(newValue);
                    domaines3.remove(newValue);
                    T2.setOnKeyTyped(getCompleter(newValue,T2,C2));
                }else
                {
                    Champ2.setVisible(false);
                }
                if(!domaines3.contains(""))
                    domaines3.add("");
                if(!domaines.contains(""))
                    domaines.add("");


            }
        });

        SP3.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(oldValue != null && !oldValue.equalsIgnoreCase(""))
                {
                    domaines2.add(oldValue);
                    domaines.add(oldValue);
                }else
                {
                    Champ3.setVisible(true);
                }
                if(newValue != null && !newValue.equalsIgnoreCase(""))
                {
                    domaines2.remove(newValue);
                    domaines.remove(newValue);
                    T3.setOnKeyTyped(getCompleter(newValue,T3,C3));
                }else
                {
                    Champ3.setVisible(false);
                }
                if(!domaines2.contains(""))
                    domaines2.add("");
                if(!domaines.contains(""))
                    domaines.add("");

            }
        });
    }


    @Override
    public void setVisuel() {
        Table.setRowFactory(new Callback<TableView<Modele<Inventorier>>, TableRow<Modele<Inventorier>>>() {
            @Override
            public TableRow<Modele<Inventorier>> call(TableView<Modele<Inventorier>> param) {
                return new TableRow<Modele<Inventorier>>(){
                    @Override
                    public void updateSelected(boolean selected) {
                        if(selected && map_domaines.get(getItem().getProperty("ISBN").getValue()).titre.equalsIgnoreCase("INCONNU"))
                        {
                            if(!getStyle().equalsIgnoreCase("-fx-background-color: #ff807b"))
                            {
                                setStyle("-fx-background-color: #ff807b");
                            }
                            else
                                setStyle("");
                        }
                        else{
                            super.updateSelected(selected);

                        }
                    }
                };
            }
        });
        Table.setEditable(true);
        Table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for(String s :inventaire_courant.getRecensement().getkeys()){
            try {
                super.recensement.ajouter(inventaire_courant.getRecensement().recuperer(s));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        HashMap<String,TableColumn<Modele<Inventorier>,String>> H =  super.implenter_visuel(Table);
        H.get("Progression").setVisible(false);
        H.get("Quantite").setVisible(false);
        TableColumn<Modele<Inventorier>,String> T;
        T = new TableColumn<>("TITRE");
        T.setCellValueFactory(cell -> new SimpleStringProperty(map_domaines.get(cell.getValue().getProperty("ISBN").getValue()).titre));
        Table.getColumns().add(T);
        T = new TableColumn<>("DOMAINE");
        T.setCellValueFactory(cell -> new SimpleStringProperty(map_domaines.get(cell.getValue().getProperty("ISBN").getValue()).domaine));
        Table.getColumns().add(T);
        T = new TableColumn<>("DOMAINE2");
        T.setCellValueFactory(cell -> new SimpleStringProperty(map_domaines.get(cell.getValue().getProperty("ISBN").getValue()).domaine2));
        Table.getColumns().add(T);
        T = new TableColumn<>("DOMAINE3");
        T.setCellValueFactory(cell -> new SimpleStringProperty(map_domaines.get(cell.getValue().getProperty("ISBN").getValue()).domaine3));
        Table.getColumns().add(T);
        T = new TableColumn<>("Présence en Base de donnée");
        T.setCellValueFactory(cell -> cell.getValue().getProperty("ISBN"));
        T.setCellFactory(new Callback<TableColumn<Modele<Inventorier>, String>, TableCell<Modele<Inventorier>, String>>() {
            @Override
            public TableCell<Modele<Inventorier>, String> call(TableColumn<Modele<Inventorier>, String> param) {
                TableCell<Modele<Inventorier>,String> TT = new TableCell<>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        Node N = null;
                        String style = "";
                        if(item != null && !empty){
                            System.out.println(item);
                            if( map_domaines.get(item).titre.equalsIgnoreCase("INCONNU"))
                            {
                                Button button = new Button("Enrichir");
                                N = button;
                                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        Inventorier I = Completion_Inventaire.super.recensement.recuperer(item);
                                        try {
                                            I.enrichir_BDD();
                                            maj_mapDomaines();
                                            setVisuel();
                                        } catch (SQLException | IllegalAccessException | NonExistantDansLesInfos | BuildingException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                style = "-fx-background-color: #ff8761";
                            }
                            else{
                                Label L = new Label("PRESENT");
                                N = L;
                                style = "-fx-background-color: #c1ffa8";
                            }
                        }
                        setStyle(style);
                        setGraphic(N);
                        super.updateItem(item, empty);
                    }

                };
                return TT;
            }
        });
        Table.getColumns().add(T);
    }

    @FXML
    private void fermerFenetre() {
        this.fermerFenetre(Table);
    }

    @FXML
    private void Iconify() {
        this.Iconify(Table);
    }
}
