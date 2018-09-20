package Presentation.Control;

import Interfaces.Modele;
import POJO.Livre;
import POJO.Type_Transaction;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.Collection;
import java.util.LinkedList;

public class Milieu_Piece extends Corps_Facture{

    public TableView<Modele<Livre>> Details_Commande;
    public Label Reference;
    public Text PiedPage2;


    public void lancer_impression(Collection<Modele<Livre>> C,String ref) throws IllegalAccessException {
        Reference.setText(ref);
        PiedPage2.setVisible(true);
        LinkedList<Livre> CC = new LinkedList<>();
        for(Modele<Livre> L:C){
            CC.add(L.getSource());
        }
        super.RefreshCollection(CC);
        super.setVisuel();
        /*PrinterJob PJ = PrinterJob.createPrinterJob();
        Printer P = Printer.getDefaultPrinter();
        JobSettings JS = PJ.getJobSettings();
        JS.setPageLayout(Printer.getDefaultPrinter().createPageLayout(Paper.A4,PageOrientation.PORTRAIT,1,1,1,1));
        PJ.setPrinter(P);
        PJ.printPage(Reference.getScene().getRoot());
        PJ.endJob();*/
    }



}
