package Client;

import DAO.*;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.Contact;
import POJO.Mail;
import POJO.SiteWeb;
import POJO.TelFax;
import javafx.collections.*;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import java.sql.SQLException;
import java.util.Collection;

public class Repertoire {

    private ObservableMap<Long,Modele_Contact<Contact>> contacts = FXCollections.observableHashMap();
    private ObservableMap<Modele_Contact<Contact>,ObservableSet<Modele_Contact<Mail>>> section_mail = FXCollections.observableHashMap();
    private ObservableMap<Modele_Contact<Contact>,ObservableSet<Modele_Contact<TelFax>>> section_telfax = FXCollections.observableHashMap();
    private ObservableMap<Modele_Contact<Contact>,ObservableSet<Modele_Contact<SiteWeb>>> section_sitew = FXCollections.observableHashMap();


    public static void main(String[] s) throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException {
        //Ceci est le test unitaire
        /*Prérequis du test
        1-Avoir crée dans la BDD un enregistrement dans Contact qui s'appelle Racha Salhi
        2-Créer au moins deux numéros au contact dans la BDD Telfax, deux mails et disons trois avec comme numc le numc de Racha Salhi
        3-Avoir écris la fonction Load du DAOContact qui retourne une liste de tout les contacts de la BDD en utilisant la fonction récupérer
        4-Avoir redéfini la fonction équals de la class contact pour qu'elle compare les num
        5-Ajouter un constructeur vide au contact et un constructeur qui prend seulement en paramètre le num contact
         */

        //Partie 1 : Lecture et création du répértoire
        DAO<Contact> DC = new DAOContact(new Contact());
        Repertoire R = new Repertoire(DC.load()); //Repertoire crée avec comme base l'ensemble des enregistrement de type Contact
        //Lire le répértoire et l'afficher pour voire qu'il contient tout ce qui est sur la BDD
        for (Modele_Contact<Contact> C : R.contacts.values()) {
            System.out.println(C.getDescription());
        }
        //Ajouter un contact a R et vérifier qu'il est dans la BDD
        /*Contact C = new Contact();
        C.setDenomnation("Racha Salhi");
        R.ajouter_conctact(C);
        try{
            DC.recuperer("0");
            System.out.println("Trouvé !");
        }catch (NonExistantDansLaBDD nonExistantDansLaBDD){
            nonExistantDansLaBDD.printStackTrace();
        }*/
        //Supprimer un contact dans R et vérifier qu'il s'enlève de la BDD
        /*
        R.supprimer_contact(C1);
        try{
            DC.recuperer("7");
        }catch (NonExistantDansLaBDD nonExistantDansLaBDD){
            System.out.println("Supprimé !");
        }*/
        //Partie 2 : Modification de quelque chose
        //Modification d'un contact et vérification que ça se modifie au niveau de la BDD
        //Modification d'un mail par exemple et vérification qu'il se modifie

        //Partie3 : Ajout de quelque chose ou supression de quelque chose
        //même chose que la modification mais tu supprime et tu ajoute des trucs
        
        //R.supprimer_Mail_Contact((long) 7,m);;
    }

    public void ajouter_mail_contact(Long c,Mail m) throws IllegalAccessException {
        if(!contacts.get(c).getSrc().getMails().contains(m)) {
            contacts.get(c).getSrc().addMail(m);
            if (!section_mail.get(contacts.get(c)).contains(m.getModeleContact()))
                section_mail.get(contacts.get(c)).add(new Modele_Contact<Mail>(m));
        }
    }

    public void ajouter_tel_contact(Long c,TelFax tf) throws IllegalAccessException {
       if(!contacts.get(c).getSrc().getTels().contains(tf)) {
            contacts.get(c).getSrc().addTelFax(tf);
            if(!section_telfax.get(contacts.get(c)).contains(tf.getModeleContact()))
                section_telfax.get(contacts.get(c)).add(new Modele_Contact<TelFax>(tf));
        }
    }

    public void ajouter_Site_contact(Long c,SiteWeb sw) throws IllegalAccessException {
        if(!contacts.get(c).getSrc().getSites().contains(sw)) {
            contacts.get(c).getSrc().addSiteWeb(sw);
            if (!section_sitew.get(contacts.get(c)).contains(sw.getModeleContact()))
                section_sitew.get(contacts.get(c)).add(new Modele_Contact<SiteWeb>(sw));
        }
    }

    public void supprimer_Mail_Contact(Long c, Mail m) throws IllegalAccessException {
        if (contacts.get(c).getSrc().getMails().contains(m)) {
            Modele_Contact<Mail> M=new Modele_Contact<Mail>(new Mail());
            for (Modele_Contact<Mail> MM : section_mail.get(contacts.get(c))) {
                if (m.getAdresseMail().equals(MM.getSrc().getAdresseMail())) {
                    M=MM;
                }
            }
            contacts.get(c).getSrc().removeMail(m);
            section_mail.get(contacts.get(c)).remove(M);
        }
    }

    public void supprimer_TelFax_Contact(Long c, TelFax tf) throws IllegalAccessException {
        if (contacts.get(c).getSrc().getTels().contains(tf)) {
            Modele_Contact<TelFax> M=new Modele_Contact<TelFax>(new TelFax());
            for (Modele_Contact<TelFax> MM : section_telfax.get(contacts.get(c))) {
                if (tf.getNumero().equals(MM.getSrc().getNumero())) {
                    M=MM;
                }
            }
            contacts.get(c).getSrc().removeTelFax(tf);
            section_telfax.get(contacts.get(c)).remove(M);
        }
    }

    public void supprimer_SiteWeb_Contact(Long c, SiteWeb sw) throws IllegalAccessException {
        if (contacts.get(c).getSrc().getSites().contains(sw)) {
            Modele_Contact<SiteWeb> M=new Modele_Contact<>(new SiteWeb());
            for (Modele_Contact<SiteWeb> MM : section_sitew.get(contacts.get(c))) {
                if (sw.getUrl().equals(MM.getSrc().getUrl())) {
                    M=MM;
                }
            }
            contacts.get(c).getSrc().removeSiteWeb(sw);
            section_sitew.get(contacts.get(c)).remove(M);
        }
    }


    public void modifier_mail(Long c,Mail oldM, Mail newM) throws IllegalAccessException {
        for(Modele_Contact<Mail> MM : section_mail.get(contacts.get(c))){
            //Cherche le modele mail qui a la même adresse que le mail a modifier
            if(oldM.getAdresseMail().equalsIgnoreCase(MM.getSrc().getAdresseMail())){
                if(newM.getAdresseMail()!=null && !oldM.getAdresseMail().equalsIgnoreCase(newM.getAdresseMail())) {
                    MM.modifierProperty(MM.getDescription().get("ADRESSE_MAIL"), newM.getAdresseMail());
                    MM.getSrc().setAdresseMail(newM.getAdresseMail());
                }
                if(newM.getNomMail()!=null && !oldM.getNomMail().equalsIgnoreCase(newM.getNomMail())) {
                    MM.modifierProperty(MM.getDescription().get("NOM_MAIL"), newM.getNomMail());
                    MM.getSrc().setNomMail(newM.getNomMail());
                }
                if(newM.getTypeC()!=null && !oldM.getTypeC().equalsIgnoreCase(newM.getTypeC())) {
                    MM.modifierProperty(MM.getDescription().get("TYPEC"), newM.getTypeC());
                    MM.getSrc().setTypeC(newM.getTypeC());
                }
            }
        }
    }

    public void modifier_telFax(Long c,TelFax oldT, TelFax newT) throws IllegalAccessException {
        for(Modele_Contact<TelFax> MM : section_telfax.get(contacts.get(c))){
            if(oldT.getNumero().equalsIgnoreCase(MM.getSrc().getNumero())){
                if(newT.getNumero()!=null && !oldT.getNumero().equalsIgnoreCase(newT.getNumero())) {
                    MM.modifierProperty(MM.getDescription().get("NUMERO"), newT.getNumero());
                    MM.getSrc().setNumero(newT.getNumero());
                }
                if(oldT.isTelfax() != newT.isTelfax()) {
                    MM.modifierProperty(MM.getDescription().get("TELFAX"), String.valueOf(newT.isTelfax()));
                    MM.getSrc().setTelfax(newT.isTelfax());
                }
            }
        }
    }


    public void modifier_siteWeb(Long c,SiteWeb oldS, SiteWeb newS) throws IllegalAccessException {
        for(Modele_Contact<SiteWeb> MM : section_sitew.get(contacts.get(c))){
            if(oldS.getUrl().equalsIgnoreCase(MM.getSrc().getUrl())){
                if(newS.getUrl()!=null && !oldS.getUrl().equalsIgnoreCase(newS.getUrl())) {
                    MM.modifierProperty(MM.getDescription().get("URL"), newS.getUrl());
                    MM.getSrc().setUrl(newS.getUrl());
                }
                if(newS.getDescription()!=null && !oldS.getDescription().equalsIgnoreCase(newS.getDescription())) {
                    MM.modifierProperty(MM.getDescription().get("DESCRIPTION_URL"), newS.getDescription());
                    MM.getSrc().setDescription(newS.getDescription());
                }
            }
        }
    }


    public ObservableMap<Modele_Contact<Contact>, ObservableSet<Modele_Contact<Mail>>> getSection_mail() {
        return section_mail;
    }

    public ObservableMap<Modele_Contact<Contact>, ObservableSet<Modele_Contact<TelFax>>> getSection_telfax() {
        return section_telfax;
    }

    public ObservableMap<Modele_Contact<Contact>, ObservableSet<Modele_Contact<SiteWeb>>> getSection_sitew() {
        return section_sitew;
    }

    public Repertoire(Collection<Contact> c) throws IllegalAccessException {
        contacts.addListener(new MapChangeListener<Long, Modele_Contact<Contact>>() {
            @Override
            public void onChanged(Change<? extends Long, ? extends Modele_Contact<Contact>> change) {
                try {
                    DAO<Contact> DC = new DAOContact(new Contact());
                    if (change.wasAdded()) {
                        try {
                            DC.recuperer(change.getValueAdded().getSrc().getReference());
                        } catch (IllegalAccessException e1) {
                            e1.printStackTrace();
                        } catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
                            DC.ajouter(change.getValueAdded().getSrc());
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        } catch (BuildingException e1) {
                            e1.printStackTrace();
                        }
                    }else if(change.wasRemoved())
                        DC.supprimer(change.getValueRemoved().getSrc());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        for (Contact C:c) {
            ajouter_conctact(C);
        }
    }


    public void ajouter_conctact(Contact c) throws IllegalAccessException {
        //1er étape quand on ajoute un contact on l'ajoute a la liste des contacts
        Modele_Contact<Contact> MC = new Modele_Contact<Contact>(c);
        contacts.put(MC.getSrc().getNumContact(),MC);
        ObservableSet<Modele_Contact<Mail>> OBM = FXCollections.observableSet();
        OBM.addListener(new SetChangeListener<Modele_Contact<Mail>>() {
            @Override
            public void onChanged(Change<? extends Modele_Contact<Mail>> change) {
                try {
                    DAO<Mail> DM = new DAOMail(new Mail());
                    repercutionBDD(DM,change);
                    if(change.wasAdded())
                        ((DAOMail) DM).updateNumC(c.getNumContact(),change.getElementAdded().getSrc());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (BuildingException e) {
                    e.printStackTrace();
                }
            }
            });
        for(Mail M:c.getMails()){
            OBM.add(new Modele_Contact<Mail>(M));
        }
        ObservableSet<Modele_Contact<SiteWeb>> OBS = FXCollections.observableSet();
        OBS.addListener(new SetChangeListener<Modele_Contact<SiteWeb>>() {
            @Override
            public void onChanged(Change<? extends Modele_Contact<SiteWeb>> change) {
               try {
                    DAO<SiteWeb> DSW = new DAOSiteWeb(new SiteWeb());
                    repercutionBDD(DSW,change);
                    if(change.wasAdded())
                        ((DAOSiteWeb) DSW).updateNumC(c.getNumContact(),change.getElementAdded().getSrc());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (BuildingException e) {
                    e.printStackTrace();
                }
            }
        });
        for(SiteWeb SW:c.getSites()){
            OBS.add(new Modele_Contact<SiteWeb>(SW));
        }
        ObservableSet<Modele_Contact<TelFax>> OTF = FXCollections.observableSet();
        OTF.addListener(new SetChangeListener<Modele_Contact<TelFax>>() {
            @Override
            public void onChanged(Change<? extends Modele_Contact<TelFax>> change) {
                try {
                    DAO<TelFax> DTF = new DAOTelFax(new TelFax());
                    repercutionBDD(DTF,change);
                    if(change.wasAdded())
                        ((DAOTelFax) DTF).updateNumC(c.getNumContact(),change.getElementAdded().getSrc());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (BuildingException e) {
                    e.printStackTrace();
                }
            }
        });
        for(TelFax SW:c.getTels()){
            OTF.add(new Modele_Contact<TelFax>(SW));
        }
        //Phase d'ajout aux dictionnaires :
        section_telfax.put(MC,OTF);
        section_mail.put(MC,OBM);
        section_sitew.put(MC,OBS);
    }

    public void supprimer_contact(Contact c) throws IllegalAccessException {
            section_mail.remove(c.getModele());
            section_sitew.remove(c.getModele());
            section_telfax.remove(c.getModele());
            contacts.remove(c.getNumContact());
        }


    public void repercutionBDD(DAO D, SetChangeListener.Change<? extends Modele_Contact> change) throws IllegalAccessException, BuildingException, SQLException {
        if(change.wasAdded()){
            try{
                D.recuperer(change.getElementAdded().getSrc().getReference());
            } catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
                D.ajouter(change.getElementAdded().getSrc());
            }
        }else if(change.wasRemoved())
            D.supprimer(change.getElementRemoved().getSrc());
    }

}
