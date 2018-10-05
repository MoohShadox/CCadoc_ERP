package Presentation.FXML;

import Presentation.Controller.Gestionnaire_Repertoire;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch ( args );
    }

    @Override
    public void start(Stage primaryStage) throws IOException, IllegalAccessException {
        Gestionaire_Interface G = new Gestionaire_Interface ( primaryStage );
        Gestionnaire_Repertoire C = (Gestionnaire_Repertoire) G.switchPanel("VisualisationContact.fxml", "Informations Téléchargées");
        C.provideTable();
    }
}
