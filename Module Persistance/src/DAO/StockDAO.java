package DAO;

import Connections.ConnectionOrcl;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.Livre;
import POJO.Stock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Collection;

public class StockDAO extends GenericDAO<Stock> {

    public static void main(String s[]) throws SQLException, IllegalAccessException, ParseException, NonExistantDansLaBDD, BuildingException {
        StockDAO SD = new StockDAO(new Stock());
        /*Stock S = new Stock();
        S.setNom("ST2");
        SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
        S.setDate_realisation(SDF.parse("12-03-2005"));
        S.setLocalisation("Alger");
        Livre LL = new Livre();
        LL.setISBN("9782889380053");
        S.addLivre(LL,10);
        SD.ajouter(S);
*/
        Stock SSS = SD.recuperer("ST1");
        System.out.println(SSS.getNom());
    }


    public void corriger(String isbn,int qte,Stock S) throws SQLException {
        PreparedStatement PS = c.prepareStatement("UPDATE EST_DANS SET QUANTITE=\'"+qte+"\' WHERE NOM_S=\'"+S.getNom()+"\' AND ISBN=\'"+isbn+"\'");
        PS.executeUpdate();
    }

    public void corriger(String isbn,int qte,String S) throws SQLException {
        Statement St = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS = St.executeQuery("SELECT * FROM EST_DANS WHERE NOM_S='"+S+"' AND ISBN='"+isbn+"'");
        PreparedStatement PS;
        if(!RS.next())
        {
            PS = ConnectionOrcl.getInstance().prepareStatement("INSERT INTO EST_DANS(QUANTITE, ISBN, NOM_S) VALUES (?,?,?)");
            PS.setInt(1,qte);
            PS.setString(2,isbn);
            PS.setString(3,S);
            PS.executeUpdate();
        }
        else
        {
            PS = c.prepareStatement("UPDATE EST_DANS SET QUANTITE=\'"+qte+"\' WHERE NOM_S=\'"+S+"\' AND ISBN=\'"+isbn+"\'");
            PS.executeUpdate();
        }

    }



    public StockDAO(Stock T) throws SQLException, IllegalAccessException {
        super(T);
    }

    @Override
    public void ajouter(Stock T) throws SQLException, IllegalAccessException {
        super.ajouter(T);
        DAOLivre D = new DAOLivre(new Livre());
        for(Livre L:T.getLivres().keySet()){
            try
            {
                D.recuperer(L.getISBN());
            } catch (BuildingException e) {
                e.printStackTrace();
            } catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
                D.ajouter(L);
            }
            //
            int qte=T.getLivres().get(L);
            Statement S = ConnectionOrcl.getInstance().createStatement();
            ResultSet RS = S.executeQuery("SELECT QUANTITE FROM EST_DANS WHERE ISBN=\'"+L.getISBN()+"\' AND NOM_S=\'"+T.getNom()+"\'");
            if(RS.next()){
                qte += RS.getInt(1);
                S.executeUpdate("UPDATE EST_DANS SET QUANTITE=\'"+qte+"\' WHERE NOM_S=\'"+T.getNom()+"\' AND ISBN=\'"+L.getISBN()+"\'");
            }
            else
            {
                PreparedStatement PS = ConnectionOrcl.getInstance().prepareStatement("INSERT INTO EST_DANS(QUANTITE,ISBN,NOM_S) VALUES (?,?,?) ");
                PS.setInt(1,qte);
                PS.setString(2,L.getISBN());
                PS.setString(3,T.getNom());
                PS.executeUpdate();
            }

        }
    }


    @Override
    public Stock recuperer(String isbn) throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD {
        Stock ST =  super.recuperer(isbn);
        Statement S = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS = S.executeQuery("SELECT ISBN,QUANTITE FROM EST_DANS WHERE NOM_S=\'"+isbn+"\'");
        DAOLivre DL = new DAOLivre(new Livre());
        while (RS.next()){
            Livre L = DL.recuperer(RS.getString(1));
            ST.addLivre(L,RS.getInt(2));
        }
        return ST;
    }

    @Override
    public Collection<Stock> load() throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD {
        return null;
    }

    public void ajouterStock(Livre L){

    }





}
