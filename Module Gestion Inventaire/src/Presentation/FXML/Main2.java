package Presentation.FXML;

import Interfaces.Controllable;
import POJO.Inventorier;
import POJO.Livre;
import Presentation.Controller.Modele_Piece;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class Main2 extends Application {

    public static void main(String[] args) {
        launch ( args );
    }

    @Override
    public void start(Stage primaryStage) throws IOException, IllegalAccessException, SQLException {
        Gestionaire_Interface G = new Gestionaire_Interface ( primaryStage );
        //Controllable<Inventorier> C =G.switchPanel("Gestion_Stock.fxml", "Informations Téléchargées");
        //Controllable<Inventorier> C =G.switchPanel("DashBord.fxml", "Informations Téléchargées");
        //Controllable<Inventorier> C =G.switchPanel("Completion_Inventaire.fxml", "Informations Téléchargées");
        Controllable<Livre> C =G.switchPanel("Modele_Piece.fxml", "Informations Téléchargées");
        C.setVisuel();
        /*LinkedList <Inventorier> Co = new LinkedList <> ( );
        C.RefreshCollection ( Co );
        C.setVisuel ( );
        System.out.println("Fini");*/
    }
}
