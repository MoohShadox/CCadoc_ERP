package POJO;


import Exceptions.BuildingException;
import Interfaces.DAOAble;
import Interfaces.Modele;
import Interfaces.Visualisable;

import java.util.HashMap;

public class Employe implements DAOAble<Employe>, Visualisable {
    private String numEmploye; //codification
    private String nom, prenom;
    private Postes poste;

    public String getNumEmploye() {
        return numEmploye;
    }

    public  void setNumEmploye(String numEmploye) {
        this.numEmploye = numEmploye;
    }

    public String getNom(){
        return nom;
    }

    public void setNom(String nom){
        this.nom=nom;
    }

    public String getPrenom(){
        return prenom;
    }

    public void setPrenom(String prenom){
        this.prenom=prenom;
    }

    public String getPoste() {
        return poste.toString();
    }

    public void setPoste(String poste) {
        this.poste =Postes.valueOf(poste.toLowerCase());
    }


    @Override
    public String toString() {
        return numEmploye +" "+nom+" "+prenom;
    }

    @Override
    public boolean equals(Object obj) {
        Employe e=(Employe)obj;
        return this.numEmploye==e.numEmploye;
    }

    @Override
    public HashMap<String, String> getRepositoryAttributs() throws IllegalAccessException {
        return getPrincipalAttributes();
    }

    @Override
    public String getTableName() {
        return "EMPLOYE";
    }

    @Override
    public String getReference() {
        return numEmploye;
    }

    @Override
    public String getKeyName() {
        return "NUM_E";
    }

    @Override
    public HashMap<String, String> getPrincipalAttributes() throws IllegalAccessException {
        HashMap<String,String> H = new HashMap<>();
        H.put("NUM_E",numEmploye);
        H.put("NOM",nom);
        H.put("PRENOM",prenom);
        if(poste!=null)
            H.put("POSTE",poste.toString());
        return H;

    }

    @Override
    public Modele<Visualisable> getModele() throws IllegalAccessException {
        return new Modele<>(this);
    }


    @Override
    public Employe buildFromRepData(HashMap<String, String> H) throws BuildingException, IllegalAccessException {
        nom = H.get("NOM");
        prenom = H.get("PRENOM");
        poste = Postes.valueOf(H.get("POSTE"));
        numEmploye = H.get("NUM_E");
        return null;
    }
}
