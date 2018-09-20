package Presentation.Control;

import DAO.DAO;
import DAO.DAOLivre;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.Controlleur_Visualisable;
import Interfaces.Modele;
import POJO.Livre;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

public class Corps_Facture extends Controlleur_Visualisable<Livre> {

    @FXML
    protected TableView<Modele<Livre>> Details_Commande;


    @Override
    public void setVisuel() {
        HashMap<String, TableColumn<Modele<Livre>, String>> H = new HashMap<>();
        H.putAll(implenter_visuel(Details_Commande));
        TableColumn<Modele<Livre>,String> T = new TableColumn<>("Prix H.T");
        T.setCellValueFactory(cell-> new SimpleStringProperty(""+cell.getValue().getSource().getPrix_ht()));
        Details_Commande.getColumns().add(T);
        H.put(T.getText(),T);
        T = new TableColumn<>("Qte");
        T.setCellValueFactory(cell -> new SimpleStringProperty(0+""));
        Details_Commande.getColumns().add(T);
        H.put(T.getText(),T);
        T = new TableColumn<>("Total H.T");
        T.setCellValueFactory(cell -> new SimpleStringProperty(0+""));
        Details_Commande.getColumns().add(T);
        H.put(T.getText(),T);
        for(String s:H.keySet()){

            H.get(s).setStyle("-fx-font-size: 7.5pt");
            H.get(s).setCellFactory(new Callback<TableColumn<Modele<Livre>, String>, TableCell<Modele<Livre>, String>>() {
                @Override
                public TableCell<Modele<Livre>, String> call(TableColumn<Modele<Livre>, String> param) {
                    TableCell<Modele<Livre>, String> T = new TableCell<>(){
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            Node N = null;
                            if(item != null && !empty){
                                Text L = new Text(item.trim());
                                /*TextFlow TF = new TextFlow(L);*/
                                /*L.setBoundsType(TextBoundsType.VISUAL);
                                L.setStrokeType();*/
                                H.get(s).widthProperty().addListener(new ChangeListener<Number>() {
                                    @Override
                                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                                        L.setWrappingWidth(Double.valueOf(newValue+""));
                                    }
                                });
                                switch (s){
                                    case "TITRE":
                                        L.setWrappingWidth(110);
                                        break;
                                    case "DATE_P":
                                        L.setWrappingWidth(45);
                                        break;
                                    case "AUTEUR":
                                        L.setWrappingWidth(100);
                                        break;
                                    case "ISBN":
                                        L.setWrappingWidth(50);
                                        break;
                                    case "Prix H.T":
                                    case "Qte":
                                    case "Total H.T":
                                        L.setWrappingWidth(30);
                                        break;
                                    default:
                                        L.setWrappingWidth(70);

                                }
                                L.setStyle("-fx-font-size: 6.5pt");
                                N = L;
                            }
                            setGraphic(N);
                        }
                    };
                    T.setMaxHeight(1);
                    return T;
                }
            });
            if(s.equalsIgnoreCase("ISBN"))
                H.get(s).setVisible(false);
        }
    }
}
