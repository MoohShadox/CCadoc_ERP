package POJO;

import Exceptions.BuildingException;
import Exceptions.ExistantDansLaPieceException;
import Interfaces.Controllable;
import Interfaces.DAOAble;
import Interfaces.Modele;
import Interfaces.Visualisable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class Piece implements Visualisable, DAOAble<Piece> {
    //Element de la pièce
    private HashMap<Livre,Integer> quantite = new HashMap();
    private HashMap<Livre,Float> prix_unitaires = new HashMap<>();
    private HashMap<Livre,Stock> sources = new HashMap<>();
    private String reference;
    private Date dateF;
    private Types_Pieces typesPiece;
    private Type_Transaction typeTransaction;
    private Date delai;
    private boolean rempli;
    private Contact numClientConcerne;
    private Employe employe;

    public void ajouter_livre(Stock source,Livre L,float prix,int qte) throws ExistantDansLaPieceException {
        if(quantite.containsKey(L))
            throw new ExistantDansLaPieceException();
        quantite.put(L,qte);
        prix_unitaires.put(L,prix);
        sources.put(L,source);
    }


    public String total_toutes_lettres() throws Exception {
        float total = 0;
        for (Livre L:prix_unitaires.keySet()){
            total += prix_unitaires.get(L);
        }
        long l = (long) total;
        return Nombre.CALCULATE.getValue(l);
    }

    public long total_ht(){
        float total = 0;
        for (Livre L:prix_unitaires.keySet()){
            total += prix_unitaires.get(L);
        }
        return (long) total;
    }



    public HashMap<Livre, Integer> getQuantite() {
        return quantite;
    }

    public HashMap<Livre, Float> getPrix_unitaires() {
        return prix_unitaires;
    }

    public Date getDateF() {
        return dateF;
    }

    public Types_Pieces getTypesPiece() {
        return typesPiece;
    }

    public Type_Transaction getTypeTransaction() {
        return typeTransaction;
    }

    public Date getDelai() {
        return delai;
    }

    public boolean isRempli() {
        return rempli;
    }

    public Contact getContact() {
        return numClientConcerne;
    }

    public Employe getEmploye() {
        return employe;
    }

    @Override
    public boolean equals(Object obj) {
        Piece p=(Piece)obj;
        return this.reference.equals(p.reference);
    }

    @Override
    public Piece buildFromRepData(HashMap<String, String> H) {
        Piece P = new Piece();
        SimpleDateFormat SDF = new SimpleDateFormat("jj-MM-yyyy");
        if(H.containsKey("REFERENCE"))
            P.reference = H.get("REFERENCE");
        if(H.containsKey("DATE_FACTURE")) {
            try {
                P.dateF = SDF.parse(H.get("DATE_FACTURE"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(H.containsKey("TYPE_FACTURE"))
            P.typesPiece = Types_Pieces.valueOf(H.get("TYPE_FACTURE"));
        if(H.containsKey("TRANSACTION"))
            P.typeTransaction = Type_Transaction.valueOf(H.get("TRANSACTION"));
        if(H.containsKey("DELAI")) {
            try {
                P.delai = SDF.parse(H.get("DELAI"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(H.containsKey("REMPLI"))
            P.rempli = Boolean.valueOf(H.get("REMPLI")).booleanValue();
        if(H.containsKey("REFERENCE"))
            P.reference = H.get("REFERENCE");
        return null;
    }


    @Override
    public HashMap<String, String> getRepositoryAttributs() {
        HashMap<String,String> H = new HashMap<>();
        SimpleDateFormat SDF = new SimpleDateFormat("jj-MM-yyyy");
        H.put("REFERENCE",reference);
        H.put("DATE_FACTURE",SDF.format(dateF));
        H.put("TYPE_FACTURE",typesPiece.toString());
        H.put("TRANSACTION",typeTransaction.toString());
        H.put("DELAI",SDF.format(delai));
        H.put("NUMC",""+numClientConcerne.getNumContact());
        H.put("NUM_E",employe.getNumEmploye());
        H.put("REMPLI",rempli+"");
        return H;
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
        return getRepositoryAttributs();
    }

    @Override
    public Modele<Visualisable> getModele() throws IllegalAccessException {
        return new Modele<Visualisable>(this);
    }


}
