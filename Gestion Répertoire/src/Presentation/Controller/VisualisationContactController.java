package Presentation.Controller;

import Client.Repertoire;
import DAO.*;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.Descriptible;
import POJO.Contact;
import POJO.Mail;
import POJO.SiteWeb;
import POJO.TelFax;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import java.sql.SQLException;
import java.util.Iterator;

public class VisualisationContactController extends Gestionnaire_Repertoire  {

    @FXML
    private TreeTableView<Modele_Contact<? extends Descriptible>> TableContact =new TreeTableView<>();

    public VisualisationContactController() throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException {
    }

    @FXML
    private void fermerFenetre() {
        this.fermerFenetre(TableContact);
    }

    @FXML
    private void Iconify() {
        this.Iconify(TableContact);
    }

    private DAO<Contact> DC = new DAOContact(new Contact());
    private Repertoire R=new Repertoire(DC.load());


    @Override
    public void provideTable() throws IllegalAccessException {

        TreeTableColumn<Modele_Contact<? extends Descriptible>,String>  denomination=new TreeTableColumn<>("Dénomination");
        TreeTableColumn<Modele_Contact<? extends Descriptible>,String>  numC=new TreeTableColumn<>("Numéro");
        TableContact.getColumns().addAll(numC,denomination);

        //just to show la phrase "Répertoire courant "...sinon ça n'a pas d'autre role
        Mail m=new Mail();
        m.setAdresseMail("Répertoire Courant : ");
        m.setNomMail("");
        Modele_Contact<Mail> MC=new Modele_Contact(m);
        TreeItem<Modele_Contact<? extends Descriptible>> Troot=new TreeItem<Modele_Contact<?extends Descriptible>>(MC);
        TreeItem<Modele_Contact<? extends Descriptible>> Titem;
        TreeItem<Modele_Contact<? extends Descriptible>> TSousItem;

        Iterator i ;
        for (Long c : R.getContacts().keySet()) {
            Titem=new TreeItem<Modele_Contact<? extends Descriptible>>(R.getContacts().get(c));
            i=R.getSection_mail().get(R.getContacts().get(c)).iterator();
            while (i.hasNext()){
                TSousItem=new TreeItem<Modele_Contact<? extends Descriptible>>((Modele_Contact<Mail>)i.next());
                Titem.getChildren().add(TSousItem);
            }
            i=R.getSection_telfax().get(R.getContacts().get(c)).iterator();
            while (i.hasNext()){
                TSousItem=new TreeItem<Modele_Contact<? extends Descriptible>>((Modele_Contact<TelFax>)i.next());
                Titem.getChildren().add(TSousItem);
            }
            i=R.getSection_sitew().get(R.getContacts().get(c)).iterator();
            while (i.hasNext()){
                TSousItem=new TreeItem<Modele_Contact<? extends Descriptible>>((Modele_Contact<SiteWeb>)i.next());
                Titem.getChildren().add(TSousItem);
            }
            Troot.getChildren().add(Titem);
        }
        numC.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Modele_Contact<? extends Descriptible>,String> param)
                        ->
                    new SimpleStringProperty(""+param.getValue().getValue().getDescription().get(param.getValue().getValue().getSrc().getKeyName()).get())
        );
        denomination.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Modele_Contact<? extends Descriptible>,String> param)
                        -> new SimpleStringProperty(""+param.getValue().getValue().getDescription().get(param.getValue().getValue().getSrc().getName()).get())
        );
        TableContact.setRoot(Troot);
        TableContact.setShowRoot(true);

        TableContact.setColumnResizePolicy((param -> true));
        Platform.runLater(() -> customResize(TableContact));
    }
}
