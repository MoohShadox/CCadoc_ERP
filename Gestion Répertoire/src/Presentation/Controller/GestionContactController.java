package Presentation.Controller;

import Client.Repertoire;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.Contact;
import POJO.Mail;
import POJO.SiteWeb;
import POJO.TelFax;
import Presentation.FXML.Gestionaire_Interface;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GestionContactController extends Gestionnaire_Repertoire implements Initializable {

    @FXML
    private ListView<String> ListMail = new ListView<>();
    @FXML
    private ListView<String> ListSiteWeb = new ListView<>();
    @FXML
    private ListView<String> ListTel = new ListView<>();
    @FXML
    private JFXTextField denomination;
    @FXML
    private JFXTextField adresse;
    @FXML
    private JFXTextField type;

    public static Contact contactCourant;
    public static Stage primaryStage;


    @Override
    public void provideTable() throws IllegalAccessException {
    }

    @FXML
    private void fermerFenetre() {
        this.fermerFenetre(denomination);
    }

    @FXML
    private void iconify() {
        this.Iconify(denomination);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        denomination.setText(contactCourant.getDenomnation());
        adresse.setText(contactCourant.getAdresse());
        type.setText(contactCourant.getType());
        Platform.runLater(() -> ListMail.setItems(remplissageListMail(contactCourant)));
        Platform.runLater(() -> ListTel.setItems(remplissageListTelFax(contactCourant)));
        Platform.runLater(() -> ListSiteWeb.setItems(remplissageListSiteWeb(contactCourant)));

        denomination.setOnAction(event -> {
            if (!contactCourant.getDenomnation().equals(denomination.getText())) {
                try {
                    contactCourant.setDenomnation(denomination.getText());
                    R.getContacts().get(contactCourant.getNumContact()).getSrc().setDenomnation(denomination.getText());
                    R.getContacts().get(contactCourant.getNumContact()).modifierProperty(R.getContacts().get(contactCourant.getNumContact()).getDescription().get("DENOMINATION"), denomination.getText());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        adresse.setOnAction(event -> {
            if (!contactCourant.getAdresse().equals(adresse.getText())) {
                try {
                    contactCourant.setAdresse(adresse.getText());
                    R.getContacts().get(contactCourant.getNumContact()).getSrc().setAdresse(adresse.getText());
                    R.getContacts().get(contactCourant.getNumContact()).modifierProperty(R.getContacts().get(contactCourant.getNumContact()).getDescription().get("ADRESSE"), adresse.getText());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        type.setOnAction(event -> {
            if (!contactCourant.getType().equals(type.getText())) {
                try {
                    contactCourant.setType(type.getText());
                    R.getContacts().get(contactCourant.getNumContact()).getSrc().setType(type.getText());
                    R.getContacts().get(contactCourant.getNumContact()).modifierProperty(R.getContacts().get(contactCourant.getNumContact()).getDescription().get("TYPE"), type.getText());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        ListMail.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                Gestionaire_Interface G = new Gestionaire_Interface ( new Stage() );
                Gestionnaire_Repertoire C ;
                try {
                    primaryStage=(Stage)denomination.getScene().getWindow();
                    C = G.switchPanel("ajoutCoor.fxml", "Ajout De Coordonnées");
                    Mail m = (Mail) getObject(contactCourant.getMails(),ListMail.getSelectionModel().getSelectedItem());
                    ((ajoutCoorController) C).setRenseignements(contactCourant.getNumContact(),Type.MAIL,m);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ListTel.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                Gestionaire_Interface G = new Gestionaire_Interface ( new Stage() );
                Gestionnaire_Repertoire C ;
                try {
                    primaryStage=(Stage)denomination.getScene().getWindow();
                    C = G.switchPanel("ajoutCoor.fxml", "Ajout De Coordonnées");
                    TelFax tf = (TelFax) getObject(contactCourant.getTels(),ListTel.getSelectionModel().getSelectedItem());
                    ((ajoutCoorController) C).setRenseignements(contactCourant.getNumContact(),Type.TEL,tf);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ListSiteWeb.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                Gestionaire_Interface G = new Gestionaire_Interface ( new Stage() );
                Gestionnaire_Repertoire C ;
                try {
                    primaryStage=(Stage)denomination.getScene().getWindow();
                    C = G.switchPanel("ajoutCoor.fxml", "Ajout De Coordonnées");
                    SiteWeb sw = (SiteWeb) getObject(contactCourant.getSites(),ListSiteWeb.getSelectionModel().getSelectedItem());
                    ((ajoutCoorController) C).setRenseignements(contactCourant.getNumContact(),Type.SITEW,sw);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void ajoutMail() {
        Gestionaire_Interface G = new Gestionaire_Interface ( new Stage() );
        Gestionnaire_Repertoire C ;
        try {
            C = G.switchPanel("ajoutCoor.fxml", "Ajout De Coordonnées");
            ((ajoutCoorController) C).setNumContact(Type.MAIL,contactCourant.getNumContact());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ajoutTel() {
        Gestionaire_Interface G = new Gestionaire_Interface ( new Stage() );
        Gestionnaire_Repertoire C ;
        try {
            C = G.switchPanel("ajoutCoor.fxml", "Ajout De Coordonnées");
            ((ajoutCoorController) C).setNumContact(Type.TEL,contactCourant.getNumContact());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ajoutSiteW() {
        Gestionaire_Interface G = new Gestionaire_Interface ( new Stage() );
        Gestionnaire_Repertoire C ;
        try {
            C = G.switchPanel("ajoutCoor.fxml", "Ajout De Coordonnées");
            ((ajoutCoorController) C).setNumContact(Type.SITEW,contactCourant.getNumContact());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimerMail() throws IllegalAccessException {
        Mail m = (Mail) getObject(contactCourant.getMails(),ListMail.getSelectionModel().getSelectedItem());
        R.supprimer_Mail_Contact(contactCourant.getNumContact(),m);
        ListMail.getItems().remove(m.getAdresseMail());
    }

    @FXML
    private void supprimerTel() throws IllegalAccessException {
        TelFax tf = (TelFax) getObject(contactCourant.getTels(),ListTel.getSelectionModel().getSelectedItem());
        R.supprimer_TelFax_Contact(contactCourant.getNumContact(),tf);
        ListTel.getItems().remove(tf.getNumero());
    }

    @FXML
    private void supprimerSiteW() throws IllegalAccessException {
        SiteWeb sw = (SiteWeb) getObject(contactCourant.getSites(),ListSiteWeb.getSelectionModel().getSelectedItem());
        R.supprimer_SiteWeb_Contact(contactCourant.getNumContact(),sw);
        ListSiteWeb.getItems().remove(sw.getUrl());
    }

}
