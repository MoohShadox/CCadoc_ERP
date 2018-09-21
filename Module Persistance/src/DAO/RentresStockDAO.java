package DAO;

import Connections.ConnectionOrcl;
import Database_Entity.RentreStockEntity;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.Livre;
import POJO.Mouvement_Stock;
import POJO.Rentres;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class RentresStockDAO extends MouvementDAO {

    @Override
    public Mouvement_Stock recuperer(String isbn) throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD {
        Mouvement_Stock MS = super.recuperer(isbn);
        Rentres R = new Rentres(MS);
        Statement S = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS = S.executeQuery("SELECT ISBN,PRIX_ACQUISITION FROM SE_MOUVOIE WHERE REF_MOUV = \'"+isbn+"\'");
        while(RS.next()){
            Livre LL = null;
            for(Livre L:R.getLivres().keySet())
            {
                LL = L;
                if(L.getISBN().equalsIgnoreCase(RS.getString(1)))
                    break;
            }
            R.getPrix_acquisition().put(LL,RS.getFloat(2));
        }
        return R;
    }

    @Override
    public void ajouter(Mouvement_Stock T) throws SQLException, IllegalAccessException {
        if(!Rentres.class.isInstance(T))
            return;
        super.ajouter(T);
        Rentres R = (Rentres) T;
        Statement S = ConnectionOrcl.getInstance().createStatement();
        for(Livre LL:(R.getPrix_acquisition().keySet())){
            String requete = "UPDATE SE_MOUVOIE SET PRIX_ACQUISITION=" + R.getPrix_acquisition().get(LL).toString()+ " WHERE REF_MOUV=" + R.getReference();
            S.executeQuery(requete);
        }
    }

    public RentresStockDAO(Rentres T) throws SQLException, IllegalAccessException {
        super(T);
    }



}
