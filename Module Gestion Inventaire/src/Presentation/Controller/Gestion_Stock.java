package Presentation.Controller;


import Connections.ConnectionOrcl;
import DAO.DAO;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.Controlleur_Visualisable;
import Presentation.FXML.Gestionaire_Interface;

import POJO.*;
import DAO.StockDAO;
import Presentation.FXML.Main2;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;

public class Gestion_Stock extends Controlleur_Visualisable<Livre> {

    public JFXSpinner Spinner;
    @FXML
    private JFXComboBox<String> ChoixStockComboBox;

    @FXML
    private TextField NomStockCree;

    @FXML
    private TextField LocalisationStockCree;

    @FXML
    private ComboBox<String> InventaireUtilise;

    @FXML
    private CheckBox CompletionCheckbox;

    @FXML
    private CheckBox PrixCheckbox;


    @FXML
    ImageView icon;

    private HashMap<String,Integer> quantite = new HashMap<>();
    private boolean maximize=false;
    Gestionaire_Interface GUI=new Gestionaire_Interface();
    private Stage PS = new Stage();
    private boolean visuel_ready = false;
    private Service<Void> chargement_en_cours;


    public Service<Void> chargement_bdd_task(String nom){
        return  new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                Task<Void> T = new Task<Void>(){
                    @Override
                    protected Void call() {
                        System.out.println("Début du chargement");
                        PS.initStyle(StageStyle.TRANSPARENT);
                        FXMLLoader loader = new FXMLLoader ( );
                        loader.setLocation ( Main2.class.getResource ( "Visuel_Stock.fxml" ) );
                        Parent root = null;
                        try {
                            root = loader.load ( );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        GUI.moveStage(PS,root);
                        Controller_Visuel_Stock FOP = loader.getController ( );
                        Scene scene = new Scene(root);
                        scene.setFill(Color.TRANSPARENT);
                        try {
                            FOP.setNom_stock(nom);
                        } catch (SQLException | BuildingException | NonExistantDansLaBDD | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        try {
                            PS.setScene(scene);
                        }catch(Exception e){
                            System.out.println(e.getMessage());
                        }
                        Spinner.setVisible(false);
                        System.out.println("Fin du chargement");

                        return null;
                    }
                };
                T.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        visuel_ready = true;
                    }
                });
                T.setOnFailed(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        reset();
                    }
                });
                return T;

            }
        };
    }



    @FXML
    void initialize() throws SQLException{
        Statement S = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS = S.executeQuery("SELECT  NOM_S FROM STOCK");
        ChoixStockComboBox.getItems().clear();
        while (RS.next()){
            ChoixStockComboBox.getItems().add(RS.getString(1));
        }
        RS = S.executeQuery("SELECT NOM_INVENTAIRE FROM INVENTAIRES WHERE FINALISE='true'");
        while(RS.next()){
            InventaireUtilise.getItems().add(RS.getString(1));
        }
    }

    @FXML
    void AdditionnerButton(ActionEvent event) throws SQLException, IOException {
        Stage PS = new Stage();
        PS.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader ( );
        loader.setLocation ( Main2.class.getResource ( "Addition_Panel.fxml" ) );
        Parent root = loader.load ( );
        GUI.moveStage(PS,root);
        Correction_Panel FOP = loader.getController ( );
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        FOP.setStockEtInventaire(ChoixStockComboBox.getValue(),InventaireUtilise.getValue());
        PS.setScene(scene);
        PS.show ( );
    }




    @FXML
    void CreerAction(ActionEvent event) throws SQLException, IllegalAccessException {
        Stock S = new Stock();
        S.setNom(NomStockCree.getText());
        S.setLocalisation(LocalisationStockCree.getText());
        S.setDate_realisation(Calendar.getInstance().getTime());
        DAO<Stock> DS = new StockDAO(new Stock());
        DS.ajouter(S);
    }


    @FXML
    void SoustraireButton(ActionEvent event) throws SQLException, IOException {
        Stage PS = new Stage();
        PS.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader ( );
        loader.setLocation ( Main2.class.getResource ( "Soustraction_Panel.fxml" ) );
        Parent root = loader.load ( );
        GUI.moveStage(PS,root);
        Correction_Panel FOP = loader.getController ( );
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        FOP.setStockEtInventaire(ChoixStockComboBox.getValue(),InventaireUtilise.getValue());
        PS.setScene(scene);
        PS.show ( );
    }

    @FXML
    void TransfererButton(ActionEvent event) throws IOException, SQLException {

    }

    @FXML
    void VerifierButton(ActionEvent event) throws SQLException, IOException {
        Stage PS = new Stage();
        PS.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader ( );
        loader.setLocation ( Main2.class.getResource ( "Verification_Panel.fxml" ) );
        Parent root = loader.load ( );
        Verification_Panel_Controller FOP = loader.getController ( );
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        GUI.moveStage(PS,root);
        FOP.setStockEtInventaire(ChoixStockComboBox.getValue(),InventaireUtilise.getValue());
        PS.setScene(scene);
        PS.show ( );
    }

    @Override
    public void setVisuel() {

    }

    @FXML
    private void fermerFenetre() {
        //this.fermerFenetre(VisualisationStock);
    }

    @FXML
    private void Iconify() {
        //this.Iconify(VisualisationStock);
    }

    @FXML
    private void Maximize(ActionEvent e) {
        /*//Stage stage = (Stage) VisualisationStock.getScene().getWindow();
        if (!maximize) {
            icon.setImage(new Image("Presentation/Assets/Maximize.png"));
            stage.setMaximized(true);
            maximize=true;
        }
        else {
            icon.setImage(new Image("Presentation/Assets/Minimize.png"));
            stage.setMaximized(false);
            maximize=false;
        }*/
    }

    public void AjouterStockButton() {

    }

    public void ChoixStock(ActionEvent actionEvent) {
        visuel_ready = false;
        chargement_en_cours = chargement_bdd_task(ChoixStockComboBox.getValue());
        chargement_en_cours.start();
    }

    public void Gerer_Stock_Action(ActionEvent actionEvent) throws IOException, SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD {
        if(visuel_ready)
            PS.show();
        else
        {
            chargement_en_cours.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    PS.show();
                }
            });
            if(!chargement_en_cours.isRunning())
                chargement_en_cours.start();
        }
    }

    public void Supprimer_Stock(ActionEvent actionEvent) {

    }


}