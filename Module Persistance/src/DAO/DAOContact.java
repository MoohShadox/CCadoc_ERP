package DAO;

import Connections.ConnectionOrcl;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.Contact;
import POJO.Mail;
import POJO.SiteWeb;
import POJO.TelFax;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class DAOContact extends GenericDAO<Contact> {

    public static void main(String[] s) throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException {
        DAO<Contact> DC = new DAOContact(new Contact());
        Contact c=DC.recuperer("1");

        DC.supprimer(c);
    }

    public DAOContact(Contact T) throws SQLException, IllegalAccessException {
        super(T);
    }

    @Override
    public Contact recuperer(String isbn) throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD {
        Contact C = super.recuperer(isbn);
        Statement S = ConnectionOrcl.getInstance().createStatement();
        DAO<Mail> DM = new DAOMail(new Mail());
        ResultSet RS = S.executeQuery("SELECT MAIL.ADRESSE_MAIL FROM CADOC_ADMIN.MAIL WHERE NUMC =\'"+isbn+"\'");
        while(RS.next()){
            Mail M = DM.recuperer(RS.getString(1));
            C.addMail(M);
        }
        DAO<TelFax> DTF = new DAOTelFax(new TelFax());
        RS = S.executeQuery("SELECT TELFAX.NUMERO FROM CADOC_ADMIN.TELFAX WHERE NUMC =\'"+isbn+"\'");
        while(RS.next()){
            TelFax M = DTF.recuperer(RS.getString(1));
            C.addTelFax(M);
        }
        DAO<SiteWeb> DSW = new DAOSiteWeb(new SiteWeb());
        RS = S.executeQuery("SELECT URL FROM CADOC_ADMIN.SITEW WHERE NUMC =\'"+isbn+"\'");
        while(RS.next()){
            SiteWeb SW = DSW.recuperer(RS.getString(1));
            C.addSiteWeb(SW);
        }
        return C;

    }


    @Override
    public void ajouter(Contact T) throws SQLException, IllegalAccessException {
        super.ajouter(T);
        Statement S = ConnectionOrcl.getInstance().createStatement();
        DAO<SiteWeb> DSW = new DAOSiteWeb(new SiteWeb());
        DAO<TelFax> DTF = new DAOTelFax(new TelFax());
        DAO<Mail> DM = new DAOMail(new Mail());
        for(SiteWeb SS:T.getSites()) {
            try {
                DSW.recuperer(SS.getReference());
            }
            catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
                DSW.ajouter(SS);
            } catch (BuildingException e) {
                e.printStackTrace();
            }
        }
        for(TelFax TF:T.getTels()){
            try {
                DTF.recuperer(TF.getReference());
            } catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
                DTF.ajouter(TF);
            } catch (BuildingException e) {
                e.printStackTrace();
            }
        }
        for(Mail M:T.getMails()){
            try {
                DM.recuperer(M.getReference());
            }catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
                DM.ajouter(M);
            } catch (BuildingException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void supprimer(Contact T) throws SQLException, IllegalAccessException {
        super.supprimer(T);
        Statement S = ConnectionOrcl.getInstance().createStatement();
        DAO<SiteWeb> DSW = new DAOSiteWeb(new SiteWeb());
        DAO<TelFax> DTF = new DAOTelFax(new TelFax());
        DAO<Mail> DM = new DAOMail(new Mail());
        for(SiteWeb SS:T.getSites()) {
            try {
                DSW.recuperer(SS.getReference());
                DSW.supprimer(SS);
            }
            catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
                nonExistantDansLaBDD.printStackTrace();
            } catch (BuildingException e) {
                e.printStackTrace();
            }
        }
        for(TelFax TF:T.getTels()){
            try {
                DTF.recuperer(TF.getReference());
                DTF.supprimer(TF);
            } catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
                nonExistantDansLaBDD.printStackTrace();
            } catch (BuildingException e) {
                e.printStackTrace();
            }
        }
        for(Mail M:T.getMails()){
            try {
                DM.recuperer(M.getReference());
                DM.supprimer(M);
            }catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
                nonExistantDansLaBDD.printStackTrace();
            } catch (BuildingException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public Collection<Contact> load() throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD, NonExistantDansLaBDD {
        return null;
    }
}
