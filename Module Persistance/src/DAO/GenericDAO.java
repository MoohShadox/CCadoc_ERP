package DAO;

import Exceptions.BuildingException;
import Exceptions.NonExistantDansLaBDD;
import Interfaces.DAOAble;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class GenericDAO<Type extends DAOAble<Type>> extends DAO <Type> {

    protected Type objet;

    public GenericDAO(Type T) throws SQLException, IllegalAccessException {
        PreparedStatement S = c.prepareStatement ( "SELECT UNIQUE TNAME FROM SYSTEM.COL" );
        LinkedList <String> liste_colonnes = new LinkedList <> ( );
        ResultSet RS = S.executeQuery ( );
        LinkedList <String> SD = new LinkedList <> ( );
        boolean b = false;
        objet = T;
        while (RS.next ( )) {
            SD.add ( RS.getString ( 1 ) );
        }
        RS.close ( );
        S = c.prepareStatement ( "SELECT CNAME FROM SYSTEM.COL WHERE TNAME=" + "\'" + T.getTableName ( ) + "\'" );
        RS = S.executeQuery ( );
        while (RS.next ( )) {
            liste_colonnes.add ( RS.getString ( 1 ) );
        }
        if ( !SD.contains ( T.getTableName ( ) ) ) {
            S = c.prepareStatement ( "CREATE TABLE " + T.getTableName ( ) + "( " + T.getKeyName ( ) + " VARCHAR2(15) PRIMARY KEY ) " );
            S.executeQuery ( );
        }
        for (String attributs : T.getRepositoryAttributs ( ).keySet ( )) {
            b = false;
            for (String SS : liste_colonnes) {
                if ( SS.equalsIgnoreCase ( attributs ) ) {
                    b = true;
                    break;
                }
            }
            if ( !b ) {
                S = c.prepareStatement ( "ALTER TABLE " + T.getTableName ( ) + " ADD \"" + attributs + "\" VARCHAR2(3500)" );
                S.executeQuery ( );
            }
        }
        S.close ( );
    }


    @Override
    public void ajouter(Type T) throws SQLException, IllegalAccessException {
        PreparedStatement S = c.prepareStatement ( "INSERT INTO " + T.getTableName ( ) + " ( \"" + T.getKeyName ( ) + "\" ) VALUES(\'" + T.getReference ( ) + "\') " );
        S.executeQuery ( );
        HashMap <String,String> H = T.getRepositoryAttributs();
        for (String s : T.getRepositoryAttributs ( ).keySet ( )) {
            if(s.equalsIgnoreCase(T.getKeyName()))
                continue;
            S = c.prepareStatement ( "UPDATE " + T.getTableName ( ) + " SET \"" + s + "\" = \'" +
                    T.getRepositoryAttributs ( ).get ( s ) + " \' WHERE (\""+T.getKeyName()+"\"=\'"+T.getReference()+"\')");
            System.out.println( "UPDATE " + T.getTableName ( ) + " SET \"" + s + "\" = \'" +
                    T.getRepositoryAttributs ( ).get ( s ) + " \' WHERE (\""+T.getKeyName()+"\"=\'"+T.getReference()+"\')");
            try{
            S.executeQuery ( );}catch (SQLException e){
                System.out.println("Exception de type : " + e.getMessage());
            }
        }
        S.close ( );
    }

    @Override
    public void supprimer(Type T) throws SQLException, IllegalAccessException {
        PreparedStatement S = c.prepareStatement ( "DELETE FROM " + T.getTableName ( ) +" WHERE \""+T.getKeyName()+"\" = '"+T.getReference()+"'");
        S.executeQuery ( );
        S.close();
    }

    @Override
    public void supprimer(String isbn) throws SQLException, IllegalAccessException, NonExistantDansLaBDD, BuildingException {
        PreparedStatement S = c.prepareStatement ( "DELETE FROM " + objet.getTableName() +" WHERE \""+objet.getKeyName()+"\" = '"+isbn+"'");
        S.executeQuery ( );
        S.close();
    }


    @Override
    public Type recuperer(String isbn) throws SQLException, BuildingException, IllegalAccessException, NonExistantDansLaBDD {

        return objet.buildFromRepData( fetch ( isbn ) );
    }

    public HashMap <String, String> fetch(String reference) throws SQLException, NonExistantDansLaBDD {
        HashMap <String, String> attributs_fetched = new HashMap <> ( );
        Statement S = c.createStatement ( ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
        ResultSet RS = S.executeQuery ( "SELECT * FROM " + objet.getTableName ( ) + " WHERE \"" + objet.getKeyName ( ) + "\" = '" + reference + "'" );
        ResultSetMetaData RMD = RS.getMetaData ( );
        if(!RS.next())
            throw new NonExistantDansLaBDD();
        int nb = RMD.getColumnCount ( );
        LinkedList <String> E = new LinkedList <> ( );
        for (int i = 1; i <= nb; i++) {
            E.add ( RMD.getColumnName ( i ) );
        }
        for (String e : E) {
            try{
                String att = RS.getString ( e );
                attributs_fetched.put ( e, att );
            }catch (Exception ex){
                System.out.println("une exception de type : " + ex.getMessage());
            }
        }
        RS.close ( );
        S.close ( );
        return attributs_fetched;
    }

    public Type mettre_a_jour(Type T, String reference) throws SQLException, IllegalAccessException, NonExistantDansLaBDD {
        PreparedStatement S = null;
        fetch(reference);
        for (String s : T.getRepositoryAttributs ( ).keySet ( )) {
            S = c.prepareStatement ( "UPDATE " + T.getTableName ( ) + " SET \"" + s + "\" = \'" +
                    T.getRepositoryAttributs ( ).get ( s ) + "\' WHERE (\""+T.getKeyName()+"\"=\'"+reference+"\')");
            System.out.println( "UPDATE " + T.getTableName ( ) + " SET \"" + s + "\" = \'" +
                    T.getRepositoryAttributs ( ).get ( s ) + "\' WHERE (\""+T.getKeyName()+"\"=\'"+reference+"\')");
            S.executeQuery ( );
        }
        if(S!=null)
            S.close ( );
        return T;
    }


    public void updateNumC(long numC , Type T) throws SQLException {
        PreparedStatement s =c.prepareStatement("UPDATE "+ T.getTableName()+" SET NUMC = '"+numC+"' WHERE (\""+T.getKeyName()+"\"=\'"+T.getReference()+"\')");
        System.out.println("UPDATE "+ T.getTableName()+" SET NUMC = '"+numC+"' WHERE (\""+T.getKeyName()+"\"=\'"+T.getReference()+"\')");
        s.executeQuery();
        s.close();
    }

}
