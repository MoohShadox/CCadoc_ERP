package Database_Entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "RENTRE_STOCK", schema = "CADOC_ADMIN")
@IdClass(EntityRentreStockPK.class)
public class RentreStockEntity {
    private Integer quantite;
    private String unite;
    private Long prixAchat;
    private String isbn;
    private String nomS;
    private Time dateSysteme;

    @Basic
    @Column(name = "QUANTITE", nullable = true, precision = 0)
    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    @Basic
    @Column(name = "UNITE", nullable = true, length = 25)
    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    @Basic
    @Column(name = "PRIX_ACHAT", nullable = true, precision = 3)
    public Long getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(Long prixAchat) {
        this.prixAchat = prixAchat;
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
    @Column(name = "NOM_S", nullable = false, length = 25)
    public String getNomS() {
        return nomS;
    }

    public void setNomS(String nomS) {
        this.nomS = nomS;
    }

    @Id
    @Column(name = "DATE_SYSTEME", nullable = false)
    public Time getDateSysteme() {
        return dateSysteme;
    }

    public void setDateSysteme(Time dateSysteme) {
        this.dateSysteme = dateSysteme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentreStockEntity that = (RentreStockEntity) o;
        return Objects.equals(quantite, that.quantite) &&
                Objects.equals(unite, that.unite) &&
                Objects.equals(prixAchat, that.prixAchat) &&
                Objects.equals(isbn, that.isbn) &&
                Objects.equals(nomS, that.nomS) &&
                Objects.equals(dateSysteme, that.dateSysteme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantite, unite, prixAchat, isbn, nomS, dateSysteme);
    }
}
