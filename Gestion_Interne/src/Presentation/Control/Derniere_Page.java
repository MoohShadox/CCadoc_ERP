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

public class Derniere_Page extends Corps_Facture{

    public TableView<Modele<Livre>> Details_Commande;
    public Label TotalHR_Label;
    public Label TVA_Label;
    public Label TTC_Label;
    public Text Toute_Lettres;

    public void lancer_impression(Collection<Modele<Livre>> C,float Total,float TVA,float TTC,String toutes_lettres) throws IllegalAccessException {
        LinkedList<Livre> CC = new LinkedList<>();
        for(Modele<Livre> L:C){
            CC.add(L.getSource());
        }
        super.RefreshCollection(CC);
        super.setVisuel();
        TotalHR_Label.setText(Total+"");
        TVA_Label.setText(TVA+"");
        TTC_Label.setText(TTC+"");
        Toute_Lettres.setText(toutes_lettres);

    }



}
