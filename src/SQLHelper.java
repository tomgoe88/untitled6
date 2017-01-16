
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


/**
 *
 * @author Jutom
 */
@ManagedBean
@SessionScoped
public class SQLHelper{
    private static Connection con = null;
    private static String dbHost = "localhost"; // Hostname
    private static String dbPort = "3306";      // Port -- Standard: 3306
    private static String dbName = "Terminplaner";   // Datenbankname
    private static String dbUser = "root";     // Datenbankuser
    private static String dbPass = "times-88";      // Datenbankpasswort

    private SQLHelper(){
        try {
            Class.forName("com.mysql.jdbc.Driver"); // Datenbanktreiber für JDBC Schnittstellen laden.

            // Verbindung zur JDBC-Datenbank herstellen.
            con = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+ dbPort+"/"+dbName+"?"+"user="+dbUser+"&"+"password="+dbPass);
        } catch (ClassNotFoundException e) {
            System.out.println("Treiber nicht gefunden");
        } catch (SQLException e) {
            System.out.println("Verbindung nicht moglich");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    private static Connection getInstance() {
        if(con == null)
            new SQLHelper();
        return con;
    }
    //so wie diese Methode wird auch die Methode um die Termine zu holen verwenden also : mit public static
    public static List<Termine> termine(Date chooseDate){
        List<Termine> termine= new ArrayList<Termine>();
        return termine ;
    }
    //Gebe Tabelle in die Konsole aus
    public static void printNameList(){
        con = getInstance();

        if(con != null){
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();

                // Tabelle anzeigen
                String sql =
                        "SELECT kundeid, vorname, nachname, email FROM kunde";
                ResultSet result = query.executeQuery(sql);

                // Ergebnisstabelle durchforsten
                while (result.next()) {
                    String kundeid = result.getString("kundeid");
                    String vorname = result.getString("vorname");
                    String nachname = result.getString("nachname");
                    String email = result.getString("email");
                    String info = kundeid + ", " + vorname + ", " + nachname + ", " + email;

                    System.out.println(info);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
