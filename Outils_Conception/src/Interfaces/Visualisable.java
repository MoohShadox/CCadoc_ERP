package Interfaces;

import java.util.HashMap;

public interface Visualisable {


    String getReference() throws IllegalAccessException;

    HashMap <String, String> getPrincipalAttributes() throws IllegalAccessException;

    Modele<Visualisable> getModele() throws IllegalAccessException;


}
