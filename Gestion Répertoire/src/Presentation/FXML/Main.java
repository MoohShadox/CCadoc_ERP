package Presentation.FXML;

import Interfaces.Controllable;
import POJO.Contact;
import POJO.Livre;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) {
        launch ( args );
    }

    @Override
    public void start(Stage primaryStage) throws IOException, IllegalAccessException, SQLException {
        Gestionaire_Interface G = new Gestionaire_Interface ( primaryStage );
        //Controllable<Inventorier> C =G.switchPanel("Completion_Inventaire.fxml", "Informations Téléchargées");
        Controllable<Contact> C =G.switchPanel("VisualisationContact.fxml", "Informations Téléchargées");
        C.setVisuel();
        /*LinkedList <Inventorier> Co = new LinkedList <> ( );
        C.RefreshCollection ( Co );
        C.setVisuel ( );
        System.out.println("Fini");*/
    }
}
