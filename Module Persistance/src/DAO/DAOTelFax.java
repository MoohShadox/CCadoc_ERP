package DAO;

import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.TelFax;

import java.sql.SQLException;
import java.util.Collection;

public class DAOTelFax extends GenericDAO<TelFax> {

    public DAOTelFax(TelFax T) throws SQLException, IllegalAccessException {
        super(T);
    }

    @Override
    public Collection<TelFax> load() throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD, NonExistantDansLaBDD {
        return null;
    }
}
