package Repository;

import Connections.ConnectionOrcl;
import DAO.GenericDAO;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.Inventaire;
import POJO.Inventorier;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class InventaireDAO extends GenericDAO<Inventaire> {

    public InventaireDAO(Inventaire T) throws SQLException, IllegalAccessException {
        super(T);
    }

    @Override
    public Inventaire recuperer(String isbn) throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD {
        int qte;
        Inventorier Inv;
        Inventaire I = super.recuperer(isbn);
        Statement S = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS = S.executeQuery("SELECT ISBN,QTE FROM EST_INVENTORIER WHERE INVENTAIRE=\'"+isbn+"\'");
        while (RS.next()){
            qte = RS.getInt(2);
            try {
                Inv = new Inventorier(RS.getString(1),qte);
                I.getRecensement().ajouter(Inv);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return I;
    }

    @Override
    public Collection<Inventaire> load() throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD {
        return null;
    }
}
