package POJO;

import java.util.LinkedList;

public class Client {
    private static long cpt=0;
    private long numClient; //code client
    private String nom;
    private LinkedList<Contact> contacts= new LinkedList<>();

    public Client(){
        numClient =++cpt;
    }

    public long getNumClient() {
        return numClient;
    }

    public void setNumClient(long numClient) {
        this.numClient = numClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LinkedList<Contact> getContacts() {
        return contacts;
    }

    public void addContact(Contact c){
        if(!contacts.contains(c))
            contacts.add(c);
    }

    public void removeContact(Contact c){
        if(contacts.contains(c))
            contacts.remove(c);
    }

}
