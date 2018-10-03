package Interfaces;

import DAO.Modele_Contact;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.DAOAble;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.HashMap;

public interface Descriptible<Type> extends DAOAble<Type> {
    void Maj_BDD(String attribut,String nouvelle_valeur,String ref) throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException;
    boolean verfier(String s);
    Modele_Contact<? extends Descriptible> getModeleContact()throws IllegalAccessException;
}
