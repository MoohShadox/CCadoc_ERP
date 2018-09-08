package POJO;

import java.util.Date;
import java.util.LinkedList;

public class Piece {
    //Element de la pièce
    LinkedList <Livre> contenu = new LinkedList <> ( );
    private String reference;
    private Date DateF;
    private Types_Pieces typesPiece;
    private Type_Transaction typeTransaction;
    private Date delai;
    private boolean rempli;
    private Client numClientConcerne; //le numéro du client est suffisant *** A REVOIR
    private Employe employe; //le numéro d l'employé suffit *** A REVOIR

    public boolean isRempli() {
        return rempli;
    }

    public void setRempli(boolean rempli) {
        this.rempli = rempli;
    }

    public Client getNumClientConcerne() {
        return numClientConcerne;
    }

    public void setNumClientConcerne(Client numClientConcerne) {
        this.numClientConcerne = numClientConcerne;
    }

    public Date getDateF() {
        return DateF;
    }

    public void setDateF(Date dateF) {
        DateF = dateF;
    }

    public Date getDelai() {
        return delai;
    }

    public void setDelai(Date delai) {
        this.delai = delai;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Type_Transaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = Type_Transaction.valueOf(typeTransaction.toLowerCase());
    }

    public Types_Pieces getTypesPiece() {
        return typesPiece;
    }

    public void setTypesPiece(String typesPiece) {
        this.typesPiece = Types_Pieces.valueOf(typesPiece.toLowerCase());
    }

    public LinkedList<Livre> getContenu() {
        return contenu;
    }

    public void setContenu(LinkedList<Livre> contenu) {
        this.contenu = contenu;
    }

    public void addLivre(Livre l){
        if(!contenu.contains(l))
            contenu.add(l);
    }

    public void removeLivre(Livre l){
        if(!contenu.contains(l))
            contenu.remove(l);
    }

    @Override
    public boolean equals(Object obj) {
        Piece p=(Piece)obj;
        return this.reference.equals(p.reference);
    }
}
