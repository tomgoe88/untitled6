
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.sql.DriverManager;
import java.util.Locale;


/**
 *
 * @author Jutom
 */
@ManagedBean
@SessionScoped
public class SQLHelper{
    private static Connection con = null;
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost/demodb";
    String user = "root";
    String passwd = "times-88";

    private static String dbHost = "localhost"; // Hostname
    private static String dbPort = "3306";      // Port -- Standard: 3306
    private String dbName = "Terminplaner";   // Datenbankname
    private static String dbUser = "root";     // Datenbankuser
    private static String dbPass = "times-88";      // Datenbankpasswort
    private static String sql = "CREATE DATABASE IF NOT EXISTS DEMODB";
    private static SQLHelper aktuell = new SQLHelper();

    private SQLHelper(){
        try {
            //Class.forName("com.mysql.jdbc.Driver"); // Datenbanktreiber für JDBC Schnittstellen laden.
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, passwd);

            // Verbindung zur JDBC-Datenbank herstellen.
           // con = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+ dbPort+"/"+"?user="+dbUser+"&"+"?password="+dbPass);//+"&"+"password="+dbPass
            PreparedStatement stmt = con.prepareStatement(sql);
            createTables(con);
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


    public static void createTables(Connection con){
       // con= getInstance();
        try {
            String tableMitarbeiter="" +
                    "CREATE TABLE IF NOT EXISTS mitarbeiter( " +
                    "MitarbeiterID int NOT NULL AUTO_INCREMENT, " +
                    "vorname VARCHAR(45), " +
                    "nachname VARCHAR(45), " +
                    "kalenderfarbe VARCHAR(45), " +
                    "PRIMARY KEY (MitarbeiterID)" +
                    ")";
            con.createStatement().executeUpdate(tableMitarbeiter);
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableAnmeldung="" +
                    "CREATE TABLE IF NOT EXISTS anmeldung( " +
                    "AnmeldeID int NOT NULL AUTO_INCREMENT, " +
                    "kennwort VARCHAR(45), " +
                    "admin VARCHAR(45), " +
                    "MitarbeiterID int, "+
                    "PRIMARY KEY (AnmeldeID), " +
                    "FOREIGN KEY (MitarbeiterID) REFERENCES mitarbeiter(MitarbeiterID) ON DELETE CASCADE ON UPDATE CASCADE " +
                    ")";
            con.createStatement().executeUpdate(tableAnmeldung);
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableKunden="" +
                    "CREATE TABLE IF NOT EXISTS kunde( " +
                    "KundenID int NOT NULL AUTO_INCREMENT, " +
                    "vorname VARCHAR(45), " +
                    "nachname VARCHAR(45), " +
                    "telefonnummer VARCHAR (45), " +
                    "PRIMARY KEY (KundenID)" +
                    ")";
            con.createStatement().executeUpdate(tableKunden);
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableTermine="" +
                    "CREATE TABLE IF NOT EXISTS termin( " +
                    "TerminID int NOT NULL AUTO_INCREMENT, " +
                    "MitarbeiterMacherID int, " +
                    "KundenID INT , " +
                    "Terminstart VARCHAR (200), " +
                    "Terminende VARCHAR (200), " +
                    "Terminart VARCHAR (200), " +
                    "Beschreibung TEXT, " +
                    "MitarbeiterSchreiberID int, " +
                    "PRIMARY KEY (TerminID), " +
                    "FOREIGN KEY (MitarbeiterMacherID) REFERENCES mitarbeiter(MitarbeiterID), " +
                    "FOREIGN KEY (KundenID) REFERENCES kunde(KundenID) ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "FOREIGN KEY (MitarbeiterSchreiberID) REFERENCES mitarbeiter(MitarbeiterID) ON DELETE CASCADE ON UPDATE CASCADE  " +
                    ")";
            con.createStatement().executeUpdate(tableTermine);
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableArbeitszeiten="" +
                    "CREATE TABLE IF NOT EXISTS arbeitszeiten( " +
                    "ArbeitszeitID int NOT NULL AUTO_INCREMENT, " +
                    "MitarbeiterID int, " +
                    "Schichtbeginn VARCHAR (200), " +
                    "Schichtende VARCHAR (200), " +
                    "PRIMARY KEY (ArbeitszeitID), " +
                    "FOREIGN KEY (MitarbeiterID) REFERENCES mitarbeiter(MitarbeiterID) ON DELETE CASCADE ON UPDATE CASCADE " +
                    ")";
            con.createStatement().executeUpdate(tableArbeitszeiten);
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableUrlaubszeiten="" +
                    "CREATE TABLE IF NOT EXISTS urlaubszeit( " +
                    "UrlaubszeitID int NOT NULL AUTO_INCREMENT, " +
                    "MitarbeiterID int, " +
                    "Urlaubszeitbeginn VARCHAR (200), " +
                    "Urlaubszeitende VARCHAR (200), " +
                    "PRIMARY KEY (UrlaubszeitID), " +
                    "FOREIGN KEY (MitarbeiterID) REFERENCES mitarbeiter(MitarbeiterID) ON DELETE CASCADE ON UPDATE CASCADE " +
                    ")";
            con.createStatement().executeUpdate(tableUrlaubszeiten);
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableSperrzeiten="" +
                    "CREATE TABLE IF NOT EXISTS sperrzeiten( " +
                    "SperrID int NOT NULL AUTO_INCREMENT, " +
                    "MitarbeiterGesperrtID int, " +
                    "Terminstart VARCHAR (200), " +
                    "Terminende VARCHAR (200), " +
                    "MitarbeiterSperrerID int, " +
                    "TerminID int, " +
                    "PRIMARY KEY (SperrID), " +
                    "FOREIGN KEY (MitarbeiterGesperrtID) REFERENCES mitarbeiter(MitarbeiterID) ON DELETE CASCADE ON UPDATE CASCADE , " +
                    "FOREIGN KEY (TerminID) REFERENCES termin(TerminID) ON DELETE CASCADE ON UPDATE CASCADE , " +
                    "FOREIGN KEY (MitarbeiterSperrerID) REFERENCES mitarbeiter(MitarbeiterID) ON DELETE CASCADE ON UPDATE CASCADE " +
                    ")";
            con.createStatement().executeUpdate(tableSperrzeiten);
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableAufgaben="" +
                    "CREATE TABLE IF NOT EXISTS aufgaben( " +
                    "AufgabenID int NOT NULL AUTO_INCREMENT, " +
                    "Beschreibung TEXT, " +
                    "Aufgabedatum VARCHAR (200), " +
                    "Erledigt VARCHAR (45), " +
                    "PRIMARY KEY (AufgabenID) " +
                    ")";
            con.createStatement().executeUpdate(tableAufgaben);
        } catch (SQLException e){
            System.out.println("Aufgabem SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableAufgabenErledigt="" +
                    "CREATE TABLE IF NOT EXISTS aufgabenErledigt( " +
                    "AufgabenErledigtID int NOT NULL AUTO_INCREMENT, " +
                    "AufgabenID int, " +
                    "MitarbeiterID int, " +
                    "PRIMARY KEY (AufgabenErledigtID), " +
                    "FOREIGN KEY (AufgabenID) REFERENCES aufgaben(AufgabenID), " +
                    "FOREIGN KEY (MitarbeiterID) REFERENCES mitarbeiter(MitarbeiterID) ON DELETE CASCADE ON UPDATE CASCADE " +
                    ")";
            con.createStatement().executeUpdate(tableAufgabenErledigt);
        } catch (SQLException e){
            System.out.println("Erledigt SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableMemo="" +
                    "CREATE TABLE IF NOT EXISTS memo( " +
                    "MemoID int NOT NULL AUTO_INCREMENT, " +
                    "Beschreibung TEXT, " +
                    "PRIMARY KEY (MemoID) " +
                    ")";
            con.createStatement().executeUpdate(tableMemo);
        } catch (SQLException e){
            System.out.println("Memo SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

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
                    temp.setName(result.getString("vorname")+" "+result.getString("nachname"));
                    temp.setFarbe(result.getString("kalenderfarbe"));
                    temp.setMitarbeiterID(result.getInt("MitarbeiterID"));
                    m.add(temp);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(m.size()==0){
            Mitarbeiter neu= new Mitarbeiter();
            neu.setMitarbeiterID(0);
            neu.setName("-");
            neu.setFarbe("red");
            m.add(neu);
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
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
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
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
        return kunden;
    }
    public static void neuerTermin (int MitarbeiterID, int KundenID, String Beschreibung, String Terminart, String start, String end , int eintrager){
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
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
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
                        "SELECT termin.TerminID, termin.Terminstart, termin.Terminende, M1.kalenderfarbe, termin.terminart, kunde.vorname, kunde.nachname, kunde.telefonnummer, termin.Beschreibung, M2.vorname AS eintrager " +
                                "FROM termin " +
                                "INNER JOIN mitarbeiter M1 ON termin.MitarbeiterMacherID = M1.MitarbeiterID " +
                                "INNER JOIN mitarbeiter M2 ON termin.MitarbeiterSchreiberID = M2.MitarbeiterID " +
                                "INNER JOIN kunde ON termin.KundenID = kunde.KundenID " +
                                "WHERE termin.MitarbeiterMacherID = '"+mitarbeiter+"'";
                ResultSet result = query.executeQuery(sql);
                String title;

                while (result.next()) {
                    FullCalendarEventBean temp = new FullCalendarEventBean();
                    String terminstart=result.getString("Terminstart");
                    String terminende=result.getString("Terminende");
                    DateFormat dateFormat=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    //DateFormat dateFormat=DateFormat.getDateTimeInstance();
                    Date start=null;
                    Date end= null;
                    try {
                        start= dateFormat.parse(terminstart);
                        end=dateFormat.parse(terminende);
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                    temp.setStart(start);
                    temp.setEnd(end);
                    temp.setColor(result.getString("kalenderfarbe"));
                    String terminart= result.getString("Terminart");
                    String vorname=result.getString("vorname");
                    String nachname =result.getString("nachname");
                    String tele=result.getString("telefonnummer");
                    String beschreibung = result.getString("Beschreibung");
                    String eintrager=result.getString("eintrager");
                    int terminID=result.getInt("TerminID");
                    title= terminart+" ; "+vorname+" ; "+nachname+" ; "+tele+" ; "+beschreibung+" ; "+eintrager+" ; "+terminID;
                    temp.setTitle(title);
                    fb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("SET TERMIN //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return fb;
    }
    public static ScheduleModel rimeEvents(int mitarbeiter){//hier sollen die Events geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();

        ScheduleModel  fb= new DefaultScheduleModel();


        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT termin.TerminID, termin.Terminstart, termin.Terminende, M1.kalenderfarbe, termin.terminart, kunde.vorname, kunde.nachname, kunde.telefonnummer, termin.Beschreibung, M2.vorname AS eintrager " +
                                "FROM termin " +
                                "INNER JOIN mitarbeiter M1 ON termin.MitarbeiterMacherID = M1.MitarbeiterID " +
                                "INNER JOIN mitarbeiter M2 ON termin.MitarbeiterSchreiberID = M2.MitarbeiterID " +
                                "INNER JOIN kunde ON termin.KundenID = kunde.KundenID " +
                                "WHERE termin.MitarbeiterMacherID = '"+mitarbeiter+"'";
                ResultSet result = query.executeQuery(sql);
                String title;

                while (result.next()) {
                    ScheduleEvent temp ;
                    String terminstart=result.getString("Terminstart");
                    String terminende=result.getString("Terminende");
                    DateFormat dateFormat=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    //DateFormat dateFormat=DateFormat.getDateTimeInstance();
                    Date start=null;
                    Date end= null;
                    try {
                        start= dateFormat.parse(terminstart);
                        end=dateFormat.parse(terminende);
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                   // temp.getStartDate(start);
                    //temp.(end);
                    //temp.setColor(result.getString("kalenderfarbe"));
                    String terminart= result.getString("Terminart");
                    String vorname=result.getString("vorname");
                    String nachname =result.getString("nachname");
                    String tele=result.getString("telefonnummer");
                    String beschreibung = result.getString("Beschreibung");
                    String eintrager=result.getString("eintrager");
                    int terminID=result.getInt("TerminID");
                    title= terminart+" ; "+vorname+" ; "+nachname+" ; "+tele+" ; "+beschreibung+" ; "+eintrager+" ; "+terminID;
                  //  temp.setTitle(title);
                  //  fb.add(temp);
                temp= new DefaultScheduleEvent(title,start,end);
                }
            } catch (SQLException e) {
                System.out.println("SET TERMIN //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
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

                    String schichtbeginn=result.getString("Schichtbeginn");
                    String schichtende=result.getString("Schichtende");
                    DateFormat dateFormat=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    //DateFormat dateFormat=DateFormat.getDateTimeInstance();
                    Date start=null;
                    Date end= null;
                    try {
                         start= dateFormat.parse(schichtbeginn);
                        end= dateFormat.parse(schichtende);
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }

                    temp.setStart(start);
                    temp.setEnd(end);
                    temp.setColor(result.getString("kalenderfarbe"));
                    temp.setTitle("Arbeiteszeit");
                    temp.setRendering("background");
                    fb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("SET ARBEITSZEITEN //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return fb;
    }
    public static void newArbeitszeit(int mitarbeiterID, String schichtbeginn, String schichtende){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO arbeitszeiten(MitarbeiterID, Schichtbeginn, Schichtende) VALUES(" +
                                "'"+mitarbeiterID+"','"+schichtbeginn+"','"+schichtende+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static void neuerKunde (String vorname, String nachname,String telefonnummer ){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO kunde(vorname, nachname, telefonnummer) VALUES(" +
                                "'"+vorname+"','"+nachname+"','"+telefonnummer+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static int getMaxKundenID(){
        //TODO: mit Operator Max die Maxnummer der tabelle heruasfinden
        con = getInstance();
        int max=0;
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT MAX(KundenID) AS maxID FROM kunde";
                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    max = result.getInt("maxID");
                }
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
        return max;
    }
    public static int getMaxTerminID(){
        //TODO: mit Operator Max die Maxnummer der tabelle heruasfinden
        con = getInstance();
        int max=0;
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT MAX(TerminID) AS maxID FROM termin";
                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    max = result.getInt("maxID");
                }
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
        return max;
    }
    public static List<FullCalendarEventBean> getSperrZeiten(int mitarbeiter){//hier sollen die Events geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();

        List<FullCalendarEventBean> fb= new ArrayList<FullCalendarEventBean>();

        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT sperrzeiten.Terminstart, sperrzeiten.Terminende, M2.kalenderfarbe, M2.vorname AS sperrer " +
                                "FROM sperrzeiten " +
                                "INNER JOIN mitarbeiter M1 ON sperrzeiten.MitarbeiterGesperrtID = M1.MitarbeiterID " +
                                "INNER JOIN mitarbeiter M2 ON sperrzeiten.MitarbeiterSperrerID = M2.MitarbeiterID " +
                                "WHERE sperrzeiten.MitarbeiterGesperrtID = '"+mitarbeiter+"'";
                ResultSet result = query.executeQuery(sql);
                String title;

                while (result.next()) {
                    FullCalendarEventBean temp = new FullCalendarEventBean();
                    String terminstart=result.getString("Terminstart");
                    String terminende=result.getString("Terminende");
                    DateFormat dateFormat=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    //DateFormat dateFormat=DateFormat.getDateTimeInstance();
                    Date start=null;
                    Date end= null;
                    try {
                        start= dateFormat.parse(terminstart);
                        end=dateFormat.parse(terminende);
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                    temp.setStart(start);
                    temp.setEnd(end);
                    temp.setColor(result.getString("kalenderfarbe"));
                    String vornameSperrer= result.getString("sperrer");
                    title= "Hier hat "+vornameSperrer+" bereits einen Termin";
                    temp.setTitle(title);
                    fb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("SET Sperrzeit //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return fb;
    }
    public static void newSperrzeit(int mitarbeiterGesperrtID, String sperrbeginn, String sperrende, int mitarbeiterSperrerID, int terminid){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO sperrzeiten(MitarbeiterGesperrtID, Terminstart, Terminende, MitarbeiterSperrerID, TerminID) VALUES(" +
                                "'"+mitarbeiterGesperrtID+"','"+sperrbeginn+"','"+sperrende+"','"+mitarbeiterSperrerID+"','"+terminid+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static void deleteTermin(int terminID){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "DELETE FROM termin WHERE TerminID='"+terminID+"'";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static void deleteMitarbeiter(int mitarbeiterID){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "DELETE FROM mitarbeiter WHERE MitarbeiterID='"+mitarbeiterID+"'";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static void newAufgabe(String beschreibung, String erledigungsDatum){
        con = getInstance();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO aufgaben(Beschreibung, Aufgabedatum, Erledigt) VALUES('"+beschreibung+"','"+erledigungsDatum+"','false')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

    }
    public static void newAufgabeErledigt(int aufgabenID, int mitarbeiterID){
        con = getInstance();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO aufgabenErledigt(AufgabenID, MitarbeiterID) VALUES('"+aufgabenID+"','"+mitarbeiterID+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

    }
    public static void newMemo(String beschreibung){
        con = getInstance();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO memo(Beschreibung) VALUES('"+beschreibung+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

    }
    public static List<Aufgabe> getAufgaben(String aufgabenDatum){
        con= getInstance();
        List<Aufgabe> aufgaben = new ArrayList<Aufgabe>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM aufgaben WHERE Aufgabedatum ='"+aufgabenDatum+"' AND Erledigt = 'false'";

                ResultSet result = query.executeQuery(sql);
                String title;

                while (result.next()) {
                    Aufgabe aufgabe= new Aufgabe();
                    String erledigt=result.getString("Aufgabedatum");
                    DateFormat dateFormat=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    //DateFormat dateFormat=DateFormat.getDateTimeInstance();
                    Date erledigung=null;
                    Boolean isErledigt = Boolean.parseBoolean(result.getString("Erledigt"));
                    try {
                        erledigung= dateFormat.parse(erledigt);

                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                    aufgabe.setErledig(isErledigt);
                    aufgabe.setAufgabeID(result.getInt("AufgabenID"));
                    aufgabe.setBeschreibung(result.getString("Beschreibung"));
                    aufgabe.setAufgabendatum(erledigung);
                    aufgaben.add(aufgabe);

                }
            } catch (SQLException e) {
                System.out.println("SET Sperrzeit //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
        return aufgaben;
    }
    public static List<Memo> getMemo(){
        List<Memo> memos = new ArrayList<Memo>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM memo";
                ResultSet result = query.executeQuery(sql);
                String title;

                while (result.next()) {
                    Memo memo= new Memo();
                    memo.setMemoID(result.getInt("MemoID"));
                    memo.setBeschreibung(result.getString("Beschreibung"));
                    memos.add(memo);

                }
            } catch (SQLException e) {
                System.out.println("SET Sperrzeit //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
        return memos;
    }

    public static void updateAufgabe(int aufgabenID){
        con = getInstance();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "Update aufgaben SET Erledigt='true' WHERE AufgabenID='"+aufgabenID+"'";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static List<FullCalendarEventBean> getUrlaubszeiten(int mitarbeiter){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<FullCalendarEventBean> fb= new ArrayList<FullCalendarEventBean>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM urlaubszeit " +
                                "INNER JOIN mitarbeiter ON urlaubszeit.MitarbeiterID = mitarbeiter.MitarbeiterID " +
                                "WHERE urlaubszeit.MitarbeiterID = '"+mitarbeiter+"'";
                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    FullCalendarEventBean temp = new FullCalendarEventBean();

                    String schichtbeginn=result.getString("Urlaubszeitbeginn");
                    String schichtende=result.getString("Urlaubszeitende");
                    DateFormat dateFormat=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    //DateFormat dateFormat=DateFormat.getDateTimeInstance();
                    Date start=null;
                    Date end= null;
                    try {
                        start= dateFormat.parse(schichtbeginn);
                        end= dateFormat.parse(schichtende);
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }

                    temp.setStart(start);
                    temp.setEnd(end);
                    temp.setColor(result.getString("kalenderfarbe"));
                    temp.setTitle("Urlaub");

                    fb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("SET ARBEITSZEITEN //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return fb;
    }
    public static void newUrlaubszeit(int mitarbeiterID, String urlaubsbeginn, String urlaubsende){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO urlaubszeit(MitarbeiterID, Urlaubszeitbeginn, Urlaubszeitende) VALUES(" +
                                "'"+mitarbeiterID+"','"+urlaubsbeginn+"','"+urlaubsende+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static String getPassword(int mitarbeiterID){
        con = getInstance();
        String password= null;
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM anmeldung " +
                                "WHERE anmeldung.MitarbeiterID = '"+mitarbeiterID+"'";
                ResultSet result = query.executeQuery(sql);


                while (result.next()) {

                   password=result.getString("kennwort");


                }
            } catch (SQLException e) {
                System.out.println("Get Passwort //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return password;
    }
    public static Boolean admin(int mitarbeiterID){
        con = getInstance();
        String password= null;
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM anmeldung " +
                                "WHERE anmeldung.MitarbeiterID = '"+mitarbeiterID+"'";
                ResultSet result = query.executeQuery(sql);


                while (result.next()) {

                    password=result.getString("admin");


                }
            } catch (SQLException e) {
                System.out.println("Get Passwort //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return Boolean.parseBoolean(password);
    }
    public static void newPassword(int mitarbeiterID, String password, String admin){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO anmeldung(MitarbeiterID, kennwort, admin) VALUES(" +
                                "'"+mitarbeiterID+"','"+password+"','"+admin+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static int getMaxMitarbeiterID(){
        //TODO: mit Operator Max die Maxnummer der tabelle heruasfinden
        con = getInstance();
        int max=0;
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT MAX(MitarbeiterID) AS maxID FROM mitarbeiter";
                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    max = result.getInt("maxID");
                }
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
        return max;
    }
    public static void updatePassword(int mitarbeiterID, String password){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "Update anmeldung SET kennwort='"+password+"' WHERE MitarbeiterID='"+mitarbeiterID+"'";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static List<Urlaub> getUrlaubsList(int mitarbeiter){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<Urlaub> fb= new ArrayList<Urlaub>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM urlaubszeit " +
                                "INNER JOIN mitarbeiter ON urlaubszeit.MitarbeiterID = mitarbeiter.MitarbeiterID " +
                                "WHERE urlaubszeit.MitarbeiterID = '"+mitarbeiter+"'";
                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    Urlaub temp = new Urlaub();

                    String schichtbeginn=result.getString("Urlaubszeitbeginn");
                    String schichtende=result.getString("Urlaubszeitende");
                    DateFormat dateFormat=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    //DateFormat dateFormat=DateFormat.getDateTimeInstance();
                    Date start=null;
                    Date end= null;
                    try {
                        start= dateFormat.parse(schichtbeginn);
                        end= dateFormat.parse(schichtende);
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                    temp.setUrlaubsID(result.getInt("UrlaubszeitID"));
                    temp.setUrlaubBeginn(start);
                    temp.setUrlaubEnde(end);
                   ;

                    fb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("SET ARBEITSZEITEN //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return fb;
    }
    public static void deleteUrlaub(int urlaubsID){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "DELETE FROM urlaubszeit WHERE UrlaubszeitID='"+urlaubsID+"'";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }

}
