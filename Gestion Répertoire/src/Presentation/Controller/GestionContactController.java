package Presentation.Controller;

import POJO.Contact;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.Collection;

public class GestionContactController extends Gestionnaire_Repertoire{

    @Override
    public void setCollection(Collection<Contact> T) {

    }

    @Override
    public void RefreshCollection(Collection<Contact> T) throws IllegalAccessException {

    }

    @Override
    public void setVisuel() {

    }

    @FXML
    private ListView<SimpleStringProperty> ListMail = new ListView<>();
    @FXML
    private ListView<SimpleStringProperty> ListSiteWeb = new ListView<>();
    @FXML
    private ListView<SimpleStringProperty> ListTel = new ListView<>();
    @FXML
    private JFXTextField denomination;
    @FXML
    private JFXTextField adresse;
    @FXML
    private JFXTextField type;
    @FXML
    private JFXButton modifier;

    private Contact contactCourant;

    @FXML
    private void fermerFenetre() {
        this.fermerFenetre(denomination);
    }

    @FXML
    private void iconify() {
        this.Iconify(denomination);
    }

    public void setContactCourant(Contact contactCourant){
        this.contactCourant=contactCourant;
    }

    @FXML
    private void modifier(){
        denomination.setEditable(true);
        adresse.setEditable(true);
        type.setEditable(true);
        ListMail.setEditable(true);
        ListSiteWeb.setEditable(true);
        ListTel.setEditable(true);
        modifier.setStyle("-fx-background-color: #FFC619");
        modifier.setText("Sauvegarder");
        modifier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                modifier.setStyle("-fx-background-color:  #A5D6A7");
                modifier.setText("Modifier");
            }
        });
    }

}
