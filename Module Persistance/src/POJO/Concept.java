package POJO;

import java.util.LinkedList;

public class Concept {
    private String nom;
    private LinkedList<Livre> livres=new LinkedList<>();
    private String responsable; //numéro de l'employé responsable du concept

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public LinkedList<Livre> getLivres() {
        return livres;
    }

    public void setLivres(LinkedList<Livre> livres) {
        this.livres = livres;
    }

    public void addLivre(Livre l){
        if(!livres.contains(l))
            livres.add(l);
    }

    public void removeLivre(Livre l){
        if(!livres.contains(l))
            livres.remove(l);
    }

}
