package Database_Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "EMPLOYE", schema = "CADOC_ADMIN", catalog = "")
public class EntityEmploye {
    private Long numE;
    private String nom;
    private String prenom;
    private String poste;

    @Id
    @Column(name = "NUM_E", nullable = false, precision = 0)
    public Long getNumE() {
        return numE;
    }

    public void setNumE(Long numE) {
        this.numE = numE;
    }

    @Basic
    @Column(name = "NOM", nullable = true, length = 25)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "PRENOM", nullable = true, length = 25)
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Basic
    @Column(name = "POSTE", nullable = true, length = 25)
    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityEmploye that = (EntityEmploye) o;
        return Objects.equals(numE, that.numE) &&
                Objects.equals(nom, that.nom) &&
                Objects.equals(prenom, that.prenom) &&
                Objects.equals(poste, that.poste);
    }

    @Override
    public int hashCode() {

        return Objects.hash(numE, nom, prenom, poste);
    }
}
