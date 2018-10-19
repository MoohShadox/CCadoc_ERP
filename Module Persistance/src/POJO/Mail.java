package POJO;

import DAO.*;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.DAOAble;
import Interfaces.Descriptible;

import java.sql.SQLException;
import java.util.HashMap;

public class Mail implements Descriptible<Mail> {
    private String adresseMail;
    private String typeC, nomMail;

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getTypeC() {
        return typeC;
    }

    public void setTypeC(String typeC) {
        this.typeC = typeC;
    }

    public String getNomMail() {
        return nomMail;
    }

    public void setNomMail(String nomMail) {
        this.nomMail = nomMail;
    }

    @Override
    public boolean equals(Object obj) {
        Mail m=(Mail)obj;
        return this.adresseMail.equals(m.adresseMail);
    }

    @Override
    public HashMap<String, String> getRepositoryAttributs() throws IllegalAccessException {
        HashMap<String,String> H = new HashMap<>();
        H.put("ADRESSE_MAIL",adresseMail);
        H.put("NOM_MAIL",nomMail);
        H.put("TYPEC",typeC);
        return H;
    }

    @Override
    public String getTableName() {
        return "MAIL";
    }

    @Override
    public String getReference() {
        return adresseMail;
    }

    @Override
    public String getKeyName() {
        return "ADRESSE_MAIL";
    }

    public String getName() {
        return "NOM_MAIL";
    }

    @Override
    public Mail buildFromRepData(HashMap<String, String> H) throws BuildingException, IllegalAccessException {
        Mail M = new Mail();
        try {
            M.adresseMail = H.get("ADRESSE_MAIL");
            M.nomMail = H.get("NOM_MAIL");
            M.typeC = H.get("TYPEC");
        }catch (Exception E){

        }
        return M;
    }


    @Override
    public void Maj_BDD(String attribut, String nouvelle_valeur, String ref) throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException {
        DAO<Mail> DM=new DAOMail(new Mail());
        System.out.println(ref);
        Mail M=DM.recuperer(ref);
        if(!M.getRepositoryAttributs().get(attribut).equalsIgnoreCase(nouvelle_valeur)){
            switch (attribut){
                case "ADRESSE_MAIL":M.adresseMail=nouvelle_valeur;
                break;
                case "NOM_MAIL":M.nomMail=nouvelle_valeur;
                break;
                case "TYPEC":M.typeC = nouvelle_valeur;
                break;
            }
        }
        DM.mettre_a_jour(M,ref);
    }

    @Override
    public boolean verfier(String s) {
        return false;
    }

    @Override
    public Modele_Contact<Mail> getModeleContact() throws IllegalAccessException {
        return new Modele_Contact<Mail>(this);
    }
}
