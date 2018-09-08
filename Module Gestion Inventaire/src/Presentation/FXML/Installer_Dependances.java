package Presentation.FXML;


import Presentation.Controller.Controlleur_InstallationPanel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Installer_Dependances extends Application {

    private Stage PS;

    public static void main(String[] args) {
        launch ( args );
    }

    public void viewInstallation_Panel() throws IOException {
        FXMLLoader loader = new FXMLLoader ( );
        loader.setLocation ( Installer_Dependances.class.getResource ( "Install_Panel.fxml" ) );
        Parent root = loader.load ( );
        Controlleur_InstallationPanel FOP = loader.getController ( );
        PS.setTitle ( "Configuration Dependances" );
        PS.setScene ( new Scene ( root, 1000, 1000 ) );
        PS.setMaximized ( true );
        PS.show ( );
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        PS = primaryStage;
        viewInstallation_Panel ( );
    }
}
