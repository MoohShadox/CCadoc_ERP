package Presentation.Controller;

import Client.Repertoire;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.Contact;
import POJO.Mail;
import POJO.SiteWeb;
import POJO.TelFax;
import Presentation.FXML.Gestionaire_Interface;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    @FXML
    private JFXButton supprMail;
    @FXML
    private JFXButton supprTel;
    @FXML
    private JFXButton supprSiteW;

    private boolean supprimerOnMail = false;
    private boolean supprimerOnTel = false;
    private boolean supprimerOnSiteW = false;

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
        ListMail.getSelectionModel().getSelectedItems().addListener(selectionModelMail(0));
        ListMail.getSelectionModel().getSelectedItems().addListener(selectionModelMail(1));
        ListTel.getSelectionModel().getSelectedItems().addListener(selectionModelTel(0));
        ListTel.getSelectionModel().getSelectedItems().addListener(selectionModelTel(1));
        ListSiteWeb.getSelectionModel().getSelectedItems().addListener(selectionModelSiteW(0));
        ListSiteWeb.getSelectionModel().getSelectedItems().addListener(selectionModelSiteW(1));

    }

    @FXML
    private void ajoutMail() {
        primaryStage = (Stage) denomination.getScene().getWindow();
        Gestionaire_Interface G = new Gestionaire_Interface(new Stage());
        Gestionnaire_Repertoire C;
        try {
            C = G.switchPanel("ajoutCoor.fxml", "Ajout De Coordonnées");
            ((ajoutCoorController) C).setNumContact(Type.MAIL, contactCourant.getNumContact());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ajoutTel() {
        primaryStage = (Stage) denomination.getScene().getWindow();
        Gestionaire_Interface G = new Gestionaire_Interface(new Stage());
        Gestionnaire_Repertoire C;
        try {
            C = G.switchPanel("ajoutCoor.fxml", "Ajout De Coordonnées");
            ((ajoutCoorController) C).setNumContact(Type.TEL, contactCourant.getNumContact());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ajoutSiteW() {
        primaryStage = (Stage) denomination.getScene().getWindow();
        Gestionaire_Interface G = new Gestionaire_Interface(new Stage());
        Gestionnaire_Repertoire C;
        try {
            C = G.switchPanel("ajoutCoor.fxml", "Ajout De Coordonnées");
            ((ajoutCoorController) C).setNumContact(Type.SITEW, contactCourant.getNumContact());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimerMail() {
        if (!supprimerOnMail) {
            supprMail.setStyle("-fx-background-color: #E53935");
            colorChanging(ListMail, "#E53935");
            supprimerOnMail = true;
        } else {
            colorChanging(ListMail, "#9E9E9E");
            supprMail.setStyle("-fx-background-color: #FFAB91;");
            supprimerOnMail = false;
        }
    }

    @FXML
    private void supprimerTel() throws IllegalAccessException {
        if (!supprimerOnTel) {
            supprTel.setStyle("-fx-background-color: #E53935");
            colorChanging(ListTel, "#E53935");
            supprimerOnTel = true;
        } else {
            colorChanging(ListTel, "#9E9E9E");
            supprTel.setStyle("-fx-background-color: #FFAB91;");
            supprimerOnTel = false;
        }
    }

    @FXML
    private void supprimerSiteW() throws IllegalAccessException {
        if (!supprimerOnSiteW) {
            supprSiteW.setStyle("-fx-background-color: #E53935");
            colorChanging(ListSiteWeb, "#E53935");
            supprimerOnSiteW = true;
        } else {
            colorChanging(ListSiteWeb, "#9E9E9E");
            supprSiteW.setStyle("-fx-background-color: #FFAB91;");
            supprimerOnSiteW = false;
        }
    }

    private void colorChanging(ListView<String> List, String color) {
        List.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setStyle("-fx-border-color: " + color);
                        setText(item);

                    }
                };
            }
        });
    }

    private ListChangeListener<String> selectionModelMail(int suppressionOn) {
        ListChangeListener<String> L = null;
        switch (suppressionOn) {
            case 0: {
                L = new ListChangeListener<String>() {
                    @Override
                    public void onChanged(Change<? extends String> c) {
                        if(!supprimerOnMail) {
                            Gestionaire_Interface G = new Gestionaire_Interface(new Stage());
                            Gestionnaire_Repertoire C;
                            try {
                                primaryStage = (Stage) denomination.getScene().getWindow();
                                C = G.switchPanel("ajoutCoor.fxml", "Ajout De Coordonnées");
                                Mail m = (Mail) getObject(contactCourant.getMails(), ListMail.getSelectionModel().getSelectedItem());
                                ((ajoutCoorController) C).setRenseignements(contactCourant.getNumContact(), Type.MAIL, m);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                break;
            }
            case 1: {
                L = new ListChangeListener<String>() {
                    @Override
                    public void onChanged(Change<? extends String> c) {
                        if(supprimerOnMail) {
                            Mail m = (Mail) getObject(contactCourant.getMails(), ListMail.getSelectionModel().getSelectedItem());
                            try {
                                R.supprimer_Mail_Contact(contactCourant.getNumContact(), m);
                                Platform.runLater(() -> ListMail.setItems(remplissageListMail(contactCourant)));
                                if(ListMail.getItems().isEmpty()){
                                    colorChanging(ListMail, "#9E9E9E");
                                    supprMail.setStyle("-fx-background-color: #FFAB91;");
                                    supprimerOnMail = false;
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                break;
            }
        }
        return L;
    }

    private ListChangeListener<String> selectionModelTel(int suppressionOn) {
        ListChangeListener<String> L = null;
        switch (suppressionOn) {
            case 0: {
                L=new ListChangeListener<String>() {
                    @Override
                    public void onChanged(Change<? extends String> c) {
                        if(!supprimerOnTel) {
                            Gestionaire_Interface G = new Gestionaire_Interface(new Stage());
                            Gestionnaire_Repertoire C;
                            try {
                                primaryStage = (Stage) denomination.getScene().getWindow();
                                C = G.switchPanel("ajoutCoor.fxml", "Ajout De Coordonnées");
                                TelFax tf = (TelFax) getObject(contactCourant.getTels(), ListTel.getSelectionModel().getSelectedItem());
                                ((ajoutCoorController) C).setRenseignements(contactCourant.getNumContact(), Type.TEL, tf);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                break;
            }
            case 1: {
                L = new ListChangeListener<String>() {
                    @Override
                    public void onChanged(Change<? extends String> c) {
                        if(supprimerOnTel) {
                            TelFax tf = (TelFax) getObject(contactCourant.getTels(), ListTel.getSelectionModel().getSelectedItem());
                            try {
                                R.supprimer_TelFax_Contact(contactCourant.getNumContact(), tf);
                                Platform.runLater(() -> ListTel.setItems(remplissageListTelFax(contactCourant)));
                                if(ListTel.getItems().isEmpty()){
                                    colorChanging(ListTel, "#9E9E9E");
                                    supprTel.setStyle("-fx-background-color: #FFAB91;");
                                    supprimerOnTel = false;
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                break;
            }
        }
        return L;
    }

    private ListChangeListener<String> selectionModelSiteW(int suppressionOn) {
        ListChangeListener<String> L = null;
        switch (suppressionOn) {
            case 0: {
                L=new ListChangeListener<String>() {
                    @Override
                    public void onChanged(Change<? extends String> c) {
                        if(!supprimerOnSiteW) {
                            Gestionaire_Interface G = new Gestionaire_Interface(new Stage());
                            Gestionnaire_Repertoire C;
                            try {
                                primaryStage = (Stage) denomination.getScene().getWindow();
                                C = G.switchPanel("ajoutCoor.fxml", "Ajout De Coordonnées");
                                SiteWeb sw = (SiteWeb) getObject(contactCourant.getSites(), ListSiteWeb.getSelectionModel().getSelectedItem());
                                ((ajoutCoorController) C).setRenseignements(contactCourant.getNumContact(), Type.SITEW, sw);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                break;
            }
            case 1: {
                L = new ListChangeListener<String>() {
                    @Override
                    public void onChanged(Change<? extends String> c) {
                        if(supprimerOnSiteW) {
                            SiteWeb sw = (SiteWeb) getObject(contactCourant.getSites(), ListSiteWeb.getSelectionModel().getSelectedItem());
                            try {
                                R.supprimer_SiteWeb_Contact(contactCourant.getNumContact(), sw);
                                Platform.runLater(() -> ListSiteWeb.setItems(remplissageListSiteWeb(contactCourant)));
                                if(ListSiteWeb.getItems().isEmpty()){
                                    colorChanging(ListSiteWeb, "#9E9E9E");
                                    supprSiteW.setStyle("-fx-background-color: #FFAB91;");
                                    supprimerOnSiteW= false;
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                break;
            }
        }
        return L;
    }
}
