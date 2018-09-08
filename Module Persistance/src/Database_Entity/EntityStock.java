package Database_Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "STOCK", schema = "CADOC_ADMIN", catalog = "")
public class EntityStock {
    private String nomS;
    private String lieu;
    private String dateR;

    @Id
    @Column(name = "NOM_S", nullable = false, length = 150)
    public String getNomS() {
        return nomS;
    }

    public void setNomS(String nomS) {
        this.nomS = nomS;
    }

    @Basic
    @Column(name = "LIEU", nullable = true, length = 25)
    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    @Basic
    @Column(name = "DATE_R", nullable = true, length = 20)
    public String getDateR() {
        return dateR;
    }

    public void setDateR(String dateR) {
        this.dateR = dateR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityStock that = (EntityStock) o;
        return Objects.equals(nomS, that.nomS) &&
                Objects.equals(lieu, that.lieu) &&
                Objects.equals(dateR, that.dateR);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nomS, lieu, dateR);
    }
}
