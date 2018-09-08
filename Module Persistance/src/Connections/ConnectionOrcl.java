package Connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionOrcl {
    /*Utilisation du pattern Singleton dans sa version simple dans le but d'avoir une connection unique par instance de l'application*/
    private static Connection conn = null;
    String password = "toor";
    String username = "CADOC_ADMIN";
    String url = "jdbc:oracle:thin:@localhost:1521:XE";

    /*Constructeur Privé*/
    private ConnectionOrcl() throws SQLException, ClassNotFoundException {
        Class.forName ( "oracle.jdbc.OracleDriver" );
        conn = DriverManager.getConnection ( url, username, password );
    }

    /*Methode principale qui permet de générer une unique connection a la base de donnée oracle*/
    public static Connection getInstance() {
        if ( conn == null ) {
            try {
                new ConnectionOrcl ( );
            } catch ( SQLException | ClassNotFoundException e ) {
                e.printStackTrace ( );
            }
        }
        return conn;
    }

    public static void disconnect() throws SQLException {
        conn.close ( );
    }

}
