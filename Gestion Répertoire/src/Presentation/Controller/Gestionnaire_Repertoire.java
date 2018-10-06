package Presentation.Controller;

import Client.Repertoire;
import Composants_Visuels.WindowButtons;
import DAO.Modele_Contact;
import Interfaces.Controllable;
import Interfaces.Descriptible;
import POJO.Contact;
import POJO.Mail;
import POJO.SiteWeb;
import POJO.TelFax;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Gestionnaire_Repertoire extends WindowButtons  {

    public abstract void provideTable() throws IllegalAccessException;

    public void  customResize(TreeTableView view) {

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


    public void modifierRow(Repertoire R,boolean keyChanging, Long c, Modele_Contact<? extends Descriptible> M, String newValue) throws IllegalAccessException{
        if(M.getSrc() instanceof Mail) {
            //Ca marche pas quand je fais Mail m=(Mail) M.getSrc();
            //car la il prend l instance elle meme de M.getSrc et quand j affecte la nvelle valeur a m , il l affecte a M.getSrc aussi C'EST TRéS CON IKKKKKK
            //so faudra chercher a le faire d'une façon plus optimisée
            Mail m =new Mail();
            m.setNomMail(((Mail) M.getSrc()).getNomMail());
            m.setAdresseMail(((Mail) M.getSrc()).getAdresseMail());
            if(keyChanging)
                m.setAdresseMail(newValue);
            else
                m.setNomMail(newValue);
            m.setTypeC(((Mail) M.getSrc()).getTypeC());
            System.out.println(((Mail) M.getSrc()).getAdresseMail()+"   "+newValue);
            R.modifier_mail(c, (Mail) M.getSrc(), m);
        }else if(M.getSrc() instanceof TelFax){
            TelFax tf =new TelFax();
            tf.setNumero(((TelFax) M.getSrc()).getNumero());
            tf.setTelfax(((TelFax) M.getSrc()).getTelfax());
            if(keyChanging)
                tf.setNumero(newValue);
            else
                tf.setTelfax(newValue);
            R.modifier_telFax(c, (TelFax) M.getSrc(), tf);
        }else if(M.getSrc() instanceof SiteWeb){
            SiteWeb sw = new SiteWeb();
            sw.setDescription(((SiteWeb) M.getSrc()).getDescription());
            sw.setUrl(((SiteWeb) M.getSrc()).getUrl());
            if(keyChanging)
                sw.setUrl(newValue);
            else
                sw.setDescription(newValue);
            R.modifier_siteWeb(c, (SiteWeb) M.getSrc(), sw);
        }
    }
}
