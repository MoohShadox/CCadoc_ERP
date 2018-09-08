package Presentation.Controller;

import Composants_Visuels.VisuelCreator;
import Connections.ConnectionOrcl;
import DAO.DAO;
import POJO.Stock;
import DAO.StockDAO;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Addition_Panel_Controller extends Correction_Panel{

    @FXML
    private TableView<SimpleStringProperty> TableAddition;

    @FXML
    void AdditionnerTout(ActionEvent event) throws SQLException, IllegalAccessException {
        DAO<Stock> DS = new StockDAO(new Stock());
        for(String s:qte_stock.keySet())
        {
            ((StockDAO) DS).corriger(s,resultat.get(s),stock);
        }
        setStockEtInventaire(stock,inventaire);
    }

    @Override
    public void remplir_resultats() throws SQLException {

    }

    @Override
    public void setColonne_rez(TableColumn<SimpleStringProperty, String> colonne) {
        colonne.setText("Addition");
        colonne.setCellValueFactory(cell -> {
            return new SimpleStringProperty(resultat.get(cell.getValue().getValue())+"");
        });
    }

    @Override
    public TableView<SimpleStringProperty> provideTable() {
        TableColumn<SimpleStringProperty, String> colonne = new TableColumn<>();

        colonne.setCellValueFactory(cell -> cell.getValue());
        colonne.setCellFactory(new Callback<TableColumn<SimpleStringProperty, String>, TableCell<SimpleStringProperty, String>>() {
            @Override
            public TableCell<SimpleStringProperty, String> call(TableColumn<SimpleStringProperty, String> param) {
                TableCell<SimpleStringProperty, String> T = new TableCell<>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        VBox B = null;
                        final boolean[] style = {false};
                        if(item!=null && !empty)
                        {
                            B = new VBox();
                            B.setAlignment(Pos.CENTER);
                            JFXButton button = new JFXButton("Additionner");
                            button.getStyleClass().add("Soustraire_Additionner");
                            button.setPrefSize(TableAddition.getColumns().get(3).getWidth(),TableAddition.getFixedCellSize());
                            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    try {
                                        //TODO Si la couleur est déja verte on ne peut pas re additionner
                                        DAO<Stock> D  =new StockDAO(new Stock());
                                        ((StockDAO) D).corriger(item,resultat.get(item),stock);
                                        setStockEtInventaire(stock,inventaire);
                                        //TODO Changement de couleur en vert pour indiquer qu'une addition a déja été faite
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                    colonne.setCellValueFactory(cell -> cell.getValue());
                                    colonne.setCellFactory(new Callback<TableColumn<SimpleStringProperty, String>, TableCell<SimpleStringProperty, String>>() {
                                        @Override
                                        public TableCell<SimpleStringProperty, String> call(TableColumn<SimpleStringProperty, String> param) {
                                            TableCell<SimpleStringProperty, String> T = new TableCell<>() {
                                                @Override
                                                protected void updateItem(String item, boolean empty) {
                                                    Label l = new Label("Additionné");
                                                    l.getStyleClass().add("Label");
                                                    l.setStyle("-fx-background-color: #26A69A");
                                                    l.setStyle("-fx-text-fill: #FFFFFF");

                                                    setGraphic(l);
                                                }
                                            };
                                            return T;
                                        }});
                                }
                            });
                            B.getChildren().add(button);
                        }
                        setGraphic(B);
                    }
                };
                return T;
            }
        });
        TableAddition.getColumns().clear();
        TableAddition.getColumns().add(colonne);
        TableAddition.getItems().clear();

        VisuelCreator VC=new VisuelCreator();
        TableAddition.setColumnResizePolicy((param) -> true);
        Platform.runLater(() -> VC.customResize(TableAddition));

        return TableAddition;
    }

    @Override
    protected void remplissageDonnee() throws SQLException {
        String isbn;
        String titre;
        int quantite,qte;

        Statement S = ConnectionOrcl.getInstance().createStatement();
        ResultSet RS;
        RS = S.executeQuery("SELECT EI.ISBN,TITRE,QTE FROM EST_INVENTORIER EI ,PRODUIT P WHERE P.ISBN=EI.ISBN AND EI.INVENTAIRE='"+inventaire+"'");
        while (RS.next()){
            isbn = RS.getString(1);
            titre = RS.getString(2);
            titres_stock.put(isbn,titre);
            qte_inventaire.put(isbn,RS.getInt(3));
            list.add(new SimpleStringProperty(isbn));
        }

        for (String s:qte_inventaire.keySet()){
            RS = S.executeQuery("SELECT ISBN,QUANTITE FROM EST_DANS ED WHERE NOM_S=\'"+stock+"\' AND ISBN=\'"+s+"\'");
            if(RS.next()){
                qte_stock.put(RS.getString(1),RS.getInt(2));
            }
            qte_stock.putIfAbsent(s,0);
            resultat.put(s,qte_stock.get(s)+qte_inventaire.get(s));
        }
    }

    @FXML
    private void fermerFenetre() {
        this.fermerFenetre(TableAddition);
    }

    @FXML
    private void Iconify() {
        this.Iconify(TableAddition);
    }
}
