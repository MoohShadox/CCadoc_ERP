package Presentation.FXML;

import Client.Repertoire;
import DAO.DAO;
import DAO.DAOContact;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.Contact;
import Presentation.Controller.Gestionnaire_Repertoire;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) {
        launch ( args );
    }

    @Override
    public void start(Stage primaryStage) throws IOException, IllegalAccessException, SQLException, NonExistantDansLaBDD, BuildingException {
        Gestionaire_Interface G = new Gestionaire_Interface ( primaryStage );
        Gestionnaire_Repertoire C = G.switchPanel("VisualisationContact.fxml", "Visualisation Du Répertoire");
        C.provideTable();
    }
}
