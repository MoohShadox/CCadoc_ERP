package POJO;

import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.DAOAble;
import Interfaces.Descriptible;
import Interfaces.Modele;
import Interfaces.Visualisable;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Livre implements Visualisable, Descriptible<Livre> {
    private String ISBN;
    private String titre;
    private String auteur;
    private String editeur;
    private Date date_parrution;
    private float prix_ht;
    private float prix_ttc;
    private String domaine;
    private String domaine2;
    private String domaine3;
    private String theme;
    private String dewey;
    private String resume;

    public Livre() {
        titre = "";
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().getName().equalsIgnoreCase("Livre"))
            return false;
        return ISBN.equalsIgnoreCase(((Livre) obj).getISBN());
    }

    @Override
    public Livre buildFromRepData(HashMap <String, String> H) throws BuildingException, IllegalAccessException {
        Livre L = new Livre();
        for(String S:H.keySet())
        {
            switch (S.toLowerCase()){
                case "titre":
                    L.setTitre(H.get(S));
                    break;
                case "auteur":
                    L.setAuteur(H.get(S));
                    break;
                case "editeur":
                    L.setEditeur(H.get(S));
                    break;
                case "isbn":
                    L.setISBN(H.get(S));
                    break;
                case "prix_ht":
                    if(H.get(S)!=null)
                        L.setPrix_ht(Float.parseFloat(H.get(S)));
                    break;
                case "date_p":
                case "date de parution":
                    L.setDate_parrution(DateFormatter(H.get(S)));
                    break;
                case "resume":
                    L.setResume(H.get(S));
                case "domaine":
                    L.setDomaine(H.get(S));
                case "domaine2":
                    L.setDomaine2(H.get(S));
                case "domaine3":
                    L.setDomaine3(H.get(S));
                case "theme":
                    L.setTheme(H.get(S));
                case "dewey":
                    L.setDewey(H.get(S));
            }
        }
        return L;
    }

    public static void main(String[] j ){
        DateFormatter("15-octobre-2015");
    }


    public static Date DateFormatter(String S) {
        if(S==null)
            return null;
        if(S.equalsIgnoreCase("UNKNOWN"))
            return null;
        S = S.replace("-","/");
        Pattern P2 = Pattern.compile("([a-z]+)");
        SimpleDateFormat SDF = null;

        Matcher M2 = P2.matcher(S);
        Date D;
        String SS;
        int j=0,m=0,a=0;
        if(M2.find()){
            SS = M2.group(1);
            System.out.println(SS);
            int numero_mois = 0;
            switch (SS.toLowerCase().trim()) {
                case "décembre":
                    numero_mois++;
                case "novembre":
                    numero_mois++;
                case "octobre":
                    numero_mois++;
                case "septembre":
                    numero_mois++;
                case "ao":
                    numero_mois++;
                case "juillet":
                    numero_mois++;
                case "juin":
                    numero_mois++;
                case "mai":
                    numero_mois++;
                case "avril":
                    numero_mois++;
                case "mars":
                    numero_mois++;
                case "février":
                    numero_mois++;
                case "janvier":
                    numero_mois++;
                    break;
            }
            if (numero_mois >= 10) {
                S = S.replace(SS, "/" + numero_mois + "/");
            } else
                S = S.replace(SS, "/0" + numero_mois + "/");
        }
        S = S.replace("//","/");
        if(S.startsWith("/"))
            S = S.replaceFirst("/","");

        Pattern P = Pattern.compile("(\\d+)");
        Matcher M = P.matcher(S);
        int  i = 0;
        while(M.find())
        {
            j = m;
            m = a;
            a = Integer.valueOf(M.group(1));
        }
        if(a<40)
        {
            a  = a + 2000;
        }
        else
            a  = a + 1900;
        Calendar C = Calendar.getInstance();
        C.set(a,m,j);
        Date DD = C.getTime();
        return DD;
    }

    @Override
    public HashMap <String, String> getRepositoryAttributs() throws IllegalAccessException {
        HashMap <String, String> H = getPrincipalAttributes();
        SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
        /*if(date_parrution!=null)
            H.put("DATE_P",SDF.format(date_parrution));*/
        if(resume!=null)
            H.put("RESUME",resume);
        if(prix_ht!=0)
            H.put("PRIX_HT",prix_ht+"");
        if(prix_ttc!=0)
            H.put("PRIX_TTC",""+prix_ttc);

        //TODO ne pas oublier ce détail
        if(domaine!=null)
            H.put("DOMAINE",""+prix_ttc);
        if(domaine2!=null)
            H.put("DOMAINE2",""+prix_ttc);
        if(domaine3!=null)
            H.put("DOMAINE3",""+prix_ttc);
        if(theme!=null)
            H.put("THEME",""+prix_ttc);
        if(dewey!=null)
            H.put("DEWEY",dewey);

        return ( H );
    }

    public String getReference() {
        return ISBN;
    }

    @Override
    public String getTableName() {
        return "PRODUIT";
    }

    @Override
    public String getKeyName() {
        return "ISBN";
    }

    public HashMap <String, String> getPrincipalAttributes() {
        HashMap <String, String> H = new HashMap <> ( );
        SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
        H.put ( "TITRE", titre );
        H.put ( "AUTEUR", auteur );
        H.put ( "EDITEUR", editeur );
        H.put ( "ISBN", ISBN );
        if(date_parrution!=null)
            H.put("DATE_P",SDF.format(date_parrution));
        return H;
    }


    @Override
    public Modele <Visualisable> getModele() throws IllegalAccessException {
        return new Modele<>( this );
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public Date getDate_parrution() {
        return date_parrution;
    }

    public void setDate_parrution(Date date_parrution) {
        this.date_parrution = date_parrution;
    }

    public float getPrix_ht() {
        return prix_ht;
    }

    public void setPrix_ht(float prix_ht) {
        this.prix_ht = prix_ht;
    }

    public float getPrix_ttc() {
        return prix_ttc;
    }

    public void setPrix_ttc(float prix_ttc) {
        this.prix_ttc = prix_ttc;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getDomaine2() {
        return domaine2;
    }

    public void setDomaine2(String domaine2) {
        this.domaine2 = domaine2;
    }

    public String getDomaine3() {
        return domaine3;
    }

    public void setDomaine3(String domaine3) {
        this.domaine3 = domaine3;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDewey() {
        return dewey;
    }

    public void setDewey(String dewey) {
        this.dewey = dewey;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    @Override
    public String toString() {
        return titre;
    }


    @Override
    public void Maj_BDD(String attribut, String nouvelle_valeur, String ref) throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException {

    }

    @Override
    public boolean verfier(String s) {
        return false;
    }
    
}
