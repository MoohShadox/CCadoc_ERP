package POJO;


import Exceptions.BuildingException;
import Interfaces.DAOAble;
import Interfaces.Modele;
import Interfaces.Visualisable;

import java.util.HashMap;

public class TelFax implements DAOAble<TelFax> {
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
}
