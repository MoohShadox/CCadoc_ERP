package Database_Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "INVENTAIRES", schema = "CADOC_ADMIN", catalog = "")
public class EntityInventaires {
    private String nomInventaire;
    private Long nombreOuvrages;
    private String dateCreation;
    private String finalise;

    @Id
    @Column(name = "NOM_INVENTAIRE", nullable = false, length = 100)
    public String getNomInventaire() {
        return nomInventaire;
    }

    public void setNomInventaire(String nomInventaire) {
        this.nomInventaire = nomInventaire;
    }

    @Basic
    @Column(name = "NOMBRE_OUVRAGES", nullable = true, precision = 0)
    public Long getNombreOuvrages() {
        return nombreOuvrages;
    }

    public void setNombreOuvrages(Long nombreOuvrages) {
        this.nombreOuvrages = nombreOuvrages;
    }

    @Basic
    @Column(name = "DATE_CREATION", nullable = true, length = 20)
    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Basic
    @Column(name = "FINALISE", nullable = true, length = 8)
    public String getFinalise() {
        return finalise;
    }

    public void setFinalise(String finalise) {
        this.finalise = finalise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityInventaires that = (EntityInventaires) o;
        return Objects.equals(nomInventaire, that.nomInventaire) &&
                Objects.equals(nombreOuvrages, that.nombreOuvrages) &&
                Objects.equals(dateCreation, that.dateCreation) &&
                Objects.equals(finalise, that.finalise);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nomInventaire, nombreOuvrages, dateCreation, finalise);
    }
}
