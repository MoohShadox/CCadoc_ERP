package Presentation.Controller;


import DAO.*;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.Controller;
import Interfaces.Controlleur_Visualisable;
import Interfaces.Modele;
import POJO.Livre;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;


import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

public class Modele_Piece extends Controlleur_Visualisable<Livre> {

    @FXML
    private Label Type_Piece;

    @FXML
    private Label Reference;

    @FXML
    private Label Denomination_Client;


    @FXML
    private TableView<Modele<Livre>> Details_Commande;


    @FXML
    public void initialize() throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException {
    }



    public TextFlow creer_case(String val){
        Text T = new Text(val);
        TextFlow TF = new TextFlow(T);
        TF.setStyle("-fx-border-color: #ff9f62");
        return TF;
    }

    public HashMap<String,TextFlow> generer_ligne(Livre L){
        HashMap<String,TextFlow> attributs = new HashMap<>();
        for(String st:L.getPrincipalAttributes().keySet()){
           attributs.put(st,creer_case(L.getPrincipalAttributes().get(st)));
        }
        attributs.put("Prix Unit H.T",creer_case(L.getPrix_ht()+""));
        return attributs;
    }

    @FXML
    void BAction(ActionEvent event) {
        PrinterJob PJ = PrinterJob.createPrinterJob();
        Printer P = Printer.getDefaultPrinter();
        JobSettings JS = PJ.getJobSettings();
        PageLayout PL = JS.getPageLayout();
        double h = 0,w = 0;
        JS.setPageLayout(Printer.getDefaultPrinter().createPageLayout(Paper.A4,PageOrientation.PORTRAIT,1,1,1,1));
        PJ.setPrinter(P);
        System.out.println(PJ.printPage(Details_Commande));
        PJ.endJob();
    }



    @Override
    public void RefreshCollection(Collection<Livre> T) throws IllegalAccessException {

    }

    @Override
    public void setVisuel() {
        DAO<Livre> DL = null;
        HashMap<String, TableColumn<Modele<Livre>, String>> H = new HashMap<>();
        try {
            DL = new DAOLivre(new Livre());
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Collection<Livre> li = DL.load();
            for(Livre L:li)
                super.recensement.ajouter(L);
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
        } catch (SQLException | BuildingException | IllegalAccessException | NonExistantDansLaBDD e) {
            e.printStackTrace();
        }


    }


}
