package Presentation.Controller;

import Client.Repertoire;
import DAO.*;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.Contact;
import POJO.Mail;
import POJO.SiteWeb;
import POJO.TelFax;
import Presentation.FXML.Gestionaire_Interface;
import Presentation.FXML.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;

public class ajoutCoorController extends Gestionnaire_Repertoire {

    @FXML
    private JFXTextField champ1;
    @FXML
    private JFXTextField champ2;
    @FXML
    private JFXTextField champ3;
    @FXML
    private JFXComboBox<String> typeTel;
    @FXML
    private JFXButton valider;

    private long contactNumCourant;
    private Type typeAjout;
    private Stage S;

    @FXML
    private Label titre;


    public void setRenseignements(long contactNumCourant,Type type, Object o){
        valider.setText("Sauvegarder Et Fermer");
        valider.setStyle("-fx-background-color: #FFAB91");
        switch (type){
            case MAIL:{
                openMode(type);
                champ1.setText(((Mail)o).getAdresseMail());
                champ2.setText(((Mail) o).getNomMail());
                champ3.setText(((Mail)o).getTypeC());
                break;
            }
            case TEL:{
                openMode(type);
                typeTel.setValue(((TelFax)o).getTelfax());
                champ1.setText(((TelFax)o).getNumero());
                break;
            }
            case SITEW:{
                openMode(type);
                champ1.setText(((SiteWeb)o).getUrl());
                champ2.setText(((SiteWeb)o).getDescription());
                break;
            }
        }
        valider.setOnAction(event -> {
            switch (type){
                case MAIL:{
                    try {
                        R.modifier_mail(contactNumCourant,(Mail)o,new Mail(champ1.getText(),champ2.getText(),champ3.getText()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case TEL: {
                    try {
                        R.modifier_telFax(contactNumCourant, (TelFax) o, new TelFax(champ1.getText(), typeTel.getValue()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case SITEW:{
                        try {
                            R.modifier_siteWeb(contactNumCourant,(SiteWeb)o,new SiteWeb(champ1.getText(),champ2.getText()));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    break;
                }
            }
            fermerFenetre();
        });
    }

    public void setNumContact(Type typeAjout,long contactNumCourant){
        this.contactNumCourant=contactNumCourant;
        this.typeAjout=typeAjout;
        openMode(typeAjout);
    }

    @FXML
    private void valider() throws IOException {
        try {
            switch (typeAjout){
                case MAIL: {
                    Mail m = new Mail(champ1.getText(), champ2.getText(), champ3.getText());
                    R.ajouter_mail_contact(contactNumCourant, m);
                    break;
                }
                case TEL:{
                    TelFax tf = new TelFax(champ1.getText(), typeTel.getValue());
                    R.ajouter_tel_contact(contactNumCourant, tf);
                    break;
                }
                case SITEW:{
                    SiteWeb sw = new SiteWeb(champ1.getText(), champ2.getText());
                    R.ajouter_Site_contact(contactNumCourant, sw);
                    break;
                }
            }
            fermerFenetre();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void provideTable() throws IllegalAccessException {
    }

    @FXML
    private void fermerFenetre() {
        S=(Stage)champ1.getScene().getWindow();
        reloadPrimaryStage(S);
        this.fermerFenetre(champ1);
    }

    @FXML
    private void iconify() {
        this.Iconify(champ1);
    }

    private void reloadPrimaryStage(Stage S){
        S.setOnHidden(event-> {
            Gestionaire_Interface GUI=new Gestionaire_Interface();
            FXMLLoader loader = new FXMLLoader ( );
            loader.setLocation ( Main.class.getResource ( "GestionContact.fxml" ) );
            Parent root ;
            try {
                root = loader.load();
                Scene scene = new Scene(root);
                GestionContactController.primaryStage.setScene(scene);
                GUI.moveStage(GestionContactController.primaryStage,root);
                GestionContactController.primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void openMode(Type type){
        switch (type){
            case MAIL:{
                titre.setText("Ajouter Un Mail");
                break;
            }
            case TEL:{
                titre.setText("Ajouter Un Tel");
                typeTel.setItems(FXCollections.observableArrayList("Tel","Fax"));
                champ1.setPromptText("Numéro");
                champ2.setVisible(false);
                champ3.setVisible(false);
                typeTel.setVisible(true);
                break;
            }
            case SITEW:{
                titre.setText("Ajouter Site Web");
                champ3.setVisible(false);
                champ1.setPromptText("Url");
                champ2.setPromptText("Description");
                break;
            }
        }
    }

}
