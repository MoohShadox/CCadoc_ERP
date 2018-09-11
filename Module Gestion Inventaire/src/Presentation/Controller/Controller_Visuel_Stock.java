package Presentation.Controller;

import Composants_Visuels.VisuelCreator;
import Connections.ConnectionOrcl;
import DAO.DAO;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Exceptions.NonExistantDansLesInfos;
import Interfaces.Controller;
import Interfaces.Controlleur_Visualisable;
import Interfaces.Modele;
import POJO.Inventorier;
import POJO.Livre;
import POJO.Stock;
import Presentation.FXML.Gestionaire_Interface;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import DAO.DAOLivre;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;

public class Controller_Visuel_Stock extends Controlleur_Visualisable<Livre> {

    @FXML
    private TableView<Modele<Livre>> VisualisationStock;

    @FXML
    private JFXTextField ISBNAjout;

    @FXML
    private JFXTextField QteAjouter;

    private HashMap<String,Integer> quantite = new HashMap<>();

    private String nom_stock;
    private boolean maximize=false;
    Gestionaire_Interface GUI=new Gestionaire_Interface();

    public void affichage_stock(String nom) throws IllegalAccessException, SQLException, NonExistantDansLaBDD, BuildingException {
        DAO<Livre> D = new DAOLivre(new Livre());
        Statement S = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS = S.executeQuery("SELECT ISBN,QUANTITE FROM EST_DANS WHERE NOM_S=\'"+nom+"\'");
        LinkedList<Livre> CL = new LinkedList<>();
        while (RS.next()){
            CL.add(D.recuperer(RS.getString(1)));
            quantite.put(RS.getString(1),RS.getInt(2));
        }
        for(Livre LL:CL){
            super.recensement.ajouter(LL);
        }
        RS.close();
        S.close();
        setVisuel();
    }

    @FXML
    void initialize() throws SQLException {

    }


    @FXML
    void AjoutPlusieursTitres(ActionEvent event) {

    }

    @FXML
    void AjouterStockButton(ActionEvent event) throws SQLException, IllegalAccessException, IOException {
        DAO<Livre> DL = new DAOLivre(new Livre());
        String isbn = ISBNAjout.getText();
        int qte = Integer.valueOf(QteAjouter.getText());
        try {
            Livre L = DL.recuperer(isbn);
            Statement S = ConnectionOrcl.getInstance().createStatement();
            ResultSet RS = S.executeQuery("SELECT * FROM EST_DANS WHERE ISBN=\'"+isbn+"\' AND NOM_S=\'"+nom_stock+"\'");
            if(!RS.next()) {
                PreparedStatement PS = ConnectionOrcl.getInstance().prepareStatement("INSERT INTO EST_DANS(NOM_S,ISBN,QUANTITE) VALUES (?,?,?)");
                PS.setString(1, nom_stock);
                PS.setString(2, isbn);
                PS.setInt(3, qte);
                try
                {
                    PS.executeUpdate();
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
                PS.close();
                RS.close();
            }
            else{
                //TODO même alerte que plus bas
                MessageBox a=new MessageBox();
                a.display("Alerte !","Interdiction d'ajout du même élément deux fois ","Fermer");
            }
        } catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
            Inventorier I = new Inventorier(isbn,qte);
            Controller_Visuel_Stock G = this;
            I.createService().setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    try {
                        System.out.println("Handle lancé");
                        I.enrichir_BDD();
                        Statement S = ConnectionOrcl.getInstance().createStatement();
                        ResultSet RS = S.executeQuery("SELECT * FROM EST_DANS WHERE ISBN=\'"+isbn+"\' AND NOM_S=\'"+nom_stock+"\'");
                        if(!RS.next()){
                            PreparedStatement PS = ConnectionOrcl.getInstance().prepareStatement("INSERT INTO EST_DANS(NOM_S,ISBN,QUANTITE) VALUES (?,?,?)");
                            PS.setString(1,nom_stock);
                            PS.setString(2,isbn);
                            PS.setInt(3,qte);
                            PS.executeUpdate();
                            G.recensement.vider();
                            G.affichage_stock(nom_stock);
                        }
                        else{
                            //TODO Mettre une allerte qui interdis deux fois l'ajout du même élément
                            MessageBox a=new MessageBox();
                            a.display("Alerte !","Interdiction d'ajout du même élément deux fois ","Fermer");
                        }
                        RS.close();
                        S.close();
                    } catch (SQLException | NonExistantDansLesInfos | BuildingException | IllegalAccessException | NonExistantDansLaBDD e) {
                        e.printStackTrace();
                    }
                }
            });
            I.createService().start();
        } catch (BuildingException e) {
            e.printStackTrace();
        }
        super.recensement.vider();
        try {
            affichage_stock(nom_stock);
        } catch (NonExistantDansLaBDD | BuildingException nonExistantDansLaBDD) {
            nonExistantDansLaBDD.printStackTrace();
        }
    }



    @Override
    public void setVisuel() {
        super.implenter_visuel(VisualisationStock);
        VisualisationStock.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        TableColumn<Modele<Livre>,String> T = new TableColumn("Quantité");
        T.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Modele<Livre>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Modele<Livre>, String> param) {

                return new SimpleStringProperty(quantite.get(param.getValue().getProperty("ISBN").getValue())+"");
            }
        });
        VisualisationStock.getColumns().add(T);
        VisuelCreator VC=new VisuelCreator();
        VisualisationStock.setColumnResizePolicy((param) -> true);
        Platform.runLater(() ->VC.customResize(VisualisationStock));
    }

    public void setNom_stock(String nom_stock) throws SQLException, BuildingException, NonExistantDansLaBDD, IllegalAccessException {
        this.nom_stock = nom_stock;
        setVisuel();
        affichage_stock(nom_stock);

    }

     @FXML
     void Supprimer_Selection(ActionEvent actionEvent) {
    }

    @FXML
    private void fermerFenetre() {
        DashBordController DB=new DashBordController();
        Stage s = (Stage) DB.acceuilPane.getScene().getWindow();
        s.show();
        this.fermerFenetre(VisualisationStock);
    }

    @FXML
    private void Iconify() {
        this.Iconify(VisualisationStock);
    }

}