package Presentation.Controller;

import Composants_Visuels.WindowButtons;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeTableView;

public abstract class Gestionnaire_Repertoire extends WindowButtons {

    public abstract TreeTableView<SimpleStringProperty> provideTable();


}
