package Presentation.Controller;


import Composants_Visuels.VisuelCreator;
import Connections.ConnectionOrcl;
import DAO.DAO;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Exceptions.NonExistantDansLesInfos;
import Interfaces.Controllable;
import Interfaces.Controlleur_Visualisable;
import Presentation.FXML.Gestionaire_Interface;
import Interfaces.Modele;
import POJO.Inventaire;
import POJO.Inventorier;
import Repository.InventaireDAO;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Realiser_Inventaire_Controlleur extends Controlleur_Visualisable<Inventorier> implements Initializable {

    public String isbncourant;
    @FXML
    private TableView<Modele<Inventorier>> TableProgression;
    @FXML
    private TextField IsbnField;
    @FXML
    private TextField QteField;
    @FXML
    private ImageView Couverture;
    @FXML
    private TextField Titre;
    @FXML
    private TextField Auteur;
    @FXML
    private TextField Editeur;
    @FXML
    private TextField Date;
    @FXML
    private TextField NbStock;
    @FXML
    private TextField Quantite;
    @FXML
    private TextField Prix;
    @FXML
    private TextField PrixFnac;
    @FXML
    private Label titreDeFenetre;

    private Inventaire inventaire_courant;

    private String ISBN = "";

    public void setInventaire_courant(Inventaire inventaire_courantt) throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException {
        this.inventaire_courant = inventaire_courantt;
        DAO<Inventaire> D = new InventaireDAO(inventaire_courant);
        inventaire_courant = D.recuperer(inventaire_courant.getNom_inventaire());
        for(String st:inventaire_courant.getRecensement().getkeys()){
            Inventorier I = inventaire_courant.getRecensement().recuperer(st);
            Ajouter_Inventorier_Recensement(I);
        }
        setVisuel();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titreDeFenetre.setText(DashBordController.titreDeFenetre);
    }


    @FXML
    void AjoutBDD(ActionEvent event) {

    }

    void Ajouter_Inventorier_Recensement(Inventorier I) throws IllegalAccessException {
        super.getRecensement().ajouter(I);
        Modele<Inventorier> MM = super.getRecensement().recuperer_modele(I.getReference());
        MM.getProperty("Progression").bind(I.getProgression().asString());
        MM.getProperty("Progression").addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                TableProgression.refresh();
            }
        });
        I.createService().setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                I.createService().reset();
                if (I.getProgression().get() < 3)
                    I.createService().start();
            }
        });
        I.createService().start();
        setVisuel();

    }

    @FXML
    void Ajout_Recensement(ActionEvent event) throws IOException, SQLException, IllegalAccessException {
        Inventorier I = new Inventorier(IsbnField.getText(), Integer.valueOf(QteField.getText()));
        Ajouter_Inventorier_Recensement(I);
        inventaire_courant.ajouter_inventaire(I);
    }

    @FXML
    void Enrichir(ActionEvent event) throws SQLException, BuildingException, NonExistantDansLesInfos, IllegalAccessException {
        isbncourant = TableProgression.getSelectionModel().getSelectedItem().getProperty("ISBN").getValue();
        Inventorier I = recensement.recuperer(isbncourant);
        I.enrichir_BDD();
    }

    @FXML
    void Visualisation(ActionEvent event) {
        isbncourant = TableProgression.getSelectionModel().getSelectedItem().getProperty("ISBN").getValue();
        Afficher_Apercu(isbncourant);
    }

    void Afficher_Apercu(String isbncourant) {
        Inventorier I = recensement.recuperer(isbncourant);
        try {
            I.fetchBDD();
            Titre.setText(I.getDonnesBDD().get("TITRE"));
            Auteur.setText(I.getDonnesBDD().get("AUTEUR"));
            Editeur.setText(I.getDonnesBDD().get("EDITEUR"));
            Date.setText(I.getDonnesBDD().get("DATE_P"));
            Connection C = ConnectionOrcl.getInstance();
            ResultSet Rez = C.prepareStatement("SELECT QTE FROM QTE_PARTITRE WHERE (ISBN='" + I.getIsbn() + "')").executeQuery();
            if (Rez.next()) {
                Quantite.setText(String.valueOf(Rez.getInt(1)));
            } else
                Quantite.setText("0");
            Rez = C.prepareStatement("SELECT COUNT(NOM_S) FROM EST_DANS WHERE (ISBN='" + I.getIsbn() + "') GROUP BY (ISBN)").executeQuery();
            if (Rez.next()) {
                NbStock.setText(String.valueOf(Rez.getInt(1)));
            } else
                NbStock.setText("0");
            Rez = C.prepareStatement("SELECT PRIX_TTC FROM PRODUIT WHERE(ISBN ='" + I.getIsbn() + "')").executeQuery();
            if (Rez.next()) {
                Prix.setText(String.valueOf(Rez.getInt(1)) + " DA");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
        }

        try {
            I.fetchProduitInfo();
            if (I.getRenseignements().containsKey("Prix Fnac"))
                PrixFnac.setText(I.getRenseignements().get("Prix Fnac").replace(" ", "").replace("\u0080", "$"));


        } catch (SQLException e) {
        } catch (NonExistantDansLesInfos nonExistantDansLesInfos) {
        }
    }


    @Override
    public void setVisuel() {
        HashMap<String, TableColumn<Modele<Inventorier>, String>> colonnes;
        colonnes = super.implenter_visuel(TableProgression);
        colonnes.remove("Progression");
        TableColumn<Modele<Inventorier>, String> colonne = new TableColumn<>("Progression bar");
        TableProgression.getColumns().add(colonne);
        colonne.setCellValueFactory(cell -> {
                    Modele<Inventorier> I = cell.getValue();
                    DoubleBinding D = new DoubleBinding() {
                        @Override
                        protected double computeValue() {
                            return Double.valueOf(I.getProperty("Progression").get()) / 3;
                        }
                    };
                    return D.asString();
                }
        );
        colonne.setCellFactory(cell -> {
                    TableCell<Modele<Inventorier>, String> T = new TableCell<>() {
                        ProgressBar B = new ProgressBar();

                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            HBox H = null;
                            if (!empty && item != null) {
                                recensement.recuperer(getTableView().getItems().get(getIndex()).getProperty("ISBN").get()).createService().stateProperty().addListener(new ChangeListener<Worker.State>() {
                                    @Override
                                    public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                                        if (getIndex() == -1)
                                            return;
                                        System.out.println(oldValue + "->" + newValue + " " + "a l'index : " + getIndex());
                                    }
                                });
                                if (Double.valueOf(item) == 1) {
                                    JFXButton BB = new JFXButton("Visualisation");
                                    BB.getStyleClass().add("Soustraire_Additionner");
                                    H = new HBox(BB);
                                    H.setAlignment(Pos.CENTER);
                                    BB.setPrefSize(TableProgression.getColumns().get(3).getWidth(),TableProgression.getFixedCellSize());
                                    BB.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            //I added this !!
                                            try {
                                                Modele<Inventorier> M = getTableView().getItems().get(getIndex());
                                                ISBN = M.getProperty("ISBN").getValue();
                                                Gestionaire_Interface G = new Gestionaire_Interface(new Stage());
                                                Controllable<Inventorier> C = G.switchPanel("Informations_Telechargees.fxml", "Informations Téléchargées");
                                                LinkedList<Inventorier> L = new LinkedList();
                                                L.add(recensement.recuperer(ISBN));
                                                C.RefreshCollection(L);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });
                                } else {
                                    //setGraphic(null);
                                    B.setProgress(Double.valueOf(item));
                                    H = new HBox(B);
                                }
                                setGraphic(H);

                            }

                        }

                    };
            return T;
                }
        );

        VisuelCreator VC=new VisuelCreator();
        TableProgression.setColumnResizePolicy((param) -> true);
        Platform.runLater(() -> VC.customResize(TableProgression));
    }


    @FXML
    private void fermerFenetre() {
        this.fermerFenetre(TableProgression);
    }

    @FXML
    private void Iconify() {
        this.Iconify(TableProgression);
    }

    public void Finalisation() throws SQLException, IllegalAccessException {
        Statement S = ConnectionOrcl.getInstance().createStatement();
        S.executeUpdate("UPDATE INVENTAIRES SET FINALISE='true' WHERE NOM_INVENTAIRE=\'"+inventaire_courant.getNom_inventaire()+"\'");
    }
}
