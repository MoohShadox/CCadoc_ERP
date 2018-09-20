package Database_Entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "PIECE", schema = "CADOC_ADMIN", catalog = "")
public class EntityPiece {
    private Integer reference;
    private Time dateFacture;
    private String typeFacture;
    private String transaction;
    private Time delai;
    private Boolean rempli;

    @Id
    @Column(name = "REFERENCE", nullable = false, precision = 0)
    public Integer getReference() {
        return reference;
    }

    public void setReference(Integer reference) {
        this.reference = reference;
    }

    @Basic
    @Column(name = "DATE_FACTURE", nullable = true)
    public Time getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(Time dateFacture) {
        this.dateFacture = dateFacture;
    }

    @Basic
    @Column(name = "TYPE_FACTURE", nullable = true, length = 25)
    public String getTypeFacture() {
        return typeFacture;
    }

    public void setTypeFacture(String typeFacture) {
        this.typeFacture = typeFacture;
    }

    @Basic
    @Column(name = "TRANSACTION", nullable = true, length = 25)
    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    @Basic
    @Column(name = "DELAI", nullable = true)
    public Time getDelai() {
        return delai;
    }

    public void setDelai(Time delai) {
        this.delai = delai;
    }

    @Basic
    @Column(name = "REMPLI", nullable = true, precision = 0)
    public Boolean getRempli() {
        return rempli;
    }

    public void setRempli(Boolean rempli) {
        this.rempli = rempli;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityPiece that = (EntityPiece) o;
        return Objects.equals(reference, that.reference) &&
                Objects.equals(dateFacture, that.dateFacture) &&
                Objects.equals(typeFacture, that.typeFacture) &&
                Objects.equals(transaction, that.transaction) &&
                Objects.equals(delai, that.delai) &&
                Objects.equals(rempli, that.rempli);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, dateFacture, typeFacture, transaction, delai, rempli);
    }
}
