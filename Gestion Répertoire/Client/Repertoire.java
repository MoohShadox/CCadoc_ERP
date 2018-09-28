import DAO.*;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.Modele;
import POJO.Contact;
import POJO.Mail;
import POJO.SiteWeb;
import POJO.TelFax;
import javafx.collections.*;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class Repertoire {
    //1er Changement notable c'est que j'ai changé l'organisation des collections
    //Ceci est la liste observable des contacts avec en face leur numéro
    private ObservableMap<Long,Modele_Contact<Contact>> contacts = FXCollections.emptyObservableMap();
    //Ceci est un tableau qui associe a chaque contact le set de ces mails (set c'est la même chose a peu pres que liste)
    private ObservableMap<Modele_Contact<Contact>,ObservableSet<Modele_Contact<Mail>>> section_mail = FXCollections.emptyObservableMap();
    //Ceci est un tableau qui associe a chaque contact le set de ces Tel et Fax (set c'est la même chose a peu pres que liste)
    private ObservableMap<Modele_Contact<Contact>,ObservableSet<Modele_Contact<TelFax>>> section_telfax = FXCollections.emptyObservableMap();
    //Ceci est un tableau qui associe a chaque contact le set de ces Sites Web (set c'est la même chose a peu pres que liste)
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
        //Ajouter un contact a R et vérifier qu'il est dans la BDD
        //Supprimer un contact dans R et vérifier qu'il s'enlève de la BDD

        //Partie 2 : Modification de quelque chose
        //Modification d'un contact et vérification que ça se modifie au niveau de la BDD
        //Modification d'un mail par exemple et vérification qu'il se modifie

        //Partie3 : Ajout de quelque chose ou supression de quelque chose
        //même chose que la modification mais tu supprime et tu ajoute des trucs



    }

    public void ajouter_mail_contact(Long c,Mail m){
        //TODO Fonction qui ajoute le mail m au contact c dans le répértoire les répércutions se ferrons automatiquement grace au listner
    }

    public void ajouter_tel_contact(Long c,TelFax m){
        //TODO Fonction qui ajoute le Tel/Fax m au contact c dans le répértoire les répércutions se ferrons automatiquement grace au listner
    }

    public void ajouter_Site_contact(Long c,SiteWeb m){
        //TODO Fonction qui ajoute le Site m au contact c dans le répértoire les répércutions se ferrons automatiquement grace au listner
    }

    //TODO Faire la même chose pour la supression et la modification

    public void modifier_mail(Long c,Mail M){
        for(Modele_Contact<Mail> MM : section_mail.get(c)){
            //Cherche le modele mail qui a la même adresse que le mail a modifier
            if(M.getAdresseMail().equalsIgnoreCase(MM.getSrc().getAdresseMail()))
            {

            }
                //TODO pour faire une modification il faut prévoir une méthode dans Modele_Contact qui modifie une propriété nommée par un String
        }

    }

    //La supression c'est pareil mais quand tu trouve le mail tu le garde de coté et en sortie de boucle tu le supprime de la liste observable en face de la clef du contact

    //TODO Créer les getters des différentes section



    public Repertoire(Collection<Contact> c) throws IllegalAccessException {
        contacts.addListener(new MapChangeListener<Long, Modele_Contact<Contact>>() {
            @Override
            public void onChanged(Change<? extends Long, ? extends Modele_Contact<Contact>> change) {
                //TODO Si j'ajoute un contact l'ajoute a la BDD lui et tout ces mails etc...

            }
        });
        for(Contact C:c)
        {
            ajouter_conctact(C);
        }
    }


    public void ajouter_conctact(Contact c) throws IllegalAccessException {
        //1er étape quand on ajoute un contact on l'ajoute a la liste des contacts
        Modele_Contact<Contact> MC = new Modele_Contact<Contact>(c);
        contacts.put(MC.getSrc().getNumContact(),MC);
        ObservableSet<Modele_Contact<Mail>> OBM = FXCollections.emptyObservableSet();
        for(Mail M:c.getMails()){
            OBM.add(new Modele_Contact<Mail>(M));
        }
        OBM.addListener(new SetChangeListener<Modele_Contact<Mail>>() {
            @Override
            public void onChanged(Change<? extends Modele_Contact<Mail>> change) {
                //TODO Ecrire une fonction qui quand j'ajoute un mail a un client l'ajoute dans la BDD (Update le mail avec comme numc C.numc bien sur) si il n'a pas encore été ajouté
                //TODO Pareil pour la supression

            }
        });
        ObservableSet<Modele_Contact<SiteWeb>> OBS = FXCollections.emptyObservableSet();
        for(SiteWeb SW:c.getSites()){
            OBS.add(new Modele_Contact<SiteWeb>(SW));
        }
        OBS.addListener(new SetChangeListener<Modele_Contact<SiteWeb>>() {
            @Override
            public void onChanged(Change<? extends Modele_Contact<SiteWeb>> change) {
                //TODO Same pour les Sites web
                //TODO Pareil pour la supression
            }
        });
        ObservableSet<Modele_Contact<TelFax>> OTF = FXCollections.emptyObservableSet();
        for(TelFax SW:c.getTels()){
            OTF.add(new Modele_Contact<TelFax>(SW));
        }
        OTF.addListener(new SetChangeListener<Modele_Contact<TelFax>>() {
            @Override
            public void onChanged(Change<? extends Modele_Contact<TelFax>> change) {
                //TODO Same pour les Tels et Fax
                //TODO Pareil pour la supression
            }
        });
        //Phase d'ajout aux dictionnaires :
        section_telfax.put(MC,OTF);
        section_mail.put(MC,OBM);
        section_sitew.put(MC,OBS);
    }

    public void supprimer_conctact(Contact c) throws IllegalAccessException {
        references.remove(c.getNumContact());
        for(Mail M:c.getMails()){
            section_mail.remove(c.getNumContact());
        }

        for(SiteWeb SW:c.getSites()){
            section_sitew.remove(c.getNumContact());
        }

        for(TelFax TF:c.getTels()){
            section_telfax.remove(c.getNumContact());
        }
    }


}
