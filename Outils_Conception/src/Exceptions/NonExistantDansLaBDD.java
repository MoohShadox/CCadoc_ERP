package Exceptions;

public class NonExistantDansLaBDD extends Exception{
    public NonExistantDansLaBDD(){
        super("L'objet que vous rechercher n'est pas dans la base de données");
    }
}
