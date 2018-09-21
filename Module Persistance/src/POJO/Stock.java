package POJO;

import Exceptions.BuildingException;
import Interfaces.DAOAble;
import Interfaces.Modele;
import Interfaces.Visualisable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Stock implements DAOAble<Stock>, Visualisable,Destination {
    private String nom="";
    private String localisation="";
    private Date realisation = Calendar.getInstance().getTime();
    private HashMap<Livre,Integer> livres=new HashMap<>();


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate_realisation() {
        return realisation;
    }

    public void setDate_realisation(Date realisation) {
        this.realisation = realisation;
    }

    public HashMap<Livre,Integer> getLivres() {
        return livres;
    }


    public void addLivre(Livre L, int q){
        if(livres.containsKey(L))
        {
            livres.put(L,livres.get(L)+q);
        }
        else
            livres.put(L,q);
    }

    public void removeLivre(Livre L){
       livres.remove(L);
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }


    @Override
    public HashMap<String, String> getRepositoryAttributs() throws IllegalAccessException {
        return getPrincipalAttributes();
    }

    @Override
    public String getTableName() {
        return "STOCK";
    }

    @Override
    public String getReference() {
        return nom;
    }

    @Override
    public String getKeyName() {
        return "NOM_S";
    }



    @Override
    public HashMap<String, String> getPrincipalAttributes() throws IllegalAccessException {
        HashMap<String,String> H = new HashMap<>();
        SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
        H.put("NOM_S",nom);
        H.put("LIEU",localisation);
        H.put("DATE_R",SDF.format(getDate_realisation()));
        return H;
    }

    @Override
    public Modele<Visualisable> getModele() throws IllegalAccessException {
        return new Modele<>(this);
    }

    @Override
    public Stock buildFromRepData(HashMap<String, String> H) throws BuildingException {
        Stock S = new Stock();
        if(!H.containsKey("NOM_S") || !H.containsKey("LIEU") || !H.containsKey("DATE_R"))
            throw new BuildingException();
        S.nom = H.get("NOM_S");
        S.localisation = H.get("LIEU");
        SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
        try {
            S.setDate_realisation(SDF.parse(H.get("DATE_R")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return S;
    }

    @Override
    public String getAdresse() {
        return localisation;
    }
}
