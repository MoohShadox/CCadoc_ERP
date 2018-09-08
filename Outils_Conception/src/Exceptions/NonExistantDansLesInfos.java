package Exceptions;

public class NonExistantDansLesInfos extends Exception {
    public NonExistantDansLesInfos(){
        super("Non existant dans la base de données des infos");
    }
}
