package POJO;

import Exceptions.BuildingException;
import Interfaces.DAOAble;

import java.util.HashMap;

public class Mail implements DAOAble<Mail> {
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


}
