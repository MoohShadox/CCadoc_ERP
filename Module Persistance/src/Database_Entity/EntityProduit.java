package Database_Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PRODUIT", schema = "CADOC_ADMIN", catalog = "")
public class EntityProduit {
    private String isbn;
    private String titre;
    private String auteur;
    private String editeur;
    private String resume;
    private String dateP;
    private Double prixHt;
    private Double prixTtc;
    private String domaine;
    private String domaine2;
    private String domaine3;
    private String theme;
    private String dewey;

    @Id
    @Column(name = "ISBN", nullable = false, length = 15)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "TITRE", nullable = true, length = 2000)
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Basic
    @Column(name = "AUTEUR", nullable = true, length = 1000)
    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    @Basic
    @Column(name = "EDITEUR", nullable = true, length = 1000)
    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    @Basic
    @Column(name = "RESUME", nullable = true, length = 3800)
    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    @Basic
    @Column(name = "DATE_P", nullable = true, length = 1000)
    public String getDateP() {
        return dateP;
    }

    public void setDateP(String dateP) {
        this.dateP = dateP;
    }

    @Basic
    @Column(name = "PRIX_HT", nullable = true, precision = 0)
    public Double getPrixHt() {
        return prixHt;
    }

    public void setPrixHt(Double prixHt) {
        this.prixHt = prixHt;
    }

    @Basic
    @Column(name = "PRIX_TTC", nullable = true, precision = 0)
    public Double getPrixTtc() {
        return prixTtc;
    }

    public void setPrixTtc(Double prixTtc) {
        this.prixTtc = prixTtc;
    }

    @Basic
    @Column(name = "DOMAINE", nullable = true, length = 2000)
    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    @Basic
    @Column(name = "DOMAINE2", nullable = true, length = 2000)
    public String getDomaine2() {
        return domaine2;
    }

    public void setDomaine2(String domaine2) {
        this.domaine2 = domaine2;
    }

    @Basic
    @Column(name = "DOMAINE3", nullable = true, length = 2000)
    public String getDomaine3() {
        return domaine3;
    }

    public void setDomaine3(String domaine3) {
        this.domaine3 = domaine3;
    }

    @Basic
    @Column(name = "THEME", nullable = true, length = 1000)
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Basic
    @Column(name = "DEWEY", nullable = true, length = 80)
    public String getDewey() {
        return dewey;
    }

    public void setDewey(String dewey) {
        this.dewey = dewey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityProduit that = (EntityProduit) o;
        return Objects.equals(isbn, that.isbn) &&
                Objects.equals(titre, that.titre) &&
                Objects.equals(auteur, that.auteur) &&
                Objects.equals(editeur, that.editeur) &&
                Objects.equals(resume, that.resume) &&
                Objects.equals(dateP, that.dateP) &&
                Objects.equals(prixHt, that.prixHt) &&
                Objects.equals(prixTtc, that.prixTtc) &&
                Objects.equals(domaine, that.domaine) &&
                Objects.equals(domaine2, that.domaine2) &&
                Objects.equals(domaine3, that.domaine3) &&
                Objects.equals(theme, that.theme) &&
                Objects.equals(dewey, that.dewey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, titre, auteur, editeur, resume, dateP, prixHt, prixTtc, domaine, domaine2, domaine3, theme, dewey);
    }
}
