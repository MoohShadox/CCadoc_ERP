package Database_Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Time;
import java.util.Objects;

public class EntityRentreStockPK implements Serializable {
    private String isbn;
    private String nomS;
    private Time dateSysteme;

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

    @Column(name = "DATE_SYSTEME", nullable = false)
    @Id
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
        EntityRentreStockPK that = (EntityRentreStockPK) o;
        return Objects.equals(isbn, that.isbn) &&
                Objects.equals(nomS, that.nomS) &&
                Objects.equals(dateSysteme, that.dateSysteme);
    }

    @Override
    public int hashCode() {

        return Objects.hash(isbn, nomS, dateSysteme);
    }
}
