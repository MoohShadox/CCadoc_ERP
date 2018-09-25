import DAO.*;
import POJO.Contact;
import POJO.Mail;
import POJO.SiteWeb;
import POJO.TelFax;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.sql.SQLException;
import java.util.LinkedList;

public class Repertoire {
    private ObservableMap<Long, Modele_Contact<Contact>> references = FXCollections.observableHashMap();
    private ObservableMap<Long,Modele_Contact<Mail>> section_mail = FXCollections.emptyObservableMap();
    private ObservableMap<Long,Modele_Contact<TelFax>> section_telfax = FXCollections.emptyObservableMap();
    private ObservableMap<Long,Modele_Contact<SiteWeb>> section_sitew = FXCollections.observableHashMap();


    public Repertoire(LinkedList<Contact> c){
        section_mail.addListener(new MapChangeListener<Long, Modele_Contact<Mail>>() {
            @Override
            public void onChanged(Change<? extends Long, ? extends Modele_Contact<Mail>> change) {
                if(!references.containsKey(change.getKey())){
                    try {
                        DAO<Mail> DM = new DAOMail(new Mail());
                        if(change.wasAdded())
                            DM.ajouter(change.getValueAdded().getSrc());
                        else if(change.wasRemoved())
                            DM.supprimer(change.getValueRemoved().getSrc());
                    } catch (SQLException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        section_sitew.addListener(new MapChangeListener<Long, Modele_Contact<SiteWeb>>() {
            @Override
            public void onChanged(Change<? extends Long, ? extends Modele_Contact<SiteWeb>> change) {
                if(!references.containsKey(change.getKey())){
                    try {
                        DAO<SiteWeb> DSW = new DAOSiteWeb(new SiteWeb());
                        if(change.wasAdded())
                            DSW.ajouter(change.getValueAdded().getSrc());
                        else if(change.wasRemoved())
                            DSW.supprimer(change.getValueRemoved().getSrc());
                    } catch (SQLException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        section_telfax.addListener(new MapChangeListener<Long, Modele_Contact<TelFax>>() {
            @Override
            public void onChanged(Change<? extends Long, ? extends Modele_Contact<TelFax>> change) {
                if(!references.containsKey(change.getKey())){
                    try {
                        DAO<TelFax> DTF = new DAOTelFax(new TelFax());
                        if ((change.wasAdded()))
                            DTF.ajouter(change.getValueAdded().getSrc());
                        else if(change.wasRemoved())
                            DTF.supprimer(change.getValueRemoved().getSrc());
                    } catch (SQLException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    public void ajouter_conctact(Contact c) throws IllegalAccessException {
        references.put(c.getNumContact(),new Modele_Contact<Contact>(c));
        for(Mail M:c.getMails()){
            section_mail.put(c.getNumContact(),new Modele_Contact<Mail>(M));
        }

        for(SiteWeb SW:c.getSites()){
            section_sitew.put(c.getNumContact(),new Modele_Contact<SiteWeb>(SW));
        }

        for(TelFax TF:c.getTels()){
            section_telfax.put(c.getNumContact(),new Modele_Contact<TelFax>(TF));
        }
    }


}
