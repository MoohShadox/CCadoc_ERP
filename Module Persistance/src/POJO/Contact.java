package POJO;

import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.DAOAble;
import Interfaces.Descriptible;
import Interfaces.Modele;
import Interfaces.Visualisable;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public class Contact implements DAOAble<Contact>,Destination, Visualisable, Descriptible<Contact> {

    private long numContact; //numéro séquentiel
    private String denomnation,adresse,type;

    private LinkedList<Mail> mails=new LinkedList<>();
    private LinkedList<SiteWeb> sites=new LinkedList<>();
    private LinkedList<TelFax> tels=new LinkedList<>();

    //TODO Ajouter les controles relatifs au champs Mails, Site Web, TelFax


    public long getNumContact() {
        return numContact;
    }

    public void setNumContact(long numContact) {
        this.numContact = numContact;
    }

    public String getDenomnation() {
        return denomnation;
    }

    public void setDenomnation(String denomnation) {
        this.denomnation = denomnation;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public void addMail(Mail m){
        if(!mails.contains(m))
            mails.add(m);
    }

    public void removeMail(Mail m){
        if(mails.contains(m))
            mails.remove(m);
    }

    public void addSiteWeb(SiteWeb s){
        if(!sites.contains(s))
            sites.add(s);
    }

    public void removreSiteWeb(SiteWeb s){
        if(sites.contains(s))
            sites.remove(s);
    }

    public void addTelFax(TelFax t){
        if(!tels.contains(t))
            tels.add(t);
    }

    public void removeTelFax(TelFax t){
        if(tels.contains(t))
            tels.remove(t);
    }

    public LinkedList<Mail> getMails() {
        return mails;
    }

    public LinkedList<SiteWeb> getSites() {
        return sites;
    }

    public LinkedList<TelFax> getTels() {
        return tels;
    }

    @Override
    public String toString() {
        return denomnation;
    }

    @Override
    public HashMap<String, String> getRepositoryAttributs() throws IllegalAccessException {
        HashMap<String,String> H = new HashMap<>();
        H.put("NUMC",numContact+"");
        H.put("DENOMINATION",denomnation);
        H.put("ADRESSE",adresse);
        H.put("TYPE",type);
        return H;
    }

    @Override
    public String getTableName() {
        return "CONTACT";
    }

    @Override
    public String getReference() {
        return numContact+"";
    }

    @Override
    public HashMap<String, String> getPrincipalAttributes() throws IllegalAccessException {
        return getRepositoryAttributs();
    }

    @Override
    public Modele<Visualisable> getModele() throws IllegalAccessException {
        return new Modele<>(this);
    }

    @Override
    public String getKeyName() {
        return "NUMC";
    }

    @Override
    public Contact buildFromRepData(HashMap<String, String> H) throws BuildingException, IllegalAccessException {
        Contact C = new Contact();
        if(H.containsKey("NUMC"))
            C.numContact = Integer.valueOf(H.get("NUMC"));
        if(H.containsKey("DENOMINATION"))
            C.denomnation= H.get("DENOMINATION");
        if(H.containsKey("ADRESSE"))
            C.adresse = H.get("ADRESSE");
        if(H.containsKey("TYPE"))
            C.type = H.get("TYPE");
        return C;
    }

    @Override
    public void Maj_BDD(String attribut, String nouvelle_valeur, String ref) throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException {

    }

    @Override
    public boolean verfier(String s) {
        return false;
    }
}
