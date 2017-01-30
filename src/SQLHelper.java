
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
                    "CREATE TABLE IF NOT EXISTS mitarbeiter( " +
                    "MitarbeiterID int NOT NULL AUTO_INCREMENT, " +
                    "vorname VARCHAR(45), " +
                    "nachname VARCHAR(45), " +
                    "kalenderfarbe VARCHAR(45) " +
                    "PRIMARY KEY (MitarbeiterID) " +
                    ")";
            con.createStatement().executeUpdate(tableMitarbeiter);
        } catch (SQLException e){
            e.getErrorCode();
        }
        try {
            String tableKunden="" +
                    "CREATE TABLE IF NOT EXISTS kunde( " +
                    "KundenID int NOT NULL AUTO_INCREMENT, " +
                    "vorname VARCHAR(45), " +
                    "nachname VARCHAR(45), " +
                    "telefonnummer VARCHAR (45) " +
                    "PRIMARY KEY (KundenID) " +
                    ")";
            con.createStatement().executeUpdate(tableKunden);
        } catch (SQLException e){
            e.getErrorCode();
        }
        try {
            String tableTermine="" +
                    "CREATE TABLE IF NOT EXISTS termin( " +
                    "TerminID int NOT NULL AUTO_INCREMENT, " +
                    "MitarbeiterMacherID int, " +
                    "KundenID, " +
                    "Terminstart DATETIME, " +
                    "Terminende DATETIME, " +
                    "Terminart VARCHAR (200), " +
                    "Beschreibung TEXT, " +
                    "MitarbeiterSchreiberID int, " +
                    "PRIMARY KEY (TerminID) " +
                    "FOREIGN KEY (MitarbeiterMacherID) REFERENCES mitarbeiter(MitarbeiterID) " +
                    "FOREIGN KEY (MitarbeiterSchreiberID) REFERENCES mitarbeiter(MitarbeiterID) " +
                    ")";
            con.createStatement().executeUpdate(tableTermine);
        } catch (SQLException e){
            e.getErrorCode();
        }
        try {
            String tableArbeitszeiten="" +
                    "CREATE TABLE IF NOT EXISTS arbeitszeiten( " +
                    "ArbeitszeitID int NOT NULL AUTO_INCREMENT, " +
                    "MitarbeiterID int, " +
                    "Schichtbeginn DATETIME, " +
                    "Schichtende DATETIME, " +
                    "PRIMARY KEY (ArbeitszeitID), " +
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
        List<Mitarbeiter> m= new ArrayList<Mitarbeiter>();
        if(con != null){
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();

                // Tabelle anzeigen
                String sql =
                        "SELECT * FROM mitarbeiter";
                ResultSet result = query.executeQuery(sql);

                // Ergebnisstabelle durchforsten
                while (result.next()) {
                    Mitarbeiter temp=new Mitarbeiter();
                    temp.setName(result.getString("vorname"));
                    temp.setFarbe(result.getString("kalenderfarbe"));
                    temp.setMitarbeiterID(result.getInt("MitarbeiterID"));
                    m.add(temp);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return m;
    }
    public static void newMitarbeiter(String vorname, String nachname, String farbe){
        con = getInstance();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO mitarbeiter(vorname,nachname,kalenderfarbe) VALUES('"+vorname+"','"+nachname+"','"+farbe+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

    }
    public static List<Kunde> kunden(){

        con = getInstance();
        List<Kunde> kunden= new ArrayList<Kunde>();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql =
                        "SELECT * FROM Kunde";
                ResultSet ergebnis = query.executeQuery(sql);
                String title;

                while (ergebnis.next()) {
                    Kunde temp = new Kunde();
                    temp.setKundeID(ergebnis.getInt("KundenID"));
                    temp.setVorname(ergebnis.getString("vorname"));
                    temp.setNachname(ergebnis.getString("nachname"));
                    temp.setTelefonnummer(ergebnis.getString("telefonnummer"));
                    kunden.add(temp);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return kunden;
    }
    public static void neuerTermin (int MitarbeiterID, int KundenID, String Beschreibung, String Terminart, Date start, Date end , int eintrager){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO termin(MitarbeiterMacherID, KundenID, Beschreibung, Terminart, Terminstart, Terminende, MitarbeiterSchreiberID) VALUES(" +
                                "'"+MitarbeiterID+"','"+KundenID+"','"+Beschreibung+"','"+Terminart+"','"+start+"','"+end+"','"+eintrager+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }


    }
    public static List<FullCalendarEventBean> getAllEvents(int mitarbeiter){//hier sollen die Events geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();

        List<FullCalendarEventBean> fb= new ArrayList<FullCalendarEventBean>();

        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT termin.Terminstart, termin.Terminende, M1.kalenderfarbe, termin.terminart, kunde.vorname, kunde.nachname, kunde.telefonnummer, kunde.Beschreibung, M2.vorname AS eintrager " +
                                "FROM termin " +
                                "INNER JOIN mitarbeiter M1 ON termin.MitarbeiterMacherID = mitarbeiter.MitarbeiterID " +
                                "INNER JOIN mitarbeiter M2 ON termin.MitarbeiterSchreiberID = mitarbeiter.MitarbeiterID " +
                                "INNER JOIN kunde ON termin.KundenID = kunde.KundenID " +
                                "WHERE termin.MitarbeiterMacherID = '"+mitarbeiter+"'";
                ResultSet result = query.executeQuery(sql);
                String title;

                while (result.next()) {
                    FullCalendarEventBean temp = new FullCalendarEventBean();
                    temp.setStart(result.getDate("Terminstart"));
                    temp.setEnd(result.getDate("Terminende"));
                    temp.setColor(result.getString("kalenderfarbe"));
                    String terminart= result.getString("Terminart");
                    String vorname=result.getString("vorname");
                    String nachname =result.getString("nachname");
                    String tele=result.getString("telefonnummer");
                    String beschreibung = result.getString("Beschreibung");
                    String eintrager=result.getString("eintrager");
                    title= terminart+" ; "+vorname+" ; "+nachname+" ; "+tele+" ; "+beschreibung+" ; "+eintrager;
                    temp.setTitle(title);
                    fb.add(temp);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return fb;
    }
    public static List<FullCalendarEventBean> getAllArbeitszeiten(int mitarbeiter){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<FullCalendarEventBean> fb= new ArrayList<FullCalendarEventBean>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM arbeitszeiten " +
                                "INNER JOIN mitarbeiter ON arbeitszeiten.MitarbeiterID = mitarbeiter.MitarbeiterID " +
                                "WHERE arbeitszeiten.MitarbeiterID = '"+mitarbeiter+"'";
                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    FullCalendarEventBean temp = new FullCalendarEventBean();
                    temp.setStart(result.getDate("Schichtbeginn"));
                    temp.setEnd(result.getDate("Schichtende"));
                    temp.setColor(result.getString("kalenderfarbe"));
                    temp.setTitle("Arbeiteszeit");
                    temp.setRendering("background");
                    fb.add(temp);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return fb;
    }
    public static void newArbeitszeit(int mitarbeiterID, Date schichtbeginn, Date schichtende){
        con = getInstance();
        //TODO Anwesenheitstabelle befüllen
    }
    public static void neuerKunde (String vorname, String nachname,String telefonnummer ){
        con = getInstance();
        //TODO InsertSdtatement
    }
    public static int getMaxKundenID(){
        //TODO: mit Operator Max die Maxnummer der tabelle heruasfinden
        con = getInstance();
        int max=0;
        return max;
    }

}
