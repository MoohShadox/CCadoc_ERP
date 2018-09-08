package Interfaces;

import java.util.HashMap;

public interface DAOAble<Type> extends Buildable <Type> {
    HashMap <String, String> getRepositoryAttributs() throws IllegalAccessException;

    String getTableName();

    String getReference();

    String getKeyName();
}
