
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.*;
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
    private String dbName = "Terminplaner";   // Datenbankname
    private static String dbUser = "root";     // Datenbankuser
    private static String dbPass = "times-88";      // Datenbankpasswort
    private static String sql = "CREATE DATABASE IF NOT EXISTS DEMODB";
    private static SQLHelper aktuell = new SQLHelper();

    private SQLHelper(){
        try {
            Class.forName("com.mysql.jdbc.Driver"); // Datenbanktreiber für JDBC Schnittstellen laden.

            // Verbindung zur JDBC-Datenbank herstellen.
            con = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+ dbPort+"/"+"user="+dbUser+"&"+"password="+dbPass);
            PreparedStatement stmt = con.prepareStatement(sql);

                stmt.execute();
        } catch (ClassNotFoundException e) {
            System.out.println("Treiber nicht gefunden");
        } catch (SQLException e) {
            System.out.println("Verbindung nicht moglich");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }

    }

    public static SQLHelper getAktuell() {
        return aktuell;
    }

    public static void setAktuell(SQLHelper aktuell) {
        SQLHelper.aktuell = aktuell;
    }

    private static Connection getInstance() {
        if(con == null)
            new SQLHelper();
        return con;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
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

    public static void createTables(){
        con= getInstance();
        try {
            String tableMitarbeiter="" +
                    "CREATE TABLE IF NOT EXISTS mitarbeiter(" +
                    "MitarbeiterID int NOT NULL AUTO_INCREMENT," +
                    "vorname VARCHAR(45)," +
                    "nachname VARCHAR(45)," +
                    "kalenderfarbe VARCHAR(45)" +
                    "PRIMARY KEY (MitarbeiterID)" +
                    ")";
            con.createStatement().executeUpdate(tableMitarbeiter);
        } catch (SQLException e){
            e.getErrorCode();
        }
        try {
            String tableKunden="" +
                    "CREATE TABLE IF NOT EXISTS mitarbeiter(" +
                    "KundenID int NOT NULL AUTO_INCREMENT," +
                    "vorname VARCHAR(45)," +
                    "nachname VARCHAR(45)," +
                    "telefonnummer VARCHAR (45)" +
                    "PRIMARY KEY (KundenID)" +
                    ")";
            con.createStatement().executeUpdate(tableKunden);
        } catch (SQLException e){
            e.getErrorCode();
        }
        try {
            String tableTermine="" +
                    "CREATE TABLE IF NOT EXISTS termin(" +
                    "TerminID int NOT NULL AUTO_INCREMENT," +
                    "MitarbeiterMacherID int," +
                    "KundenID," +
                    "Terminstart DATETIME," +
                    "Terminende DATETIME," +
                    "Terminart VARCHAR (200)," +
                    "Beschreibung TEXT," +
                    "MitarbeiterSchreiberID int," +
                    "PRIMARY KEY (TerminID)" +
                    "FOREIGN KEY (MitarbeiterMacherID) REFERENCES mitarbeiter(MitarbeiterID)" +
                    "FOREIGN KEY (MitarbeiterSchreiberID) REFERENCES mitarbeiter(MitarbeiterID)" +
                    ")";
            con.createStatement().executeUpdate(tableTermine);
        } catch (SQLException e){
            e.getErrorCode();
        }
        try {
            String tableArbeitszeiten="" +
                    "CREATE TABLE IF NOT EXISTS arbeitszeiten(" +
                    "ArbeitszeitID int NOT NULL AUTO_INCREMENT," +
                    "MitarbeiterID int," +
                    "Schichtbeginn DATETIME," +
                    "Schichtende DATETIME," +
                    "PRIMARY KEY (ArbeitszeitID)," +
                    "FOREIGN KEY (MitarbeiterID) REFERENCES mitarbeiter(MitarbeiterID)" +
                    ")";
            con.createStatement().executeUpdate(tableArbeitszeiten);
        } catch (SQLException e){
            e.getErrorCode();
        }
    }
    //TODO: hier müssen noch weiter Methoden aufgeschrieben werden
    public static List<Mitarbeiter> getMitarbeiterListe(){
        con = getInstance();
        return new ArrayList<Mitarbeiter>();
    }
    public void newMitarbeiter(String vorname, String nachname, String farbe){
        con = getInstance();
    //TODO insert statement
    }
    public void neuerTermin (int MitarbeiterID, int KundenID, String Beschreibung, String Terminart, Date start, Date end ){
        con = getInstance();
        //TODO InsertSdtatement
    }
    public static List<FullCalendarEventBean> getAllEvents(int mitarbeiter){//hier sollen die Events geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        // TODO innerjoin auf die tabeller auf Kunde, Mitarbeiter und Termine
        return new ArrayList<>();
    }
    public static List<FullCalendarEventBean> getAllArbeitszeiten(int mitarbeiter){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        //TODO nnerjoin auf die tabeller  und Arbeitszeiten
        return new ArrayList<>();
    }
    public static void newArbeitszeit(int mitarbeiterID, Date schichtbeginn, Date schichtende){
        con = getInstance();
        //TODO Anwesenheitstabelle befüllen
    }

}
