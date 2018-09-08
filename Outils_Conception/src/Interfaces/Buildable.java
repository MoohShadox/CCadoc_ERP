package Interfaces;

import Exceptions.BuildingException;

import java.util.HashMap;

public interface Buildable<Type> {
    Type buildFromRepData(HashMap<String, String> H) throws BuildingException, IllegalAccessException;
}
