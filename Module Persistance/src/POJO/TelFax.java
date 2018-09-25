package POJO;


import DAO.*;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.DAOAble;
import Interfaces.Descriptible;
import Interfaces.Modele;
import Interfaces.Visualisable;

import java.sql.SQLException;
import java.util.HashMap;

public class TelFax implements DAOAble<TelFax>, Descriptible<TelFax> {
    private String numero;
    private boolean telfax;
    private Contact contact;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean isTelfax() {
        return telfax;
    }

    public void setTelfax(boolean telfax) {
        this.telfax = telfax;
    }

    @Override
    public boolean equals(Object obj) {
        TelFax t= (TelFax)obj;
        return this.numero==t.numero;
    }

    @Override
    public HashMap<String, String> getRepositoryAttributs() throws IllegalAccessException {
        HashMap<String,String> H = new HashMap<>();
        H.put("NUMERO",numero);
        H.put("TELFAX",telfax+"");
        return H;
    }

    @Override
    public String getTableName() {
        return "TELFAX";
    }

    @Override
    public String getReference() {
        return numero;
    }

    @Override
    public String getKeyName() {
        return "NUMERO";
    }


    @Override
    public TelFax buildFromRepData(HashMap<String, String> H) throws BuildingException, IllegalAccessException {
        TelFax TF = new TelFax();
        if(H.containsKey("NUMERO"))
            TF.numero = H.get("NUMERO");
        if(H.containsKey("TELFAX"))
            TF.telfax = Boolean.valueOf(H.get("TELFAX"));
        return TF;
    }

    @Override
    public void Maj_BDD(String attribut, String nouvelle_valeur, String ref) throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException {
        DAO<TelFax> DTF = new DAOTelFax(new TelFax());
        TelFax TF= DTF.recuperer(ref);
        if(!TF.getRepositoryAttributs().get(attribut).equalsIgnoreCase(nouvelle_valeur)){
            switch (attribut){
                case "NUMERO":TF.numero=nouvelle_valeur;
                    break;
                case "TELFAX":TF.telfax=Boolean.valueOf(nouvelle_valeur);
                    break;
            }
        }
        DTF.mettre_a_jour(TF,ref);
    }

    @Override
    public boolean verfier(String s) {
        return false;
    }
}

