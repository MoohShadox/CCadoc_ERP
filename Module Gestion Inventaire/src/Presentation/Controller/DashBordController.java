package Presentation.Controller;

import Connections.ConnectionOrcl;
import DAO.*;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.Controller;
import Presentation.FXML.Gestionaire_Interface;
import Interfaces.Modele;
import POJO.*;
import Presentation.FXML.Main2;
import Repository.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class DashBordController extends Controller<Inventorier, Modele<Inventorier>>  {


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

    private HashMap<String,Integer> quantite = new HashMap<>();
    private Stage PS = new Stage();
    private boolean visuel_ready = false;
    private Service<Void> chargement_en_cours;

    public void ChoixStock(ActionEvent actionEvent) {
        visuel_ready = false;
        chargement_en_cours = chargement_bdd_task(ChoixStockComboBox.getValue());
        chargement_en_cours.start();
    }

    public void AjouterStockButton(ActionEvent actionEvent) {
    }

    public void AjoutPlusieursTitres(ActionEvent actionEvent) {
    }

    public void VerifierButton(ActionEvent actionEvent) throws IOException, SQLException{
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

    public void AdditionnerButton(ActionEvent actionEvent) throws IOException, SQLException{
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

    public void TransfererButton(ActionEvent actionEvent) {
    }

    public void SoustraireButton(ActionEvent actionEvent) throws IOException, SQLException {
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

    public void CreerAction(ActionEvent event) throws SQLException, IllegalAccessException {
        Stock S = new Stock();
        S.setNom(NomStockCree.getText());
        S.setLocalisation(LocalisationStockCree.getText());
        S.setDate_realisation(Calendar.getInstance().getTime());
        DAO<Stock> DS = new StockDAO(new Stock());
        DS.ajouter(S);
    }

    //CORRECTIFS ERREURS 

    class InventaireVisuel extends VBox{
        private Inventaire Inventaire;
        public void setInventaire(Inventaire i) {
            Inventaire = i;
        }

        public Inventaire getInventaire() {
            return Inventaire;
        }
    }

    @FXML
    JFXButton acceuil;
    @FXML
    JFXButton visualiserLeStock;
    @FXML
    JFXButton realiserUnInventaire;
    @FXML
    JFXButton statistique;
    @FXML
    JFXButton aPropos;
    @FXML
    JFXButton notification;
    @FXML
    JFXButton parametres;
    @FXML
    JFXButton rechercher;
    @FXML
    JFXButton suppr;
    @FXML
    ComboBox choixDuStock1;
    @FXML
    ComboBox choixDuStock2;
    @FXML
    JFXTextField codeISBN;
    @FXML
    Pane acceuilPane;
    @FXML
    Pane stockPane;
    @FXML
    Pane inventairePane;
    @FXML
    Pane aProposPane;
    @FXML
    Pane statsPane;
    @FXML
    ImageView icon;

    private boolean supprimerButtonOn=false;

    private boolean maximize=false;

    public final int nb_objets_ligne = 3;

    @FXML
    public Pane Vbox1;
    @FXML
    public AnchorPane Conteneur;

    public LinkedList<HBox> Liste_Lignes = new LinkedList<>();

    public HBox box_courant = null;

    public static String titreDeFenetre = new String();

    public static boolean supprimer=false;

    Gestionaire_Interface GUI=new Gestionaire_Interface();

    public EventHandler<MouseEvent> getSupprimerHandler(InventaireVisuel IV)
    {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //TODO ajouter une Vérification avant la suppression
                    MessageBox a=new MessageBox();
                    a.display("Confirmation !","Voulez-vous continuer ?","Confirmer");
                    if(supprimer) {
                        Statement S = ConnectionOrcl.getInstance().createStatement();
                        S.executeUpdate("DELETE EST_INVENTORIER WHERE INVENTAIRE=\'" + IV.getInventaire().getNom_inventaire() + "\'");
                        S.executeUpdate("DELETE FROM INVENTAIRES WHERE NOM_INVENTAIRE=\'" + IV.getInventaire().getNom_inventaire() + "\'");
                        clean_interface();
                        load_interface();
                        supprimer=false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
                    nonExistantDansLaBDD.printStackTrace();
                } catch (BuildingException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public EventHandler<MouseEvent> getLoadHandler(InventaireVisuel inventaire)

    {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Stage PS = new Stage();
                    if(inventaire.getInventaire().getFinalise())
                    {
                        //TODO créer une alerte qui te dit qu'on peut pas modifier un inventaire finalisé
                        MessageBox a=new MessageBox();
                        a.display("Alerte !","Vous ne pouvez pas modifier un inventaire finalisé !","Fermer");
                    }else{
                        PS.initStyle(StageStyle.TRANSPARENT);
                        FXMLLoader loader = new FXMLLoader ( );
                        loader.setLocation ( Main2.class.getResource ( "Realiser_Inventaire.fxml" ) );
                        Parent root = loader.load ( );
                        GUI.moveStage(PS,root);
                        Realiser_Inventaire_Controlleur FOP = loader.getController ( );
                        titreDeFenetre = "Gestion de l'inventaire  : " + inventaire.getInventaire().getNom_inventaire();
                        Scene scene = new Scene(root);
                        scene.setFill(Color.TRANSPARENT);
                        PS.setScene(scene);
                        //PS.setMaximized ( true );
                        PS.show ( );
                        FOP.setInventaire_courant(inventaire.getInventaire());
                    }} catch (IOException | BuildingException | SQLException | NonExistantDansLaBDD | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    @FXML
    private void handleButtonAction(ActionEvent e) {
        if (e.getSource() == acceuil)
            acceuilPane.toFront();
        else if (e.getSource() == visualiserLeStock)
        {
            stockPane.toFront();
        }
        else if (e.getSource() == realiserUnInventaire)
            inventairePane.toFront();
        else if (e.getSource() == statistique)
            statsPane.toFront();
        else aProposPane.toFront();
    }

    @FXML
    void initialize() throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException {
        clean_interface();
        load_interface();
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


    public VBox generer_Definition(Inventaire inventaire) {
        SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
        InventaireVisuel PaneTest = new InventaireVisuel();
        PaneTest.setAlignment(Pos.TOP_LEFT);
        Label L = new Label();
        Label L2 = new Label();
        Label L3 = new Label("Nombre d'ouvrages : " + inventaire.getNombre_total_ouvrages());
        L.setText("Nom : " + inventaire.getNom_inventaire());
        L2.setText("Date de création  : " + SDF.format(inventaire.getDate_realisation()));
        PaneTest.getChildren().addAll(L, L3, L2);
        PaneTest.setInventaire(inventaire);
        PaneTest.setOnMouseClicked(getLoadHandler(PaneTest));
        PaneTest.getStyleClass().add("Vbox");
        L.getStyleClass().add("Label");
        L2.getStyleClass().add("Label");
        L3.getStyleClass().add("Label");
        PaneTest.setVgrow(L,Priority.ALWAYS);
        PaneTest.setVgrow(L2,Priority.ALWAYS);
        PaneTest.setVgrow(L3,Priority.ALWAYS);
        if(inventaire.getFinalise())
        {
            //TODO Colorer les Vbox des inventaire finalisés
            PaneTest.setStyle("-fx-border-color:  #FFC619");
        }

        return PaneTest;
    }

    void ajouter_interface(Inventaire I) {
        if (box_courant != null && box_courant.getChildren().size() < nb_objets_ligne) {
            box_courant.getChildren().add(generer_Definition(I));
        } else {
            HBox H = new HBox();
            H.setSpacing(15);
            Liste_Lignes.add(H);
            box_courant = H;
            Vbox1.getChildren().add(H);
            box_courant.getChildren().add(generer_Definition(I));
        }
    }


    @Override
    public void RefreshCollection(Collection<Inventorier> T) {

    }


    public void Ajouter(ActionEvent actionEvent) throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException, IOException {
        Stage PS = new Stage();
        PS.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader ( );
        loader.setLocation ( Main2.class.getResource ( "Creer_Inventaire.fxml" ) );
        Parent root = loader.load ( );
        GUI.moveStage(PS,root);
        CreerInventaire FOP = loader.getController ( );
        FOP.setDB(this);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        PS.setScene(scene);
        PS.show ( );
    }



    public void load_interface() throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException {
        Statement S = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS = S.executeQuery("SELECT NOM_INVENTAIRE FROM INVENTAIRES ");
        DAO<Inventaire> D = new InventaireDAO(new Inventaire(""));
        Inventaire I = new Inventaire("");
        while (RS.next()) {
            D = new InventaireDAO(new Inventaire(""));

            I = D.recuperer(RS.getString(1));
            ajouter_interface(I);
        }

    }


    public void clean_interface(){
        for(HBox H:Liste_Lignes){
            H.getChildren().clear();
        }
    }


    public void Supprimer(ActionEvent actionEvent) throws SQLException, BuildingException, NonExistantDansLaBDD, IllegalAccessException {
        if(!supprimerButtonOn){
            for(HBox H:Liste_Lignes){
                for(Node N : H.getChildren()){
                    N.setOnMouseClicked(getSupprimerHandler((InventaireVisuel) N));
                    //TODO Changer la coloration pour colorer tout les carrés en rouge ce qui veut dire que le prochain sur lequel on va cliquer serra supprimer
                    N.setStyle("-fx-border-color: #E53935");
                    suppr.setStyle("-fx-background-color: #E53935");
                }
            }
            supprimerButtonOn=true;
        }
        else{
            suppr.setStyle("-fx-background-color:  #FFC619");
            for (HBox H : Liste_Lignes) {
                for (Node N : H.getChildren()) {
                    N.setOnMouseClicked(getLoadHandler((InventaireVisuel) N));
                    N.setStyle("-fx-border-color: #f8f6ca");
                }
            }
            supprimerButtonOn=false;
        }
    }

    @FXML
    private void fermerFenetre() {
        this.fermerFenetre(acceuilPane);
    }

    @FXML
    private void Iconify() {
        this.Iconify(acceuilPane);
    }

    @FXML
    private void Maximize(ActionEvent e) {
        Stage stage = (Stage) acceuilPane.getScene().getWindow();
        if (!maximize) {
            icon.setImage(new Image("Presentation/Assets/Maximize.png"));
            stage.setMaximized(true);
            maximize=true;
        }
        else {
            icon.setImage(new Image("Presentation/Assets/Minimize.png"));
            stage.setMaximized(false);
            maximize=false;
        }
    }

    //GestionStockController :

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
    public void Gerer_Stock_Action(ActionEvent actionEvent) throws IOException, SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD {
        if(visuel_ready) {
            Stage s= (Stage) acceuilPane.getScene().getWindow();
            s.hide();
            PS.show();
        }
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

