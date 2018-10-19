package Presentation.Controller;

import Client.Repertoire;
import Composants_Visuels.WindowButtons;
import DAO.*;
import DAO.Modele_Contact;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.Descriptible;
import POJO.Contact;
import POJO.Mail;
import POJO.SiteWeb;
import POJO.TelFax;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Gestionnaire_Repertoire extends WindowButtons  {

    protected static DAO<Contact> DC;
    protected static Repertoire R;
    static {
        try {
            DC = new DAOContact(new Contact());
            R=new Repertoire(DC.load());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (BuildingException e) {
            e.printStackTrace();
        } catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
            nonExistantDansLaBDD.printStackTrace();
        }
    }


    public abstract void provideTable() throws IllegalAccessException;

    protected void  customResize(TreeTableView view) {

        AtomicLong width = new AtomicLong();
        Iterator<TreeTableColumn> i=view.getColumns().iterator();
        while (i.hasNext()){
            width.addAndGet((long) i.next().getWidth());
        }

        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            i=view.getColumns().iterator();
            while (i.hasNext()){
                TreeTableColumn t=i.next();
                t.setPrefWidth(t.getWidth()+(tableWidth - width.get()) / (view.getColumns().size()));
            }
        }
    }


    protected void modifierRow(Repertoire R,boolean keyChanging, Long c, Modele_Contact<? extends Descriptible> M, String newValue) throws IllegalAccessException{
        if(M.getSrc() instanceof Mail) {
            //Ca marche pas quand je fais Mail m=(Mail) M.getSrc();
            //car la il prend l instance elle meme de M.getSrc et quand j affecte la nvelle valeur a m , il l affecte a M.getSrc aussi C'EST TRéS CON IKKKKKK
            //so faudra chercher a le faire d'une façon plus optimisée
            Mail m =new Mail(((Mail) M.getSrc()).getAdresseMail(),((Mail) M.getSrc()).getTypeC(),((Mail) M.getSrc()).getNomMail());
            if(keyChanging)
                m.setAdresseMail(newValue);
            else
                m.setNomMail(newValue);
            R.modifier_mail(c, (Mail) M.getSrc(), m);
        }else if(M.getSrc() instanceof TelFax){
            TelFax tf =new TelFax(((TelFax) M.getSrc()).getNumero(),((TelFax) M.getSrc()).getTelfax());
            if(keyChanging)
                tf.setNumero(newValue);
            else
                tf.setTelfax(newValue);
            R.modifier_telFax(c, (TelFax) M.getSrc(), tf);
        }else if(M.getSrc() instanceof SiteWeb){
            SiteWeb sw = new SiteWeb(((SiteWeb) M.getSrc()).getUrl(),((SiteWeb) M.getSrc()).getDescription());
            if(keyChanging)
                sw.setUrl(newValue);
            else
                sw.setDescription(newValue);
            R.modifier_siteWeb(c, (SiteWeb) M.getSrc(), sw);
        }
    }

    protected ObservableList<String> remplissageListMail(Contact contactCourant){
        ObservableList<String> o1= FXCollections.observableArrayList();
        Iterator<Mail> i=contactCourant.getMails().iterator();
        while (i.hasNext()){
            o1.add(i.next().getAdresseMail());
        }
        return o1;
    }

    protected ObservableList<String> remplissageListTelFax(Contact contactCourant){
        ObservableList<String> o1= FXCollections.observableArrayList();
        Iterator<TelFax> i=contactCourant.getTels().iterator();
        while (i.hasNext()){
            o1.add(i.next().getNumero());
        }
        return o1;
    }

    protected ObservableList<String> remplissageListSiteWeb(Contact contactCourant){
        ObservableList<String> o1= FXCollections.observableArrayList();
        Iterator<SiteWeb> i=contactCourant.getSites().iterator();
        while (i.hasNext()){
            o1.add(i.next().getUrl());
        }
        return o1;
    }

    protected Object getObject(LinkedList l, String s){
        for (Object d : l) {
            if (d instanceof Mail){
                if (((Mail) d).getAdresseMail().equals(s))
                    return d;
            }
            if (d instanceof TelFax) {
                if (((TelFax) d).getNumero().equals(s))
                    return d;
            }
            if (d instanceof SiteWeb) {
                if (((SiteWeb) d).getUrl().equals(s))
                    return d;
            }
        }
        return null;
    }

}

