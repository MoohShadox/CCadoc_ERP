package DAO;

import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.Descriptible;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.sql.SQLException;

public class Modele_Contact<Type extends Descriptible> {

    private ObservableMap<String,SimpleStringProperty> description = FXCollections.observableHashMap();
    private Type src;

    public Modele_Contact(Type T) throws IllegalAccessException {
        for(Object S:T.getRepositoryAttributs().keySet()){
            SimpleStringProperty SS = new SimpleStringProperty((String) T.getRepositoryAttributs().get(S));
            SS.addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    try {
                        T.Maj_BDD((String) S,newValue,T.getReference());
                    } catch (SQLException | IllegalAccessException | NonExistantDansLaBDD | BuildingException e) {
                        e.printStackTrace();
                    }
                }
            });
            description.put((String) S,SS);
        }
        src = T;
    }

    public Type getSrc() {
        return src;
    }

    public void modifierProperty(SimpleStringProperty SS,String S) throws IllegalAccessException {
        SS.setValue(S);
    }

    public ObservableMap<String, SimpleStringProperty> getDescription() {
        return description;
    }
}
