package Interfaces;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Collection;
import java.util.HashMap;

public abstract class Controlleur_Visualisable<T extends Visualisable> extends Controller <T, Modele <T>> implements Controllable_Recensement<T> {

    protected Recensement <T> recensement = new Recensement <> ( );

    @Override
    public void RefreshCollection(Collection <T> T) throws IllegalAccessException {
        for (T objet : T) {
            recensement.ajouter ( objet );
        }

    }

    public void RefreshCollection() throws IllegalAccessException {
        RefreshCollection(collection);
    }

    public Recensement <T> getRecensement() {
        return recensement;
    }

    protected HashMap<String, TableColumn<Modele<T>, String>> implenter_visuel(TableView <Modele <T>> TT) {
        return recensement.appliquer_visuel_recensement( TT );
    }

    public abstract void setVisuel();

}
