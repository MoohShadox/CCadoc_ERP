package DAO;

import Connections.ConnectionOrcl;
import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public abstract class DAO<Type> {

    protected Connection c = ConnectionOrcl.getInstance ( );

    public abstract void ajouter(Type T) throws SQLException, IllegalAccessException;

    public abstract void supprimer(Type T) throws SQLException, IllegalAccessException;

    public abstract Type recuperer(String isbn) throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD, BuildingException, NonExistantDansLaBDD;

    public abstract Collection <Type> load() throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD, NonExistantDansLaBDD;

    public abstract Type mettre_a_jour(Type T, String reference) throws SQLException, NonExistantDansLaBDD, IllegalAccessException;

}
