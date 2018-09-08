package Presentation.Controller;

import Java_Python_Exploitation.PythonCaller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Controlleur_InstallationPanel {

    @FXML
    private TextField Interpreteur_Field;

    @FXML
    private TextField Project_Field;

    @FXML
    void Configurer_Action(ActionEvent event) throws IOException {
        if ( Interpreteur_Field.getText ( ).isEmpty ( ) || Project_Field.getText ( ).isEmpty ( ) ) {
            Alert A = new Alert ( Alert.AlertType.ERROR );
            A.setTitle ( "Erreur Arguments manquant" );
            A.setHeaderText ( "Vous n'avez pas saisi tout les liens" );
            A.setContentText ( "Il semblerait que vous ayez oublié de remplir un champ, tout les champs sont obligatoires sinon je n'aurais pas pris la peine de programmer cette interface..." );
            A.show ( );
            return;
        }
        File F = new File ( "config.txt" );
        DataOutputStream FOS = new DataOutputStream ( new FileOutputStream ( F ) );
        FOS.writeBytes ( Interpreteur_Field.getText ( ) + "\n" );
        FOS.writeBytes ( Project_Field.getText ( ) );
        System.out.println ( "Fin du programme sans erreur" );
        PythonCaller.installer ( Interpreteur_Field.getText ( ), Project_Field.getText ( ) );
        if ( !PythonCaller.test_fonctionnement ( ) ) {
            Alert A = new Alert ( Alert.AlertType.ERROR );
            A.setTitle ( "Interpreteur non valide" );
            A.setHeaderText ( "Le chemin que vous avez rentré ne correspond pas a un interpréteur valide" );
            A.setContentText ( "Vérifiez que l'interpreteur contient toutes les dependances necessaire et que les chemins sont valides" );
            A.show ( );
            return;
        }
        Alert A = new Alert ( Alert.AlertType.CONFIRMATION );
        A.setTitle ( "Intepréteur configuré" );
        A.setContentText ( "Vous pouvez desormais utiliser les fonctions d'enrichissement de la base de donnée\"" );
        A.show ( );
    }

}
