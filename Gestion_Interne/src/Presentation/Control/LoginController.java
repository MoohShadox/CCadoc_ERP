package Presentation.Control;

import Interfaces.Controllable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.util.Collection;

public class LoginController implements Controllable {

    @FXML
    JFXPasswordField motDePasse;
    @FXML
    JFXButton connexion;

    @Override
    public void setCollection(Collection T) {
    }

    @Override
    public void RefreshCollection(Collection T) {

    }

    @Override
    public void setVisuel() {

    }

    @FXML
    private void fermerFenetre(javafx.event.ActionEvent e) {
        Stage stage = (Stage) connexion.getScene().getWindow();
        stage.close();
    }
}
