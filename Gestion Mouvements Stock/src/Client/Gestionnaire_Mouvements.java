package Client;

import DAO.*;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.*;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;

public class Gestionnaire_Mouvements {

    LinkedList<Mouvement_Stock> mouvement_effectues = new LinkedList<>();
    HashMap<Mouvement_Stock,Etat_Mouvement> Etats = new HashMap<Mouvement_Stock, Etat_Mouvement>();


    public Mouvement_Stock generer_trace(Stock S, Inventaire I, Stock S2, Employe E){
        Mouvement_Stock MS = new Mouvement_Stock();
        MS.setSource(S);
        MS.setDate(Calendar.getInstance().getTime());
        MS.setDestination(S2);
        MS.setEmploye(E);
        try {
            DAO<Livre> DL = new DAOLivre(new Livre());
            for(String SS:I.getRecensement().getkeys()){
                Livre L = DL.recuperer(SS);
                MS.getLivres().put(L,I.getRecensement().recuperer(SS).getQuantite());
            }
        } catch (SQLException | NonExistantDansLaBDD | BuildingException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return MS;
    }

    public Mouvement_Stock generer_inverse(Mouvement_Stock MS){
        Mouvement_Stock MSS = new Mouvement_Stock();
        MSS.setLivres(MS.getLivres());
        MSS.setDestination(MS.getSource());
        MSS.setSource(MS.getDestination());
        MSS.setEmploye(MS.getEmploye());
        MSS.setDate(Calendar.getInstance().getTime());
        return MSS;
    }

    public void appliquer_mouvement(Mouvement_Stock MS){

    }







}
