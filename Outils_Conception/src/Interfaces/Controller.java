package Interfaces;


import Composants_Visuels.WindowButtons;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;

public abstract class Controller<Type, TypeModel> extends WindowButtons implements Controllable <Type> {
    /*Tout les controlleur se ressemblent ils contiennent soit une information ou une collection, et généralement une collection qui a pour
    but de représenter l'objet donc je les factorise en une classe abstraite a laquelle j'infère deux classes, la classe réel et le model correspondant.
     */

    /*La classe implemente l'interface controllable, ceci est un exemple du pattern stratégie je vous invite a regarder la classe Gestion interface
    pour comprendre l'utilité de cette interface.
     */

    protected Collection <Type> collection;
    protected ObservableList <TypeModel> liste = FXCollections.observableArrayList ( );

    /*Traitement les plus courrant qu'on peut avoir a effectuer sur un controlleur pour le faire intéragir avec le core de l'application*/

    //Ajout a la collection
    public void addtoCollection(Type Y) {
        collection.add ( Y );
    }

    //Rafraichir la collection d'objet observable, cette méthode dépend de la classe et plus précisement de comment je passe de la classe réelle
    //a la classe modèle je vous renvois a la redéfinition dpour le controlleur de produits

    public abstract void RefreshCollection(Collection <Type> T) throws IllegalAccessException;

    //Récuperer la collection
    public Collection <Type> getCollection() {
        return collection;
    }

    //Donnez une nouvelle collection
    public void setCollection(Collection <Type> collection) {
        this.collection = collection;
    }

    public void setVisuel() {

    }

}
