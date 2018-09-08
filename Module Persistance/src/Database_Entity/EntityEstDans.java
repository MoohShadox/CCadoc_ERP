package Database_Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "EST_DANS", schema = "CADOC_ADMIN", catalog = "")
@IdClass(EntityEstDansPK.class)
public class EntityEstDans {
    private Integer quantite;
    private String isbn;
    private String nomS;

    @Basic
    @Column(name = "QUANTITE", nullable = true, precision = 0)
    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityEstDans that = (EntityEstDans) o;
        return Objects.equals(quantite, that.quantite) &&
                Objects.equals(isbn, that.isbn) &&
                Objects.equals(nomS, that.nomS);
    }

    @Override
    public int hashCode() {

        return Objects.hash(quantite, isbn, nomS);
    }
}
