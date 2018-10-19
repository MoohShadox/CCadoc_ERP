package DAO;

import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.Contact;
import POJO.Destination;
import POJO.Stock;

import java.sql.SQLException;
import java.util.Collection;

import static javafx.scene.input.KeyCode.T;

public class DAODestination extends DAO<Destination> {
    DAO D;


    @Override
    public void ajouter(Destination T) throws SQLException, IllegalAccessException {
        switch (T.getClass().getName())
        {
            case "Stock":
                DAO<Stock> DS = new StockDAO(new Stock());
                DS.ajouter((Stock) T);
                break;
            case "Contact":
                DAO<Contact> DC = new DAOContact(new Contact());
                DC.ajouter((Contact) T);
        }

    }

    @Override
    public void supprimer(Destination T) throws SQLException, IllegalAccessException {
        D.supprimer(T);
    }

    @Override
    public void supprimer(String isbn) throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException {

    }

    @Override
    public Destination recuperer(String isbn) throws SQLException, BuildingException, IllegalAccessException, BuildingException {
        DAO<Contact> DC = new DAOContact(new Contact());
        DAO<Stock> DS = new StockDAO(new Stock());
        try {
            return DC.recuperer(isbn);
        } catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
            try {
                return DS.recuperer(isbn);
            } catch (NonExistantDansLaBDD nonExistantDansLaBDD1) {
                System.out.println("Récupération de la destination impossible");
                nonExistantDansLaBDD1.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Collection<Destination> load() throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD, NonExistantDansLaBDD {
        return null;
    }

    @Override
    public Destination mettre_a_jour(Destination T, String reference) throws SQLException, NonExistantDansLaBDD, IllegalAccessException {

        return null;
    }
}
