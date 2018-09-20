package Presentation.Control;

import Interfaces.Modele;
import POJO.Livre;
import POJO.Type_Transaction;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.util.Collection;
import java.util.LinkedList;

public class Premiere_Page extends Corps_Facture{

    public TableView<Modele<Livre>> Details_Commande;
    public Label Type_Piece;
    public Label Reference;
    public Label Denomination_Client;

    public void lancer_impression(Collection<Modele<Livre>> C,String type,String ref,String denom_client) throws IllegalAccessException {

        Type_Piece.setText(type);
        Reference.setText(ref);
        Denomination_Client.setText(denom_client);
        LinkedList<Livre> CC = new LinkedList<>();
        for(Modele<Livre> L:C){
            CC.add(L.getSource());
        }
        super.RefreshCollection(CC);
        super.setVisuel();

    }



}
