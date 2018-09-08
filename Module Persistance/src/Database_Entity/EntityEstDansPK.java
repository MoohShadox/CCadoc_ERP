package Database_Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class EntityEstDansPK implements Serializable {
    private String isbn;
    private String nomS;

    @Column(name = "ISBN", nullable = false, length = 15)
    @Id
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Column(name = "NOM_S", nullable = false, length = 25)
    @Id
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
        EntityEstDansPK that = (EntityEstDansPK) o;
        return Objects.equals(isbn, that.isbn) &&
                Objects.equals(nomS, that.nomS);
    }

    @Override
    public int hashCode() {

        return Objects.hash(isbn, nomS);
    }
}
