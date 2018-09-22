import Interfaces.Modele;
import POJO.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class Repertoire {
    private ObservableMap<Integer, Modele<Contact>> references = FXCollections.observableHashMap();
    private ObservableList<Object> section_mail = FXCollections.observableArrayList();
}
