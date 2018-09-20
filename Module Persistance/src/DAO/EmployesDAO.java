package DAO;

import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.Employe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

public class EmployesDAO extends GenericDAO<Employe> {

    public EmployesDAO(Employe T) throws SQLException, IllegalAccessException {
        super(T);
    }

    @Override
    public Collection<Employe> load() throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD, Exceptions.NonExistantDansLaBDD {
        LinkedList<Employe> L = new LinkedList<>();
        Statement S = c.createStatement();
        ResultSet RS = S.executeQuery("SELECT NUM_E FROM EMPLOYE");

        while(RS.next()){
            Employe E = new Employe();
            E = recuperer(RS.getString(1));
            L.add(E);
        }
        return L;
    }


}
