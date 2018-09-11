package POJO;

import Exceptions.BuildingException;
import Interfaces.DAOAble;
import Interfaces.Modele;
import Interfaces.Visualisable;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class Piece implements Visualisable, DAOAble<Piece> {
    //Element de la pièce
    HashMap<Livre,Integer> contenu = new HashMap();
    private String reference;
    private Date DateF;
    private Types_Pieces typesPiece;
    private Type_Transaction typeTransaction;
    private Date delai;
    private boolean rempli;
    private Client numClientConcerne; //le numéro du client est suffisant *** A REVOIR
    private Employe employe; //le numéro d l'employé suffit *** A REVOIR

    //Définition des méthodes abstraites

    @Override
    public boolean equals(Object obj) {
        Piece p=(Piece)obj;
        return this.reference.equals(p.reference);
    }

    @Override
    public Piece buildFromRepData(HashMap<String, String> H) throws BuildingException, IllegalAccessException {
        return null;
    }


    @Override
    public HashMap<String, String> getRepositoryAttributs() throws IllegalAccessException {
        HashMap<String,String> H = new HashMap<>();
        H.put()
        return null;
    }

    @Override
    public String getTableName() {
        return "PIECE";
    }

    public String getReference() {
        return reference;
    }

    @Override
    public String getKeyName() {
        return "REFERENCE";
    }

    @Override
    public HashMap<String, String> getPrincipalAttributes() throws IllegalAccessException {
        return null;
    }

    @Override
    public Modele<Visualisable> getModele() throws IllegalAccessException {
        return new Modele<Visualisable>(this);
    }
}
