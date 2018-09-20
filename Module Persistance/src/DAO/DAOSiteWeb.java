package DAO;

import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import POJO.SiteWeb;

import java.sql.SQLException;
import java.util.Collection;

public class DAOSiteWeb extends GenericDAO<SiteWeb> {

    public DAOSiteWeb(SiteWeb T) throws SQLException, IllegalAccessException {
        super(T);
    }

    @Override
    public Collection<SiteWeb> load() throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD, NonExistantDansLaBDD {
        return null;
    }
}
