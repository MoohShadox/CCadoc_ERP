package Presentation.Control;

import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.Controllable;
import Interfaces.Controlleur_Visualisable;
import Interfaces.Modele;
import POJO.*;
import Presentation.FXML.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class Impression extends Corps_Facture{

    @FXML
    private BorderPane Contenair;

    @FXML
    private TableView<Modele<Livre>> Details_Commande;

    @FXML
    private AnchorPane En_Tete;

    @FXML
    private Label Reference;

    @FXML
    private Label Type_Piece;

    @FXML
    private Label Denomination_Client;

    @FXML
    private AnchorPane EnTete2;

    @FXML
    private AnchorPane PiedPage1;

    @FXML
    private Label TotalHR_Label;

    @FXML
    private Label TVA_Label;

    @FXML
    private Label TTC_Label;

    @FXML
    private Text Toute_Lettres;

    @FXML
    private Text PiedPage2;

    //Informations a transmettre des l'instanciation
    private Types_Pieces TP;
    private Type_Transaction TT;
    private String reference;
    private String denom_client;

    //Informations a calculer
    private float total_ht;
    private float total_ttc;
    private String toutelettres;

    @FXML
    public void initialize() throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException {

    }

    public void setPiece(Piece P) throws Exception {
        TP = P.getTypesPiece();
        TT = P.getTypeTransaction();
        reference= P.getReference();
        denom_client = P.getContact().getDenomnation();
        total_ht = P.total_ht();
        total_ttc = total_ht + (total_ht*9)/100;
        toutelettres = P.total_toutes_lettres();
    }


    @FXML
    void BAction(ActionEvent event) throws IOException, IllegalAccessException {
        imprimer_premier_page();
    }


    void imprimer_premier_page() throws IOException, IllegalAccessException {
        FXMLLoader loader = new FXMLLoader ( );
        loader.setLocation ( Main.class.getResource ( "Premiere_Page.fxml" ) );
        Parent root = loader.load ( );
        Premiere_Page FOP = loader.getController ( );
        LinkedList<Modele<Livre>> L = new LinkedList<>();
        int i=1;
        for(Modele<Livre> ML:Details_Commande.getItems()){
            L.add(ML);
            if(i==17)
                break;
            i++;
        }
        for(Modele<Livre> ML : L)
        {
            Details_Commande.getItems().remove(ML);
        }
        FOP.lancer_impression(L,TT.toString(),reference,denom_client);
        PrinterJob PJ = PrinterJob.createPrinterJob();
        Printer P = Printer.getDefaultPrinter();
        JobSettings JS = PJ.getJobSettings();
        JS.setPageLayout(Printer.getDefaultPrinter().createPageLayout(Paper.A4,PageOrientation.PORTRAIT,1,1,1,1));
        PJ.setPrinter(P);
        PJ.printPage(root);
        PJ.endJob();
        lancer_impression_tableau();
    }


    void lancer_impression_tableau() throws IllegalAccessException, IOException {
        LinkedList<Modele<Livre>> L = new LinkedList<>();
        int i=1;
        while(Details_Commande.getItems().size()>=20)
        {
            FXMLLoader loader = new FXMLLoader ( );
            loader.setLocation ( Main.class.getResource ( "Milieu_Piece.fxml" ) );
            Parent root = loader.load ( );
            Milieu_Piece FOP = loader.getController ( );
            i=1;
            L.clear();
            for(Modele<Livre> ML:Details_Commande.getItems()){
            L.add(ML);
            if(i==19)
                break;
            i++;
            }
            for(Modele<Livre> ML : L)
            {
                Details_Commande.getItems().remove(ML);
            }
            FOP.lancer_impression(L,reference);
            PrinterJob PJ = PrinterJob.createPrinterJob();
            Printer P = Printer.getDefaultPrinter();
            JobSettings JS = PJ.getJobSettings();
            JS.setPageLayout(Printer.getDefaultPrinter().createPageLayout(Paper.A4,PageOrientation.PORTRAIT,1,1,1,1));
            PJ.setPrinter(P);
            PJ.printPage(root);
            PJ.endJob();
        }
        imprimer_derniere_page();
    }


    void imprimer_derniere_page() throws IOException, IllegalAccessException {
        LinkedList<Modele<Livre>> L = new LinkedList<>();
        FXMLLoader loader = new FXMLLoader ( );
        loader.setLocation ( Main.class.getResource ( "Derniere_Page.fxml" ) );
        Parent root = loader.load ( );
        Derniere_Page FOP = loader.getController ( );
        for(Modele<Livre> ML:Details_Commande.getItems()){
            L.add(ML);
        }
        for(Modele<Livre> ML : L) {
            Details_Commande.getItems().remove(ML);
        }
        FOP.lancer_impression(L,100,100,1000,"Toutes lettres");
        PrinterJob PJ = PrinterJob.createPrinterJob();
        Printer P = Printer.getDefaultPrinter();
        JobSettings JS = PJ.getJobSettings();
        JS.setPageLayout(Printer.getDefaultPrinter().createPageLayout(Paper.A4,PageOrientation.PORTRAIT,1,1,1,1));
        PJ.setPrinter(P);
        PJ.printPage(root);
        PJ.endJob();
    }



    @Override
    public void RefreshCollection(Collection<Livre> T) throws IllegalAccessException {
        for(Livre L:T){
            super.recensement.ajouter(L);
        }
        setVisuel();
    }

    @Override
    public void setVisuel() {
        super.setVisuel();
    }

}