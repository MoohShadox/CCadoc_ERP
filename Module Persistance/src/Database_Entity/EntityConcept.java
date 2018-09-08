package Database_Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "CONCEPT", schema = "CADOC_ADMIN", catalog = "")
public class EntityConcept {
    private String nomConcept;

    @Id
    @Column(name = "NOM_CONCEPT", nullable = false, length = 25)
    public String getNomConcept() {
        return nomConcept;
    }

    public void setNomConcept(String nomConcept) {
        this.nomConcept = nomConcept;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityConcept that = (EntityConcept) o;
        return Objects.equals(nomConcept, that.nomConcept);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nomConcept);
    }
}
