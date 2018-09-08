package DAO;

import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;

import POJO.Livre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

public class DAOLivre extends GenericDAO<Livre> {

    public DAOLivre(Livre T) throws SQLException, IllegalAccessException {
        super ( T );
    }

    @Override
    public Collection <Livre> load() throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD {
        LinkedList <Livre> L = new LinkedList <> ( );
        Statement S = c.createStatement ( );
        ResultSet RS = S.executeQuery ( "SELECT " + objet.getKeyName ( ) + " FROM " + objet.getTableName ( ) + "" );
        Livre LL = new Livre ( );
        while (RS.next ( )) {
            L.add ( recuperer ( RS.getString ( 1 ) ) );
            objet = new Livre ( );
        }
        S.close ( );
        RS.close ( );
        return L;
    }



}
