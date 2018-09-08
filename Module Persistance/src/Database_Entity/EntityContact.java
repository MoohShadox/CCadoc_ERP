package Database_Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CONTACT", schema = "CADOC_ADMIN", catalog = "")
public class EntityContact {
    private Long numc;
    private String denomination;
    private String adresse;
    private String type;

    @Id
    @Column(name = "NUMC", nullable = false, precision = 0)
    public Long getNumc() {
        return numc;
    }

    public void setNumc(Long numc) {
        this.numc = numc;
    }

    @Basic
    @Column(name = "DENOMINATION", nullable = true, length = 25)
    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    @Basic
    @Column(name = "ADRESSE", nullable = true, length = 25)
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Basic
    @Column(name = "TYPE", nullable = true, length = 25)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityContact that = (EntityContact) o;
        return Objects.equals(numc, that.numc) &&
                Objects.equals(denomination, that.denomination) &&
                Objects.equals(adresse, that.adresse) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(numc, denomination, adresse, type);
    }
}
