package Presentation.Controller;

import Connections.ConnectionOrcl;
import DAO.StockDAO;
import DAO.DAO;
import POJO.Stock;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicLong;

public class Verification_Panel_Controller extends Correction_Panel {

    @FXML
    private TableView<SimpleStringProperty> TableComparaison;



    @FXML
    void initialize() throws SQLException, IllegalAccessException {
        TableComparaison.setColumnResizePolicy((param) -> true);
        Platform.runLater(() -> customResize(TableComparaison));
    }

    public void remplir_resultats() throws SQLException {
        String isbn;
        int quantite,qte;
        Statement S = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS = S.executeQuery("SELECT P.ISBN,QUANTITE,QTE FROM PRODUIT P,EST_DANS ED ,EST_INVENTORIER EI WHERE ED.ISBN=EI.ISBN AND P.ISBN=ED.ISBN " +
                "AND ED.NOM_S=\'"+stock+"\' AND EI.INVENTAIRE=\'"+inventaire+"\'");
        while(RS.next()){
            isbn = RS.getString(1);
            qte = RS.getInt(3);
            quantite = RS.getInt(2);
            resultat.put(isbn,(qte==quantite)?1:0);
        }
    }

    @Override
    public void setColonne_rez(TableColumn<SimpleStringProperty, String> colonne) {
        colonne.setCellFactory(new Callback<TableColumn<SimpleStringProperty, String>, TableCell<SimpleStringProperty, String>>() {
            @Override
            public TableCell<SimpleStringProperty, String> call(TableColumn<SimpleStringProperty, String> param) {
                TableCell<SimpleStringProperty,String> T = new TableCell<SimpleStringProperty,String>()
                {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        VBox V = null;
                        String style = "";
                        if(!empty && item!=null) {
                            if (resultat.containsKey(item) && resultat.get(item) == 1) {
                                //TODO Mettre la couleur de la case en vert et la laisser vide
                                V = new VBox();
                                Label L = new Label("CONCORDANCE");
                                L.setStyle("-fx-text-fill: #FFFFFF");
                                V.getChildren().add(L);
                                V.setAlignment(Pos.CENTER);
                                style = "-fx-background-color: #26A69A";
                            } else {
                                //TODO Mettre une couleur rouge sur la cellule et styliser le bouton
                                JFXButton B = new JFXButton("Corriger");
                                B.getStyleClass().add("Soustraire_Additionner");
                                B.setStyle("-fx-background-color: transparent");
                                B.setPrefSize(TableComparaison.getColumns().get(3).getWidth(), TableComparaison.getFixedCellSize());
                                B.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        try {
                                            DAO<Stock> D = new StockDAO(new Stock());
                                            ((StockDAO) D).corriger(item, qte_inventaire.get(item), stock);
                                            resultat.put(item, 1);
                                            updateItem(item, empty);
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                V = new VBox(B);
                                V.setAlignment(Pos.CENTER);
                                style = "-fx-background-color: #FF9E80";
                            }
                        }
                        setStyle(style);
                        setGraphic(V);
                    }
                };
                return T;
            }
        });

    }

    public void  customResize(TableView<?> view) {

        AtomicLong width = new AtomicLong();
        view.getColumns().forEach(col -> {
            width.addAndGet((long) col.getWidth());
        });
        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            view.getColumns().forEach(col -> {
                col.setPrefWidth(col.getWidth() + ((tableWidth - width.get()) / (view.getColumns().size())));
            });
        }
    }

    @Override
    public TableView<SimpleStringProperty> provideTable() {
        return TableComparaison;
    }

    @Override
    protected void remplissageDonnee() throws SQLException {
        String isbn;
        String titre;
        int quantite,qte;
        Statement S = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS = S.executeQuery("SELECT P.ISBN,QUANTITE,QTE,TITRE FROM PRODUIT P,EST_DANS ED ,EST_INVENTORIER EI WHERE ED.ISBN=EI.ISBN AND P.ISBN=ED.ISBN " +
                "AND ED.NOM_S=\'"+stock+"\' AND EI.INVENTAIRE=\'"+inventaire+"\'");
        while(RS.next()){
            isbn = RS.getString(1);
            qte = RS.getInt(3);
            quantite = RS.getInt(2);
            titre = RS.getString(4);
            titres_stock.put(isbn,titre);
            qte_stock.put(isbn,quantite);
            qte_inventaire.put(isbn,qte);
            list.add(new SimpleStringProperty(isbn));
        }
    }

    @FXML
    private void fermerFenetre() {
        this.fermerFenetre(TableComparaison);
    }

    @FXML
    private void Iconify() {
        this.Iconify(TableComparaison);
    }

}