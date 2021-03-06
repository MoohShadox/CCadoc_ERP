package POJO;

import Exceptions.BuildingException;
import Interfaces.DAOAble;

import java.util.HashMap;

public class Mouvements_Externes extends Mouvement_Stock {

    private HashMap<Livre,Float> prix_acquisition = new HashMap<>();

    public Mouvements_Externes(Mouvement_Stock MS){
        super.date = MS.date;
        super.destination = MS.destination;
        super.employe = MS.employe;
        super.livres = MS.livres;
        super.reference = MS.reference;
        super.source = MS.source;
        super.lien_bon = MS.lien_bon;
    }



    public HashMap<Livre, Float> getPrix_acquisition() {
        return prix_acquisition;
    }

    public void setPrix_acquisition(HashMap<Livre, Float> prix_acquisition) {
        this.prix_acquisition = prix_acquisition;
    }

}
