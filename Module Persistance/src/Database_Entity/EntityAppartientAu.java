package Database_Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "APPARTIENT_AU", schema = "CADOC_ADMIN", catalog = "")
@IdClass(EntityAppartientAuPK.class)
public class EntityAppartientAu {
    private String nomConcept;
    private String isbn;

    @Id
    @Column(name = "NOM_CONCEPT", nullable = false, length = 25)
    public String getNomConcept() {
        return nomConcept;
    }

    public void setNomConcept(String nomConcept) {
        this.nomConcept = nomConcept;
    }

    @Id
    @Column(name = "ISBN", nullable = false, length = 15)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityAppartientAu that = (EntityAppartientAu) o;
        return Objects.equals(nomConcept, that.nomConcept) &&
                Objects.equals(isbn, that.isbn);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nomConcept, isbn);
    }
}
