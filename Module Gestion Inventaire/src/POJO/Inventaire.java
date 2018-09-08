package POJO;


import Connections.ConnectionOrcl;
import DAO.DAO;
import Repository.InventaireDAO;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.DAOAble;
import Interfaces.Modele;
import Interfaces.Recensement;
import Interfaces.Visualisable;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Inventaire implements DAOAble<Inventaire>, Visualisable {
    private String nom_inventaire;
    private Date date_realisation = Calendar.getInstance().getTime();
    private int nombre_total_ouvrages;
    private boolean finalise = false;
    private Recensement<Inventorier> recensement = new Recensement<>( );



    public Inventaire(String nom,String date) throws ParseException {
        nom_inventaire = nom;
        SimpleDateFormat SDF  = new SimpleDateFormat("dd/MM/yyyy");
        date_realisation = SDF.parse(date);
    }

    public String getNom_inventaire() {
        return nom_inventaire;
    }

    public Inventaire(String nom){nom_inventaire = nom;}

    public void setDate_realisation(Date date_realisation) {
        this.date_realisation = date_realisation;
    }

    public void setNom_inventaire(String nom_inventaire) {
        this.nom_inventaire = nom_inventaire;
    }

    public int inc_nb_ouvrages() {
        return nombre_total_ouvrages++;
    }

    public Date getDate_realisation() {
        return date_realisation;
    }

    public int getNombre_total_ouvrages() {
        return nombre_total_ouvrages;
    }

    public boolean isFinalise() {
        return finalise;
    }

    @Override
    public HashMap<String, String> getRepositoryAttributs() throws IllegalAccessException {
        HashMap<String,String> H = new HashMap<>();
        SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
        H.put("NOM_INVENTAIRE",nom_inventaire);
        H.put("NOMBRE_OUVRAGES",nombre_total_ouvrages+"");
        H.put("DATE_CREATION",SDF.format(date_realisation));
        H.put("FINALISE",finalise+"");
        return H;
    }

    public void setFinalise(boolean finalise) {
        this.finalise = finalise;
    }

    public boolean getFinalise(){
        return this.finalise;
    }


    public void setNombre_total_ouvrages(int nombre_total_ouvrages) {
        this.nombre_total_ouvrages = nombre_total_ouvrages;
    }

    @Override
    public String getTableName() {
        return "INVENTAIRES";
    }

    @Override
    public String getReference() {
        return nom_inventaire;
    }

    @Override
    public HashMap<String, String> getPrincipalAttributes() throws IllegalAccessException {
        return getRepositoryAttributs();
    }

    @Override
    public Modele<Visualisable> getModele() throws IllegalAccessException {
        return new Modele<>(this);
    }

    @Override
    public String getKeyName() {
        return "NOM_INVENTAIRE";
    }

    @Override
    public Inventaire buildFromRepData(HashMap H) throws BuildingException, IllegalAccessException {
        Inventaire I = new Inventaire("");
        try {
            SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
            I.setNom_inventaire(H.get("NOM_INVENTAIRE").toString());
            I.setDate_realisation(SDF.parse(H.get("DATE_CREATION").toString()));
            I.nombre_total_ouvrages = Integer.valueOf(H.get("NOMBRE_OUVRAGES").toString());
            I.finalise = Boolean.valueOf(H.get("FINALISE").toString());
            Statement S = ConnectionOrcl.getInstance().createStatement();
            ResultSet RS = S.executeQuery("SELECT * FROM INVENTAIRES JOIN EST_INVENTORIER I on INVENTAIRES.NOM_INVENTAIRE = I.INVENTAIRE WHERE I.INVENTAIRE=\'"+nom_inventaire+"\'");
            while(RS.next()){
                Inventorier inv = new Inventorier(RS.getString(6),RS.getInt(7));
                I.recensement.ajouter(inv);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return I;
    }

    public Recensement<Inventorier> getRecensement() {
        return recensement;
    }

    public void ajouter_inventaire(Inventorier I) throws IllegalAccessException, SQLException {
        recensement.ajouter(I);
        DAO<Inventaire> DI = new InventaireDAO(this);
        try {
            DI.recuperer(nom_inventaire);
        } catch (BuildingException e) {
            e.printStackTrace();
        } catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
            DI.ajouter(this);
        }
        PreparedStatement S = ConnectionOrcl.getInstance().prepareStatement("INSERT INTO EST_INVENTORIER VALUES(?,?,?)");
        S.setString(1,nom_inventaire);
        S.setString(2,I.getIsbn());
        S.setInt(3,I.getQuantite());
        S.executeUpdate();
    }




}
