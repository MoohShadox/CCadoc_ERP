package DAO;

import Connections.ConnectionOrcl;
import Exceptions.BuildingException;
import Exceptions.ExistantDansLaPieceException;
import Exceptions.NonExistantDansLaBDD;
import POJO.Livre;
import POJO.Piece;
import POJO.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class PieceDAO extends GenericDAO<Piece> {
    public PieceDAO(Piece T) throws SQLException, IllegalAccessException {
        super(T);
    }

    @Override
    public Piece recuperer(String isbn) throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD {
        DAO<Livre> DM = new DAOLivre(new Livre());
        DAO<Stock> DS = new StockDAO(new Stock());
        Piece P = super.recuperer(isbn);
        Statement S = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS = S.executeQuery("SELECT ISBN,QUANTITE,PRIX_UNITAIRE,NOM_S FROM CADOC_ADMIN.EST_FACTURE_DANS WHERE REFERENCE=\'"+P.getReference()+"\'");
        while (RS.next()){
            Livre L = DM.recuperer(RS.getString(1));
            Stock ST = DS.recuperer(RS.getString(4));
            float prix = RS.getFloat(3);
            int qte = RS.getInt(2);
            try {
                P.ajouter_livre(ST,L,prix,qte);
            } catch (ExistantDansLaPieceException e) {
                e.printStackTrace();
            }
        }

        return P;
    }

    @Override
    public Collection<Piece> load() throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD, NonExistantDansLaBDD {
        return null;
    }
}
