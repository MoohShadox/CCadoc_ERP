package Presentation.FXML;

import Interfaces.Controllable;
import Presentation.Controller.Gestionnaire_Repertoire;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Gestionaire_Interface {

    private Stage PS;
    double xOffset = 0;
    double yOffset = 0;

    public Gestionaire_Interface() {
    }

    public Gestionaire_Interface(Stage ps) {
        PS = ps;
        PS.initStyle(StageStyle.TRANSPARENT);
    }


    /*
    Ici, je me sers de l'interface controllable, au final tout ce dont j'ai besoin au moment de changer de fenetre ou d'en créer une nouvelle c'est le titre
    de la fenetre et le chemin du Présentation.FXML correspondant, j'ai aussi besoin qu'elle me renvoie le controlleur
    Etant donné que c'est impossible et fastidieux de faire une méthode par controlleur
    J'ai factorisé les controlleurs grace a l'interface controllable (implementé par la classe abstraite), ainsi je récupère via cette méthode un objet
    dont je peux me servir de 3 façon : en lui inférant une collection, en lui demandant de rafraichir sa collection observable sur ça collection, ou en lui inférant une donnée simple
    Considérant que c'est les 3 utilisations les plus courantes que je fais d'un controlleur c'est suffisant pour économiser max de code (voir le main)
     */

    public Gestionnaire_Repertoire switchPanel(String fichier, String titre) throws IOException {
        FXMLLoader loader = new FXMLLoader ( );
        loader.setLocation ( Main.class.getResource ( fichier ) );
        Parent root = loader.load ( );
        Gestionnaire_Repertoire FOP = loader.getController ( );
        PS.setTitle ( titre );
        moveStage(PS,root);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        PS.setScene(scene);
        PS.show ( );
        return FOP;
    }


    public void moveStage(Stage PS,Parent root){

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                xOffset = event.getSceneX();
                yOffset = event.getSceneY();

            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                PS.setX(event.getScreenX() - xOffset);
                PS.setY(event.getScreenY() - yOffset);
            }
        });
    }
}
