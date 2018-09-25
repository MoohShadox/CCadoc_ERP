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
                        DM.ajouter(change.getValueAdded().getSrc());
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
        //TEL FAX
        //SITE WEB
    }


}
