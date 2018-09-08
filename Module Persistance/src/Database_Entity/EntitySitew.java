package Database_Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "SITEW", schema = "CADOC_ADMIN", catalog = "")
public class EntitySitew {
    private String url;
    private String descriptionUrl;

    @Id
    @Column(name = "URL", nullable = false, length = 25)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "DESCRIPTION_URL", nullable = true, length = 25)
    public String getDescriptionUrl() {
        return descriptionUrl;
    }

    public void setDescriptionUrl(String descriptionUrl) {
        this.descriptionUrl = descriptionUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntitySitew that = (EntitySitew) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(descriptionUrl, that.descriptionUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(url, descriptionUrl);
    }
}
