package Presentation.Control;


import Connections.ConnectionOrcl;
import DAO.DAO;
import DAO.EmployesDAO;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.Controlleur_Visualisable;
import Interfaces.Modele;
import POJO.Employe;
import POJO.Postes;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.print.attribute.standard.DateTimeAtProcessing;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;

public class GestionEmployes extends Controlleur_Visualisable<Employe> {

    @FXML
    private TextField ChampRecherche;

    @FXML
    private Button AjouterBouton;

    @FXML
    private TableView<Modele<Employe>> TableEmployes;

    EventHandler<TableColumn.CellEditEvent<Modele<Employe>, String>> E = new EventHandler<TableColumn.CellEditEvent<Modele<Employe>, String>>() {
        @Override
        public void handle(TableColumn.CellEditEvent<Modele<Employe>, String> event) {
            try {
                DAO<Employe> D = new EmployesDAO(new Employe());
                Statement S = ConnectionOrcl.getInstance().createStatement();
                S.executeUpdate("UPDATE EMPLOYE SET \""+event.getTableColumn().getText()+"\"=\'"+event.getNewValue()+"\' WHERE NUM_E=\'"+event.getRowValue().getSource().getReference()+"\'");
            } catch (SQLException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    };



    @Override
    public void setVisuel() {
        TableEmployes.setEditable(true);
        HashMap<String, TableColumn<Modele<Employe>, String>> H = new HashMap<>();
        H = super.implenter_visuel(TableEmployes);
        for(String S:H.keySet()){

            if(S.equalsIgnoreCase("NOM") || S.equalsIgnoreCase("PRENOM"))
            {
                H.get(S).setCellFactory(TextFieldTableCell.forTableColumn());
                H.get(S).setEditable(true);
            }
            if(S.equalsIgnoreCase("POSTE"))
            {
                H.get(S).setCellFactory(cell -> {
                    ComboBoxTableCell<Modele<Employe>,String> CBTC = new ComboBoxTableCell();
                    for(Postes P:Postes.values())
                    {
                        CBTC.getItems().add(P.toString());
                    }
                    return CBTC;
                });
                H.get(S).setEditable(true);
            }
            H.get(S).setOnEditCommit(E);
        }
        try {
            RefreshCollection();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void initialize() throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException, BuildingException {
        DAO<Employe> D = new EmployesDAO(new Employe());
        collection = D.load();
        RefreshCollection();
        setVisuel();
    }












}
