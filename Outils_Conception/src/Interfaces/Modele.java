package Interfaces;

import Interfaces.Visualisable;
import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;

public class Modele<T extends Visualisable> {
    private T source;
    private HashMap <String, SimpleStringProperty> propriete_visible = new HashMap <> ( );

    public Modele(T src) throws IllegalAccessException {
        source = src;
        for (String S : source.getPrincipalAttributes ( ).keySet ( )) {
            propriete_visible.put ( S, new SimpleStringProperty ( source.getPrincipalAttributes ( ).get ( S ) ) );
        }

    }

    public T getSource() {
        return source;
    }

    public HashMap <String, SimpleStringProperty> ModeleParDefaut() {
        return propriete_visible;
    }

    public SimpleStringProperty getProperty(String s) {
        return propriete_visible.get ( s );
    }



}
