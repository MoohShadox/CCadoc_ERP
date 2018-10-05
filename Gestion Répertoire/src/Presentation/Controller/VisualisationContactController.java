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
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
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
                (TreeTableColumn.CellDataFeatures<Modele_Contact<? extends Descriptible>,String> param) ->
                    new SimpleStringProperty(""+param.getValue().getValue().getDescription().get(param.getValue().getValue().getSrc().getKeyName()).get())
        );
        denomination.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Modele_Contact<? extends Descriptible>,String> param)->
            new SimpleStringProperty("" + param.getValue().getValue().getDescription().get(param.getValue().getValue().getSrc().getName()).get())
        );
        numC.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        //une fonction est censé remplacer la ligne qu est au dessus de ça, mais ça marche pas a cause de no "update Item"
        /*
        numC.addEventHandler(new EventType<MouseEvent>(),new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                TextField tf=new TextField();
                tf.setStyle("-fx-border-color: black");
                numC.setCellFactory(new Callback<TreeTableColumn<Modele_Contact<? extends Descriptible>, String>, TreeTableCell<Modele_Contact<? extends Descriptible>, String>>() {
                    @Override
                    public TreeTableCell<Modele_Contact<? extends Descriptible>, String> call(TreeTableColumn<Modele_Contact<? extends Descriptible>, String> param) {
                        TreeTableCell<Modele_Contact<? extends Descriptible>, String> T=new TreeTableCell<Modele_Contact<? extends Descriptible>, String>();
                        T.setGraphic(tf);
                        return T;
                    }
                });
            }
        });
        */
        denomination.setCellFactory((TextFieldTreeTableCell.forTreeTableColumn()));
        numC.setOnEditCommit(new javafx.event.EventHandler<TreeTableColumn.CellEditEvent<Modele_Contact<? extends Descriptible>,String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Modele_Contact<? extends Descriptible>, String> event) {
                if(!(event.getRowValue().getValue().getSrc() instanceof Contact)) {
                    TreeItem<Modele_Contact<? extends Descriptible>> currentItem = TableContact.getTreeItem(event.getTreeTablePosition().getRow());
                    long c = Long.valueOf(currentItem.getParent().getValue().getDescription().get("NUMC").get());
                    try {
                        modifierRow(true,c, event.getRowValue().getValue(), event.getNewValue());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        denomination.setOnEditCommit(new javafx.event.EventHandler<TreeTableColumn.CellEditEvent<Modele_Contact<? extends Descriptible>,String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Modele_Contact<? extends Descriptible>, String> event) {
                if(!(event.getRowValue().getValue().getSrc() instanceof Contact)) {
                    TreeItem<Modele_Contact<? extends Descriptible>> currentItem = TableContact.getTreeItem(event.getTreeTablePosition().getRow());
                    long c = Long.valueOf(currentItem.getParent().getValue().getDescription().get("NUMC").get());
                    try {
                        modifierRow(false,c, event.getRowValue().getValue(), event.getNewValue());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        TableContact.setRoot(Troot);
        TableContact.setShowRoot(true);
        TableContact.setEditable(true);
        TableContact.setColumnResizePolicy((param -> true));
        Platform.runLater(() -> customResize(TableContact));
    }


    public void modifierRow(boolean keyChanging,Long c,Modele_Contact<? extends Descriptible> M,String newValue) throws IllegalAccessException{
                if(M.getSrc() instanceof Mail) {
                    //Ca marche pas quand je fais Mail m=(Mail) M.getSrc();
                    //car la il prend l instance elle meme de M.getSrc et quand j affecte la nvelle valeur a m , il l affecte a M.getSrc aussi C'EST TRéS CON IKKKKKK
                    //so faudra chercher a le faire d'une façon plus optimisée
                    Mail m =new Mail();
                    m.setNomMail(((Mail) M.getSrc()).getNomMail());
                    m.setAdresseMail(((Mail) M.getSrc()).getAdresseMail());
                    if(keyChanging)
                        m.setAdresseMail(newValue);
                    else
                        m.setNomMail(newValue);
                    m.setTypeC(((Mail) M.getSrc()).getTypeC());
                    System.out.println(((Mail) M.getSrc()).getAdresseMail()+"   "+newValue);
                    R.modifier_mail(c, (Mail) M.getSrc(), m);
                }else if(M.getSrc() instanceof TelFax){
                    TelFax tf =new TelFax();
                    tf.setNumero(((TelFax) M.getSrc()).getNumero());
                    tf.setTelfax(((TelFax) M.getSrc()).getTelfax());
                    if(keyChanging)
                        tf.setNumero(newValue);
                    else
                        tf.setTelfax(newValue);
                    R.modifier_telFax(c, (TelFax) M.getSrc(), tf);
                }else if(M.getSrc() instanceof SiteWeb){
                    SiteWeb sw = new SiteWeb();
                    sw.setDescription(((SiteWeb) M.getSrc()).getDescription());
                    sw.setUrl(((SiteWeb) M.getSrc()).getUrl());
                    if(keyChanging)
                        sw.setUrl(newValue);
                    else
                        sw.setDescription(newValue);
                    R.modifier_siteWeb(c, (SiteWeb) M.getSrc(), sw);
                }
    }



}


