package Presentation.Controller;


import Composants_Visuels.VisuelCreator;
import Connections.ConnectionOrcl;
import DAO.DAO;
import POJO.Stock;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import DAO.StockDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Soustraction_Panel_Controller extends Correction_Panel{

    @FXML
    private TableView<SimpleStringProperty> TableSoustraction;

    @FXML
    void SoustraireTout(ActionEvent event) throws SQLException, IllegalAccessException {
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
        colonne.setText("Soustraction");
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
                        String style = "";
                        if(item!=null && !empty)
                        {
                            B = new VBox();
                            JFXButton button = new JFXButton("Soustraire");
                            button.getStyleClass().add("Soustraire_Additionner");
                            button.setPrefSize(TableSoustraction.getColumns().get(3).getWidth(),TableSoustraction.getFixedCellSize());
                            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    try {
                                        if(getStyle().equalsIgnoreCase("-fx-background-color: #5c806f"))
                                        {
                                            //TODO Si la couleur est déja verte on ne peut pas re soustraire
                                        }
                                        DAO<Stock> D  =new StockDAO(new Stock());
                                        ((StockDAO) D).corriger(item,resultat.get(item),stock);
                                        setStockEtInventaire(stock,inventaire);
                                        button.setStyle("-fx-background-color: #5c806f");
                                        //TODO Changement de couleur en vert pour indiquer qu'une addition a déja été faite
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
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
        TableSoustraction.getColumns().clear();
        TableSoustraction.getColumns().add(colonne);
        TableSoustraction.getItems().clear();
        VisuelCreator VC=new VisuelCreator();
        TableSoustraction.setColumnResizePolicy((param) -> true);
        Platform.runLater(() -> VC.customResize(TableSoustraction));
        return TableSoustraction;
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
            if(qte_stock.get(s)!=0)
            {
                resultat.put(s,qte_stock.get(s)-qte_inventaire.get(s));
            }
        }
    }

    @FXML
    private void fermerFenetre() {
        this.fermerFenetre(TableSoustraction);
    }

    @FXML
    private void Iconify(ActionEvent e) {
        this.Iconify(TableSoustraction);
    }
}
