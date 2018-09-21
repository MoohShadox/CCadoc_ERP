package DAO;

import Connections.ConnectionOrcl;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.Livre;
import POJO.Mouvement_Stock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class MouvementDAO extends GenericDAO<Mouvement_Stock> {

    @Override
    public Mouvement_Stock recuperer(String isbn) throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD {
        Mouvement_Stock MS = super.recuperer(isbn);
        Statement S = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS = S.executeQuery("SELECT ISBN,QTE FROM SE_MOUVOIE WHERE REF_MOUV=\'"+isbn+"\'");
        DAO<Livre> DL = new DAOLivre(new Livre());
        while(RS.next()){
            Livre L = DL.recuperer(RS.getString(1));
            MS.getLivres().put(L,RS.getInt(2));
        }
        return MS;
    }

    @Override
    public void ajouter(Mouvement_Stock T) throws SQLException, IllegalAccessException {
        super.ajouter(T);
        PreparedStatement PS = ConnectionOrcl.getInstance().prepareStatement("INSERT INTO SE_MOUVOIE(ISBN, QTE) VALUES (?,?)");
        for(Livre L:T.getLivres().keySet()){
            PS.setString(1,L.getISBN());
            PS.setFloat(2,T.getLivres().get(L));
            PS.executeUpdate();
        }
    }

    public MouvementDAO(Mouvement_Stock T) throws SQLException, IllegalAccessException {
        super(T);
    }

    @Override
    public Collection<Mouvement_Stock> load() throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD, NonExistantDansLaBDD {
        return null;
    }
}
