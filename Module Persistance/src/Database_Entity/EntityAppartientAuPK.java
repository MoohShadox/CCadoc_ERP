package Database_Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class EntityAppartientAuPK implements Serializable {
    private String nomConcept;
    private String isbn;

    @Column(name = "NOM_CONCEPT", nullable = false, length = 25)
    @Id
    public String getNomConcept() {
        return nomConcept;
    }

    public void setNomConcept(String nomConcept) {
        this.nomConcept = nomConcept;
    }

    @Column(name = "ISBN", nullable = false, length = 15)
    @Id
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
        EntityAppartientAuPK that = (EntityAppartientAuPK) o;
        return Objects.equals(nomConcept, that.nomConcept) &&
                Objects.equals(isbn, that.isbn);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nomConcept, isbn);
    }
}
