package Database_Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TELFAX", schema = "CADOC_ADMIN", catalog = "")
public class EntityTelfax {
    private Integer numero;
    private Boolean telfax;

    @Id
    @Column(name = "NUMERO", nullable = false, precision = 0)
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Basic
    @Column(name = "TELFAX", nullable = true, precision = 0)
    public Boolean getTelfax() {
        return telfax;
    }

    public void setTelfax(Boolean telfax) {
        this.telfax = telfax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityTelfax that = (EntityTelfax) o;
        return Objects.equals(numero, that.numero) &&
                Objects.equals(telfax, that.telfax);
    }

    @Override
    public int hashCode() {

        return Objects.hash(numero, telfax);
    }
}
