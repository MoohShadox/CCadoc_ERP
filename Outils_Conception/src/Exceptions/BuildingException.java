package Exceptions;

public class BuildingException extends Exception {

    public BuildingException() {
        super ( "Building impossible, les paramètres passés ne concorde pas avec la structure de l'objet" );
    }

}
