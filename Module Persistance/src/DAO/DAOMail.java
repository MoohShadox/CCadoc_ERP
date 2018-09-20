package DAO;

import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.Mail;

import java.sql.SQLException;
import java.util.Collection;

public class DAOMail extends GenericDAO<Mail> {

    public DAOMail(Mail T) throws SQLException, IllegalAccessException {
        super(T);
    }

    @Override
    public Collection<Mail> load() throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD, NonExistantDansLaBDD {
        return null;
    }
}
