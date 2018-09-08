package Database_Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "MAIL", schema = "CADOC_ADMIN", catalog = "")
public class EntityMail {
    private String adresseMail;
    private String nomMail;
    private String typec;

    @Id
    @Column(name = "ADRESSE_MAIL", nullable = false, length = 25)
    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    @Basic
    @Column(name = "NOM_MAIL", nullable = true, length = 25)
    public String getNomMail() {
        return nomMail;
    }

    public void setNomMail(String nomMail) {
        this.nomMail = nomMail;
    }

    @Basic
    @Column(name = "TYPEC", nullable = true, length = 25)
    public String getTypec() {
        return typec;
    }

    public void setTypec(String typec) {
        this.typec = typec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityMail that = (EntityMail) o;
        return Objects.equals(adresseMail, that.adresseMail) &&
                Objects.equals(nomMail, that.nomMail) &&
                Objects.equals(typec, that.typec);
    }

    @Override
    public int hashCode() {

        return Objects.hash(adresseMail, nomMail, typec);
    }
}
