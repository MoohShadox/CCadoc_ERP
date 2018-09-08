package POJO;

import Connections.ConnectionOrcl;
import DAO.DAO;
import DAO.DAOLivre;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Exceptions.NonExistantDansLesInfos;
import Interfaces.Modele;
import Interfaces.Visualisable;
import Java_Python_Exploitation.Moteur_Enrichissement;
import javafx.animation.PauseTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.HashMap;

public class Inventorier extends Moteur_Enrichissement implements Visualisable {
    private String isbn;
    private IntegerProperty progression; // Peut valoir 1 , 2 ou 3 Suivant les différents stades de renseignements
    private HashMap <String, String> renseignements = new HashMap<>();
    private HashMap<String,String> donnesBDD = new HashMap<>();
    private int quantite;
    private Service<Inventorier> remplissage_donnes;

    public void fetchBDD() throws SQLException, NonExistantDansLaBDD {
        Statement S = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS = S.executeQuery("SELECT * FROM PRODUIT WHERE ISBN = '" + isbn + "'" );
        ResultSetMetaData RSM = RS.getMetaData();
        if(!RS.next()){
            throw  new NonExistantDansLaBDD();
        }
        else
        {
            for (int ii=1;ii<RSM.getColumnCount();ii++){
                if(!(RS.getString(ii) ==null) && !RS.getString(ii).equalsIgnoreCase("null"))
                    donnesBDD.put(RSM.getColumnName(ii),RS.getString(ii));
            }
        }
    }


    public void fetchProduitInfo() throws SQLException, NonExistantDansLesInfos {
        Statement S = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS = S.executeQuery("SELECT * FROM PRODUIT_INFO WHERE ISBN = '" + isbn + "'" );
        ResultSetMetaData RSM = RS.getMetaData();
        if(!RS.next()){
            throw  new NonExistantDansLesInfos();
        }
        else
        {
            for (int ii=1;ii<RSM.getColumnCount();ii++) {
                try {
                    if (!RS.getString(ii).equalsIgnoreCase("null")) {
                        renseignements.put(RSM.getColumnName(ii), RS.getString(ii));
                    }
                }catch (Exception e){
                }

            }
        }
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Inventorier(String isbn, int quantite) throws SQLException, IOException {
        super ( );
        init_PyLauncher ( );
        this.isbn = isbn;
        this.quantite = quantite;
        progression = new SimpleIntegerProperty();
        progression.set(0);
        if(isbn_processed.containsKey(isbn))
        {
            progression.set(isbn_processed.get(isbn) + 1);
        }

    }

    public void enrichir_BDD() throws SQLException, IllegalAccessException, BuildingException, NonExistantDansLesInfos {
        Livre L = new Livre();
        DAO<Livre> D = new DAOLivre(L);
        try {
            fetchProduitInfo();
            Livre LL = new Livre().buildFromRepData(renseignements);
            fetchBDD();
            D.mettre_a_jour(LL, LL.getReference());
        } catch (NonExistantDansLaBDD nonExistantDansLaBDD) {
            Livre LL = new Livre().buildFromRepData(renseignements);
            D.ajouter(LL);
        }
    }

    public String getIsbn() {
        return isbn;
    }

    public HashMap <String, String> getRenseignements() {
        return renseignements;
    }

    public IntegerProperty getProgression() {
        return progression;
    }

    public HashMap<String, String> renseigner() throws SQLException {

        if (renseignements != null && progression.get() == 3) {
            return renseignements;
        }
        if (renseignements.keySet().isEmpty()) {
            renseignements.putAll(super.Enrichir(isbn, 0));
            progression.setValue(1);
            if(renseignements.size()==0)
                progression.set(3);
            return renseignements;
        }

        if (progression.get() == 1) {
            renseignements.putAll( super.Enrichir(isbn, 1));
            progression.set(2);
            return renseignements;
        }

        if (progression.get() == 2) {
            renseignements.putAll(super.Enrichir(isbn, 2));
            progression.set(3);
            return renseignements;
        }
        return renseignements;

    }







    public Service<Inventorier> createService() {
        Inventorier I = this;
        if(remplissage_donnes!=null)
            return remplissage_donnes;
         remplissage_donnes = new Service<Inventorier>() {
            @Override
            protected Task<Inventorier> createTask() {
                Task<Inventorier> T = new Task<Inventorier>() {
                    @Override
                    protected Inventorier call() throws SQLException {

                        try {
                                I.fetchProduitInfo();
                        } catch (NonExistantDansLesInfos nonExistantDansLesInfos) {

                        }
                        try {
                            if(I.donnesBDD.keySet().isEmpty())
                                I.fetchBDD();
                        } catch (NonExistantDansLaBDD nonExistantDansLaBDD) {

                        }
                        if(Moteur_Enrichissement.isbn_processed.containsKey(isbn))
                        {
                            progression.set(Moteur_Enrichissement.isbn_processed.get(isbn)+1);
                        }
                        if (progression.get() == 3)
                            return null;
                        I.renseignements = renseigner();
                        System.out.println("Fin du renseignement avec " + renseignements);
                        return I;
                    }

                    @Override
                    protected void failed() {
                        progression.set(0);
                        PauseTransition P = new PauseTransition();
                        P.setDuration(Duration.millis(2000));
                        P.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                reset();
                                start();
                            }
                        });
                        P.play();
                    }
                };
                return T;
            }
        };
        return remplissage_donnes;
    }


    public HashMap<String, String> getDonnesBDD() {
        return donnesBDD;
    }

    @Override
    public String getReference() {
        return isbn;
    }

    @Override
    public HashMap <String, String> getPrincipalAttributes() {
        HashMap <String, String> H = new HashMap <> ( );
        H.put ( "Progression", progression.get() + "" );
        H.put ( "Quantite", quantite + "" );
        H.put ( "ISBN", isbn );
        return H;
    }

    public int getQuantite() {
        return quantite;
    }

    @Override
    public Modele<Visualisable> getModele() throws IllegalAccessException {
        return new Modele <Visualisable> ( this );
    }

    @Override
    public String toString() {
        return isbn;
    }
}
