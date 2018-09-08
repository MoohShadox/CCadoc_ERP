package Interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class Recensement<Type extends Visualisable> {

    private HashMap <String, Type> contenu;
    private HashMap <String, HashMap <String, String>> attributs_principaux;
    private ObservableMap<String, Modele<Type>> map = FXCollections.observableMap(new HashMap<>());

    public Recensement() {
        contenu = new HashMap <> ( );
        attributs_principaux = new HashMap <> ( );
    }

    public void ajouter(Type T) throws IllegalAccessException {
        contenu.put ( T.getReference ( ), T );
        attributs_principaux.put ( T.getReference ( ), T.getPrincipalAttributes ( ) );
        map.put(T.getReference(), (Modele<Type>) T.getModele());
    }

    public Type recuperer(String ref){
        return contenu.get(ref);
    }

    public Modele<Type> recuperer_modele(String ref){
        return map.get(ref);
    }

    public void vider(){
        contenu.clear();
        attributs_principaux.clear();
        map.clear();
    }



    public Set<String> getkeys(){
        return contenu.keySet();
    }


    public LinkedList <String> recuperer_attributs() {
        LinkedList <String> L = new LinkedList <> ( );
        for (String c : attributs_principaux.keySet ( )) {
            for (String d : attributs_principaux.get ( c ).keySet ( )) {
                if ( L.contains ( d ) )
                    continue;
                L.add ( d );
            }
        }
        return L;
    }

    private void supprimer_attribut(String s) {
        for (String c : attributs_principaux.keySet ( )) {
            if ( attributs_principaux.get ( c ).keySet ( ).contains ( s ) )
                attributs_principaux.get ( c ).remove ( s );
        }
    }


   /* public TableView getVisuel() {
        HashMap <String, TableColumn <Modele <Type>, String>> ensemble_colonnes = new HashMap <> ( );

        for (String noms_attributs : recuperer_attributs ( )) {
            TableColumn <Modele <Type>, String> Colonne = new TableColumn <> ( noms_attributs );
            ensemble_colonnes.put ( noms_attributs, Colonne );
        }
        visuel.getColumns ( ).setAll ( ensemble_colonnes.values ( ) );
        *//*for(String s:contenu.keySet ()){
            //visuel.getItems ().add(contenu.get ( s ));
        }*//*
        for (String s : ensemble_colonnes.keySet ( )) {
            //ensemble_colonnes.get ( s ).setCellValueFactory ( new PropertyValueFactory<> (s) );
            ensemble_colonnes.get ( s ).setCellValueFactory ( cell -> cell.getValue ( ).getProperty ( s ) );
        }
        return visuel;
    }*/


    public HashMap <String, TableColumn <Modele <Type>, String>> appliquer_visuel_recensement(TableView <Modele <Type>> visuel) {
        HashMap <String, TableColumn <Modele <Type>, String>> ensemble_colonnes = new HashMap <> ( );
        visuel.setItems(FXCollections.observableArrayList());
        visuel.getItems().clear();
        visuel.getItems().addAll(map.values());
        /*map.addListener(new MapChangeListener<String, Modele<Type>>() {
            @Override
            public void onChanged(Change<? extends String, ? extends Modele<Type>> change) {
                if(change.wasAdded() || change.wasRemoved())
                {
                    visuel.getItems().clear();
                    visuel.getItems().addAll(map.values());
                }
            }
        });*/
        for (String noms_attributs : recuperer_attributs ( )) {
            TableColumn <Modele <Type>, String> Colonne = new TableColumn <> ( noms_attributs );
            ensemble_colonnes.put ( noms_attributs, Colonne );
        }
        visuel.getColumns ( ).setAll ( ensemble_colonnes.values ( ) );
        for (String s : ensemble_colonnes.keySet ( )) {
            TableColumn <Modele <Type>, String> C = ensemble_colonnes.get ( s );
            C.setCellValueFactory ( cell -> cell.getValue ( ).getProperty ( s ) );
        }
        return ensemble_colonnes;
    }

}
