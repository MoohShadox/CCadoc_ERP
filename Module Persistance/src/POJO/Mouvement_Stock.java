package POJO;

import DAO.DAO;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.DAOAble;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import DAO.*;

public class Mouvement_Stock implements DAOAble<Mouvement_Stock> {
    protected String reference;
    protected Stock source;
    protected Destination destination;
    protected Date date;
    protected HashMap<Livre,Integer> livres = new HashMap<>();
    protected Employe employe;

    //Setters et Getters

    public Stock getSource() {
        return source;
    }

    public void setSource(Stock source) {
        this.source = source;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HashMap<Livre, Integer> getLivres() {
        return livres;
    }

    public void setLivres(HashMap<Livre, Integer> livres) {
        this.livres = livres;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }


    @Override
    public HashMap<String, String> getRepositoryAttributs() throws IllegalAccessException {
        HashMap<String,String> H = new HashMap<>();
        H.put("REFERENCE",reference);
        H.put("DATE_FACTURE",new SimpleDateFormat("jj-MM-yyyy").format(date));
        H.put("TRANSACTION",Type_Transaction.SORTIE.toString());
        H.put("SOURCE",source.getReference());
        H.put("DESTINATION",destination.getReference());
        H.put("NUM_E",employe.getNumEmploye());
        return H;
    }

    @Override
    public String getTableName() {
        return "MOUVEMENTS";
    }

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public String getKeyName() {
        return "REFERENCE";
    }

    @Override
    public Mouvement_Stock buildFromRepData(HashMap<String, String> H) throws BuildingException, IllegalAccessException {
        Mouvement_Stock MS = new Mouvement_Stock();
        MS.reference = H.get("REFERENCE");
        try {
            MS.date = new SimpleDateFormat("jj-MM-yyyy").parse(H.get("DATE_FACTURE"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            DAO<Contact> DC = new DAOContact(new Contact());
            MS.destination = DC.recuperer(H.get("DESTINATION"));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
            try {
                DAO<Stock> DS = new StockDAO(new Stock());
                MS.destination = DS.recuperer(H.get("DESTINATION"));
            } catch (SQLException | NonExistantDansLaBDD e) {
                e.printStackTrace();
            }
        }
        try {
            DAO<Stock> DS = new StockDAO(new Stock());
            MS.source = DS.recuperer(H.get("SOURCE"));
        } catch (SQLException | NonExistantDansLaBDD e) {
            e.printStackTrace();
        }
        try {
            DAO<Employe> DE = new EmployesDAO(new Employe());
            MS.employe = DE.recuperer(H.get("EMPLOYE"));
        } catch (SQLException | NonExistantDansLaBDD e) {
            e.printStackTrace();
        }
        return MS;
    }
}
