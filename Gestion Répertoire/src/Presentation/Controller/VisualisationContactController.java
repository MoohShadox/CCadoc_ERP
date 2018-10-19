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
import Presentation.FXML.Gestionaire_Interface;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

public class VisualisationContactController extends Gestionnaire_Repertoire  {

    @FXML
    private TreeTableView<Modele_Contact<? extends Descriptible>> TableContact =new TreeTableView<>();

    @FXML
    private void fermerFenetre() {
        this.fermerFenetre(TableContact);
    }

    @FXML
    private void Iconify() {
        this.Iconify(TableContact);
    }


    @Override
    public void provideTable() throws IllegalAccessException {

        TreeTableColumn<Modele_Contact<? extends Descriptible>,String>  denomination=new TreeTableColumn<>("Dénomination");
        TreeTableColumn<Modele_Contact<? extends Descriptible>,String>  numC=new TreeTableColumn<>("Numéro");
        TableContact.getColumns().addAll(numC,denomination);

        //just to show la phrase "Répertoire courant "...sinon ça n'a pas d'autre role
        Mail m=new Mail("Répertoire Courant : ","","");
        Modele_Contact<Mail> MC=new Modele_Contact(m);
        TreeItem<Modele_Contact<? extends Descriptible>> Troot=new TreeItem<Modele_Contact<?extends Descriptible>>(MC);
        TreeItem<Modele_Contact<? extends Descriptible>> Titem=new TreeItem<Modele_Contact<? extends Descriptible>>();
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
            while (i.hasNext()) {
                TSousItem = new TreeItem<Modele_Contact<? extends Descriptible>>((Modele_Contact<SiteWeb>) i.next());
                Titem.getChildren().add(TSousItem);
            }
            Troot.getChildren().add(Titem);
        }
        numC.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Modele_Contact<? extends Descriptible>,String> param) ->
                        new SimpleStringProperty(""+param.getValue().getValue().getDescription().get(param.getValue().getValue().getSrc().getKeyName()).get())
        );
        denomination.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Modele_Contact<? extends Descriptible>,String> param)->
                        new SimpleStringProperty("" + param.getValue().getValue().getDescription().get(param.getValue().getValue().getSrc().getName()).get())
        );
        numC.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        denomination.setCellFactory((TextFieldTreeTableCell.forTreeTableColumn()));
        denomination.setOnEditCommit(new javafx.event.EventHandler<TreeTableColumn.CellEditEvent<Modele_Contact<? extends Descriptible>,String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Modele_Contact<? extends Descriptible>, String> event) {
                if(!(event.getRowValue().getValue().getSrc() instanceof Contact)) {
                    TreeItem<Modele_Contact<? extends Descriptible>> currentItem = TableContact.getTreeItem(event.getTreeTablePosition().getRow());
                    long c = Long.valueOf(currentItem.getParent().getValue().getDescription().get("NUMC").get());
                    try {
                        modifierRow(R,false,c, event.getRowValue().getValue(), event.getNewValue());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }else return;
            }
        });
        numC.setOnEditCommit(new javafx.event.EventHandler<TreeTableColumn.CellEditEvent<Modele_Contact<? extends Descriptible>,String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Modele_Contact<? extends Descriptible>, String> event) {
                if(!(event.getRowValue().getValue().getSrc() instanceof Contact)) {
                    TreeItem<Modele_Contact<? extends Descriptible>> currentItem = TableContact.getTreeItem(event.getTreeTablePosition().getRow());
                    long c = Long.valueOf(currentItem.getParent().getValue().getDescription().get("NUMC").get());
                    try {
                        modifierRow(R,true,c, event.getRowValue().getValue(), event.getNewValue());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }else return;
            }
        });
        TableContact.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TableContact.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Modele_Contact<? extends Descriptible>>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Modele_Contact<? extends Descriptible>>> observable, TreeItem<Modele_Contact<? extends Descriptible>> oldValue, TreeItem<Modele_Contact<? extends Descriptible>> newValue) {
                TreeItem<Modele_Contact<? extends Descriptible>> selectedItem =  newValue;
                if(selectedItem.getValue().getSrc() instanceof Contact){
                    Gestionaire_Interface G ;
                    try {
                        G = new Gestionaire_Interface( new Stage() );
                        Gestionnaire_Repertoire C ;
                        GestionContactController.contactCourant=(Contact) selectedItem.getValue().getSrc();
                        C = G.switchPanel("GestionContact.fxml", "Gestion Du Contact");
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else return;
            }
        });
        TableContact.setRoot(Troot);
        TableContact.setShowRoot(true);
        TableContact.setEditable(true);
        TableContact.setFocusTraversable(false);
        TableContact.setColumnResizePolicy((param -> true));
        Platform.runLater(() -> customResize(TableContact));
    }

}


