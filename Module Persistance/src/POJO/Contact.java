package POJO;

import java.util.LinkedList;

public class Contact {
    private static long cpt=0;
    private long numContact; //numéro séquentiel
    private String denomnation,adresse,type;
    private LinkedList<Mail> mails=new LinkedList<>();
    private LinkedList<SiteWeb> sites=new LinkedList<>();
    private LinkedList<TelFax> tels=new LinkedList<>();

    public Contact(){
        numContact =++cpt;
    }

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

    @Override
    public String toString() {
        return denomnation;
    }
}
