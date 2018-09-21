package Database_Entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "EST_INVENTORIER", schema = "CADOC_ADMIN", catalog = "")
public class EntityEstInventorier {
    private String isbn;
    private Long qte;

    @Basic
    @Column(name = "ISBN", nullable = true, length = 15)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "QTE", nullable = false, precision = 0)
    public Long getQte() {
        return qte;
    }

    public void setQte(Long qte) {
        this.qte = qte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityEstInventorier that = (EntityEstInventorier) o;
        return Objects.equals(isbn, that.isbn) &&
                Objects.equals(qte, that.qte);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, qte);
    }

    @javax.persistence.Id
    public String getId() {
        return isbn;
    }

    public void setId(String id) {
        this.isbn = isbn;
    }
}
