package Exceptions;

public class ExistantDansLaPieceException extends Exception {
    public ExistantDansLaPieceException(){
        super("Objet déja existant dans la pièce");
    }
}
