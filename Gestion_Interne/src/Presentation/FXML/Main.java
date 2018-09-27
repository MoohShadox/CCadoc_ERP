package Presentation.FXML;


import DAO.DAO;
import DAO.DAOLivre;
import POJO.Livre;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Main extends Application {

    public static void main(String[] args) {
        launch ( args );
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Gestionaire_Interface G = new Gestionaire_Interface(primaryStage);
        DAO<Livre> DL = new DAOLivre(new Livre());
        LinkedList<Livre> LL = new LinkedList<>();
        int i=0;
        for(Livre L:DL.load()){
            LL.add(L);
            if(i==50)
                break;
            i++;
        }
        G.switchPanel("Impression.fxml","TITRE").RefreshCollection(LL);
    }


}
