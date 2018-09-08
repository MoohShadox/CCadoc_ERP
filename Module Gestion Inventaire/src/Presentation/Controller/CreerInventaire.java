package Presentation.Controller;

import Connections.ConnectionOrcl;
import DAO.DAO;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.Controller;
import Interfaces.Modele;
import POJO.Inventaire;
import Repository.InventaireDAO;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;

public class CreerInventaire extends Controller<Inventaire, Modele<Inventaire>> {

    @FXML
    private TextField ChampNom;

    @FXML
    private JFXButton BoutonCreer;

    private DashBordController DB;

    public void setDB(DashBordController DB) {
        this.DB = DB;
    }

    @FXML
    void initialize() throws SQLException, IllegalAccessException {
        DAO<Inventaire> D = new InventaireDAO(new Inventaire(""));
    }

    @FXML
    void MAJChamp(KeyEvent event) throws SQLException {
        KeyEvent k = event;
        Statement S = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS = S.executeQuery("SELECT NOM_INVENTAIRE FROM INVENTAIRES WHERE NOM_INVENTAIRE=\'"+ChampNom.getText()+"\'");
        EventHandler<ActionEvent> ajout = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Inventaire I = new Inventaire(ChampNom.getText());
                    DAO<Inventaire> D = new InventaireDAO(new Inventaire(ChampNom.getText()));
                    I.setDate_realisation(Calendar.getInstance().getTime());
                    D.ajouter(I);
                    MAJChamp(k);
                } catch (SQLException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        };
        if(RS.next()){
            ChampNom.setStyle("-fx-border-color:  #A5D6A7");
            BoutonCreer.setStyle("-fx-background-color:  #A5D6A7");
               BoutonCreer.setText("Modifier");
               BoutonCreer.setOnAction(new EventHandler<ActionEvent>() {
                   @Override
                   public void handle(ActionEvent event) {
                       BoutonCreer.setText("Valider");
                       String prec = ChampNom.getText();
                       BoutonCreer.setOnAction(new EventHandler<ActionEvent>() {
                           @Override
                           public void handle(ActionEvent event) {
                               String nouv = ChampNom.getText();
                               try {
                                   modifier_inventaire(prec,nouv);
                                   BoutonCreer.setOnAction(ajout);
                                   BoutonCreer.setText("Creer");
                                   ChampNom.setStyle("-fx-border-color:  #FFC619");
                                   BoutonCreer.setStyle("-fx-background-color:  #FFC619");
                                   ChampNom.setStyle("");
                                   MAJChamp(k);
                               } catch (SQLException | BuildingException | NonExistantDansLaBDD | IllegalAccessException e) {
                                   e.printStackTrace();
                               }
                           }
                       });
                   }
               });
        }
        else
        {
            if(BoutonCreer.getText().equalsIgnoreCase("Valider"))
                return;
            BoutonCreer.setText("Creer");
            ChampNom.setStyle("-fx-border-color:  #FFC619");
            BoutonCreer.setStyle("-fx-background-color:  #FFC619");
            BoutonCreer.setOnAction(ajout);
        }

    }

    @Override
    public void RefreshCollection(Collection<Inventaire> T) {

    }

    public void modifier_inventaire(String prec,String nouv) throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException {
        DAO<Inventaire> D = new InventaireDAO(new Inventaire(""));
        Inventaire I = D.recuperer(prec);
        I.setNom_inventaire(nouv);
        D.ajouter(I);
        Statement S = ConnectionOrcl.getInstance().createStatement();
        S.executeUpdate("UPDATE EST_INVENTORIER SET INVENTAIRE=\'"+nouv+"\' WHERE INVENTAIRE=\'"+prec+"\'");
        S.executeUpdate("DELETE FROM INVENTAIRES WHERE NOM_INVENTAIRE = \'"+prec+"\'");

    }


    @FXML
    private void fermerFenetre(ActionEvent e) throws SQLException, BuildingException, NonExistantDansLaBDD, IllegalAccessException {
        Stage stage = (Stage) BoutonCreer.getScene().getWindow();
        DB.clean_interface();
        DB.load_interface();
        stage.close();
    }

}
