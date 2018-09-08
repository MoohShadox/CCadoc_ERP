package Database_Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "EST_FACTURE_DANS", schema = "CADOC_ADMIN", catalog = "")
@IdClass(EntityEstFactureDansPK.class)
public class EntityEstFactureDans {
    private Integer quantite;
    private Double prixUnitaire;
    private String unite;
    private String isbn;
    private Integer reference;
    private String nomS;

    @Basic
    @Column(name = "QUANTITE", nullable = true, precision = 0)
    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    @Basic
    @Column(name = "PRIX_UNITAIRE", nullable = true, precision = 0)
    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    @Basic
    @Column(name = "UNITE", nullable = true, length = 25)
    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    @Id
    @Column(name = "ISBN", nullable = false, length = 15)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Id
    @Column(name = "REFERENCE", nullable = false, precision = 0)
    public Integer getReference() {
        return reference;
    }

    public void setReference(Integer reference) {
        this.reference = reference;
    }

    @Id
    @Column(name = "NOM_S", nullable = false, length = 25)
    public String getNomS() {
        return nomS;
    }

    public void setNomS(String nomS) {
        this.nomS = nomS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityEstFactureDans that = (EntityEstFactureDans) o;
        return Objects.equals(quantite, that.quantite) &&
                Objects.equals(prixUnitaire, that.prixUnitaire) &&
                Objects.equals(unite, that.unite) &&
                Objects.equals(isbn, that.isbn) &&
                Objects.equals(reference, that.reference) &&
                Objects.equals(nomS, that.nomS);
    }

    @Override
    public int hashCode() {

        return Objects.hash(quantite, prixUnitaire, unite, isbn, reference, nomS);
    }
}
