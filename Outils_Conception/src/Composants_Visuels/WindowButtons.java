package Composants_Visuels;

import javafx.scene.Node;
import javafx.stage.Stage;

public abstract class WindowButtons {

    protected void fermerFenetre( Node N) {
        Stage stage = (Stage) N.getScene().getWindow();
        stage.close();
    }

    protected void Iconify( Node N) {
        Stage stage = (Stage) N.getScene().getWindow();
        stage.setIconified(true);
    }

}
