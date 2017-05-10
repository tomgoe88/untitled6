
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
    String url = "jdbc:mysql://localhost/TerminplanerP";
    String user = "root";
    String passwd = "times-88";

 //   private static String dbHost = "localhost"; // Hostname
  //  private static String dbPort = "3306";      // Port -- Standard: 3306
    private String dbName = "Terminplaner";   // Datenbankname
   //private static String dbUser = "root";     // Datenbankuser
    //private static String dbPass = "times-88";      // Datenbankpasswort
    private static String sql = "CREATE DATABASE IF NOT EXISTS TerminplanerP";
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
    public static void initAnmeldung(){
        if(getMaxMitarbeiterID()==0){
            newMitarbeiter("Admin","Admin","red", "true");
            newPassword(1,"admin", "true");
        }
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
                    "kalendereintrag VARCHAR(45), " +
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
                    "email VARCHAR (45), " +
                    "Strasse VARCHAR (200), " +
                    "Plz VARCHAR (6), " +
                    "Ort VARCHAR (200), " +
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
                    "FOREIGN KEY (KundenID) REFERENCES kunde(KundenID), " +
                    "FOREIGN KEY (MitarbeiterSchreiberID) REFERENCES mitarbeiter(MitarbeiterID)" +
                    ")";
            con.createStatement().executeUpdate(tableTermine);
        } catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableFreieTermine="" +
                    "CREATE TABLE IF NOT EXISTS freieTermine( " +
                    "TerminID int NOT NULL AUTO_INCREMENT, " +
                    "MitarbeiterMacherID int, " +
                    "Terminstart VARCHAR (200), " +
                    "Terminende VARCHAR (200), " +
                    "Beschreibung TEXT, " +
                    "MitarbeiterSchreiberID int, " +
                    "PRIMARY KEY (TerminID), " +
                    "FOREIGN KEY (MitarbeiterMacherID) REFERENCES mitarbeiter(MitarbeiterID), " +
                    "FOREIGN KEY (MitarbeiterSchreiberID) REFERENCES mitarbeiter(MitarbeiterID)" +
                    ")";
            con.createStatement().executeUpdate(tableFreieTermine);
        } catch (SQLException e){
            System.out.println("FreieTermine SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableErledigteTermine="" +
                    "CREATE TABLE IF NOT EXISTS terminerledigt( " +
                    "ErledigtID int NOT NULL AUTO_INCREMENT, " +
                    "Ergebnis VARCHAR(200), " +
                    "Hinweis TEXT, " +
                    "TerminID int, " +
                    "PRIMARY KEY (ErledigtID), " +
                    "FOREIGN KEY (TerminID) REFERENCES termin(TerminID) " +
                    ")";
            con.createStatement().executeUpdate(tableErledigteTermine);
        } catch (SQLException e){
            System.out.println("Terminerledigung //SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableKursbezeichnungen="" +
                    "CREATE TABLE IF NOT EXISTS kursbezeichnungen( " +
                    "BezeichnungsID int NOT NULL AUTO_INCREMENT, " +
                    "Bezeichnung VARCHAR(200), " +
                    "PRIMARY KEY (BezeichnungsID)" +
                    ")";
            con.createStatement().executeUpdate(tableKursbezeichnungen);
        } catch (SQLException e){
            System.out.println("Kursbezeichnung //SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableKurs="" +
                    "CREATE TABLE IF NOT EXISTS kurse( " +
                    "KursID int NOT NULL AUTO_INCREMENT, " +
                    "MitarbeiterID int, " +
                    "Kursbeginn VARCHAR (200), " +
                    "Kursende VARCHAR (200), " +
                    "Kursbezeichnung VARCHAR (200), " +
                    "PRIMARY KEY (KursID), " +
                    "FOREIGN KEY (MitarbeiterID) REFERENCES mitarbeiter(MitarbeiterID)" +
                    ")";
            con.createStatement().executeUpdate(tableKurs);
        } catch (SQLException e){
            System.out.println("Kursbezeichnung //SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableKursteilnehmer="" +
                    "CREATE TABLE IF NOT EXISTS kursteilnehmer( " +
                    "TeilnehmerID int NOT NULL AUTO_INCREMENT, " +
                    "KursID int, " +
                    "KundenID int, " +
                    "PRIMARY KEY (TeilnehmerID), " +
                    "FOREIGN KEY (KursID) REFERENCES kurse(KursID), " +
                    "FOREIGN KEY (KundenID) REFERENCES kunde(KundenID)" +
                    ")";
            con.createStatement().executeUpdate(tableKursteilnehmer);
        } catch (SQLException e){
            System.out.println("Kursteilnehmer //SQLException: " + e.getMessage());
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
                    "Schichtart VARCHAR (200), " +
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
            String tableKrankheitstage="" +
                    "CREATE TABLE IF NOT EXISTS krankheitstage( " +
                    "KrankID int NOT NULL AUTO_INCREMENT, " +
                    "MitarbeiterID int, " +
                    "Krankbeginn VARCHAR (200), " +
                    "Krankende VARCHAR (200), " +
                    "PRIMARY KEY (KrankID), " +
                    "FOREIGN KEY (MitarbeiterID) REFERENCES mitarbeiter(MitarbeiterID) ON DELETE CASCADE ON UPDATE CASCADE " +
                    ")";
            con.createStatement().executeUpdate(tableKrankheitstage);
        } catch (SQLException e){
            System.out.println("Create Krank//SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableUniTage="" +
                    "CREATE TABLE IF NOT EXISTS unitage( " +
                    "UniID int NOT NULL AUTO_INCREMENT, " +
                    "MitarbeiterID int, " +
                    "Unibeginn VARCHAR (200), " +
                    "Uniende VARCHAR (200), " +
                    "PRIMARY KEY (UniID), " +
                    "FOREIGN KEY (MitarbeiterID) REFERENCES mitarbeiter(MitarbeiterID) ON DELETE CASCADE ON UPDATE CASCADE " +
                    ")";
            con.createStatement().executeUpdate(tableUniTage);
        } catch (SQLException e){
            System.out.println("Create Uni//SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try {
            String tableAusgleichtage="" +
                    "CREATE TABLE IF NOT EXISTS ausgleichtage( " +
                    "UniID int NOT NULL AUTO_INCREMENT, " +
                    "MitarbeiterID int, " +
                    "Unibeginn VARCHAR (200), " +
                    "Uniende VARCHAR (200), " +
                    "PRIMARY KEY (UniID), " +
                    "FOREIGN KEY (MitarbeiterID) REFERENCES mitarbeiter(MitarbeiterID) ON DELETE CASCADE ON UPDATE CASCADE " +
                    ")";
            con.createStatement().executeUpdate(tableAusgleichtage);
        } catch (SQLException e){
            System.out.println("Create Ausgleichtage//SQLException: " + e.getMessage());
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
            String tableMemo="" +
                    "CREATE TABLE IF NOT EXISTS memo( " +
                    "MemoID int NOT NULL AUTO_INCREMENT, " +
                    "Beschreibung TEXT, " +
                    "EintragDatum VARCHAR (200), " +
                    "MemoVon INT , " +
                    "PRIMARY KEY (MemoID), " +
                    "FOREIGN KEY (MemoVon) REFERENCES mitarbeiter(MitarbeiterID)" +
                    ")";
            con.createStatement().executeUpdate(tableMemo);
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
        SQLHelper.initAnmeldung();


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
    public static void newMitarbeiter(String vorname, String nachname, String farbe, String kalenderbool){
        con = getInstance();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO mitarbeiter(vorname,nachname,kalenderfarbe,kalendereintrag) VALUES('"+vorname+"','"+nachname+"','"+farbe+"','"+kalenderbool+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

    }

    public static void updateMitarbeiter(String farbe, String kalenderbool, int mitarbeiterID){
        con = getInstance();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "UPDATE mitarbeiter SET kalenderfarbe='"+farbe+"',kalendereintrag='"+kalenderbool+"' " +
                                "WHERE MitarbeiterID='"+mitarbeiterID+"'";

                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

    }
    public static List<Kunde> teilnehmerListe(int kursID){

        con = getInstance();
        List<Kunde> kunden= new ArrayList<Kunde>();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql =
                        "SELECT * FROM kursteilnehmer " +
                                "INNER JOIN kunde ON kursteilnehmer.KundenID=kunde.KundenID " +
                                "INNER JOIN kurse ON kursteilnehmer.KursID=kurse.KursID " +
                                "WHERE kurse.KursID='"+kursID+"'";
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
    public static List<String> getKursbezeichnungen(){

        con = getInstance();
        List<String> kursb= new ArrayList<String>();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql =
                        "SELECT * FROM kursbezeichnungen";
                ResultSet ergebnis = query.executeQuery(sql);
                String title;

                while (ergebnis.next()) {
                    String temp = ergebnis.getString("Bezeichnung");
                    kursb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("Get Kursbezeichnung SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
        return kursb;
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
    public static void neuerFreierTermin (int MitarbeiterID, String Beschreibung, String start, String end , int eintrager){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO freieTermine(MitarbeiterMacherID, Beschreibung, Terminstart, Terminende, MitarbeiterSchreiberID) VALUES(" +
                                "'"+MitarbeiterID+"','"+Beschreibung+"','"+start+"','"+end+"','"+eintrager+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("Freier Termin eintragen SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }


    }
    public static void neuerTeilnehmer (int kursID, int kundeiD){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO kursteilnehmer(KursID, KundenID) VALUES("+
                                "'"+kursID+"','"+kundeiD+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("NeuerTeilnehmer//SQLException: " + e.getMessage());
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
                        "SELECT termin.TerminID, termin.Terminstart, termin.Terminende, termin.MitarbeiterMacherID AS id, M1.kalenderfarbe, termin.terminart, kunde.vorname, kunde.nachname, kunde.telefonnummer, termin.Beschreibung, M2.vorname AS eintrager " +
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
                    temp.setResourceId(result.getInt("id")+"");
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
                    temp.setResourceId(result.getInt("MitarbeiterID")+"");
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
    public static void newArbeitszeit(int mitarbeiterID, String schichtbeginn, String schichtende, String schichtart){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO arbeitszeiten(MitarbeiterID, Schichtbeginn, Schichtende, Schichtart) VALUES(" +
                                "'"+mitarbeiterID+"','"+schichtbeginn+"','"+schichtende+"','"+schichtart+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static void neuerKunde (String vorname, String nachname,String telefonnummer, String email ){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO kunde(vorname, nachname, telefonnummer, email) VALUES(" +
                                "'"+vorname+"','"+nachname+"','"+telefonnummer+"','"+email+"')";
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
    public static void deleteArbeitszeit(int arbeitszeitID){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "DELETE FROM arbeitszeiten WHERE ArbeitszeitID='"+arbeitszeitID+"'";
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
    public static void newMemo(String beschreibung, String erledigungsDatum, int mitarbeiterID){
        con = getInstance();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO memo(Beschreibung, EintragDatum, MemoVon) VALUES('"+beschreibung+"','"+erledigungsDatum+"','"+mitarbeiterID+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("Neue Memo//SQLException: " + e.getMessage());
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
                        "SELECT * FROM memo " +
                                "INNER JOIN mitarbeiter ON mitarbeiter.MitarbeiterID = memo.MemoVon";

                ResultSet result = query.executeQuery(sql);
                String title;

                while (result.next()) {
                    Memo memo= new Memo();
                    String memoDate=result.getString("EintragDatum");
                    DateFormat dateFormat=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    //DateFormat dateFormat=DateFormat.getDateTimeInstance();
                    Date dateMemo=null;

                    try {
                        dateMemo= dateFormat.parse(memoDate);

                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                    memo.setMemoID(result.getInt("MemoID"));
                    memo.setBeschreibung(result.getString("Beschreibung"));
                    memo.setEintragDatum(dateMemo);
                    memo.setMitarbeiterM(result.getString("vorname")+" "+result.getString("nachname"));
                    memos.add(memo);

                }
            } catch (SQLException e) {
                System.out.println("SET Memo //SQLException: " + e.getMessage());
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
                    temp.setResourceId(result.getInt("MitarbeiterID")+"");
                    temp.setResourceId(result.getInt("MitarbeiterID")+"");
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
    public static List<FullCalendarEventBean> getKurszeitenMitarbeiter(int mitarbeiter){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<FullCalendarEventBean> fb= new ArrayList<FullCalendarEventBean>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM kurse " +
                                "INNER JOIN mitarbeiter ON kurse.MitarbeiterID = mitarbeiter.MitarbeiterID " +
                                "WHERE kurse.MitarbeiterID = '"+mitarbeiter+"'";
                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    FullCalendarEventBean temp = new FullCalendarEventBean();

                    String schichtbeginn=result.getString("Kursbeginn");
                    String schichtende=result.getString("Kursende");
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
                    temp.setResourceId(result.getInt("MitarbeiterID")+"");
                    temp.setStart(start);
                    temp.setEnd(end);
                    temp.setColor("red");
                    temp.setTitle(result.getInt("KursID")+" "+result.getString("Kursbezeichnung"));

                    fb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("Get Kurse //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return fb;
    }
    public static List<FullCalendarEventBean> getKurse(){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<FullCalendarEventBean> fb= new ArrayList<FullCalendarEventBean>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM kurse " +
                                "INNER JOIN mitarbeiter ON kurse.MitarbeiterID = mitarbeiter.MitarbeiterID ";

                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    FullCalendarEventBean temp = new FullCalendarEventBean();

                    String schichtbeginn=result.getString("Kursbeginn");
                    String schichtende=result.getString("Kursende");
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
                    temp.setResourceId(result.getInt("MitarbeiterID")+"");
                    temp.setColor(result.getString("kalenderfarbe"));
                    temp.setTitle(result.getString("Kursbezeichnung")+" ; "+result.getString("vorname")+" ; "+result.getInt("KursID"));

                    fb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("Get Kurse //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return fb;
    }

    public static List<FullCalendarEventBean> getKrankheitszeiten(int mitarbeiter){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<FullCalendarEventBean> fb= new ArrayList<FullCalendarEventBean>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM krankheitstage " +
                                "INNER JOIN mitarbeiter ON krankheitstage.MitarbeiterID = mitarbeiter.MitarbeiterID " +
                                "WHERE krankheitstage.MitarbeiterID = '"+mitarbeiter+"'";
                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    FullCalendarEventBean temp = new FullCalendarEventBean();

                    String schichtbeginn=result.getString("Krankbeginn");
                    String schichtende=result.getString("Krankende");
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
                    temp.setResourceId(result.getInt("MitarbeiterID")+"");
                    temp.setStart(start);
                    temp.setEnd(end);
                    temp.setColor(result.getString("kalenderfarbe"));
                    temp.setTitle("Krank");

                    fb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("Get  Krankheitstage //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return fb;
    }
    public static List<FullCalendarEventBean> getUniZeiten(int mitarbeiter){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<FullCalendarEventBean> fb= new ArrayList<FullCalendarEventBean>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM unitage " +
                                "INNER JOIN mitarbeiter ON unitage.MitarbeiterID = mitarbeiter.MitarbeiterID " +
                                "WHERE unitage.MitarbeiterID = '"+mitarbeiter+"'";
                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    FullCalendarEventBean temp = new FullCalendarEventBean();

                    String schichtbeginn=result.getString("Unibeginn");
                    String schichtende=result.getString("Uniende");
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
                    temp.setResourceId(result.getInt("MitarbeiterID")+"");
                    temp.setStart(start);
                    temp.setEnd(end);
                    temp.setColor(result.getString("kalenderfarbe"));
                    temp.setTitle("Uni");

                    fb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("Get  Unitage //SQLException: " + e.getMessage());
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
    public static void newKurs(String kursbezeichnung, String kursstart, String kursende, int mitarbeiterID){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO kurse(Kursbezeichnung, Kursbeginn, Kursende, MitarbeiterID) VALUES(" +
                                "'"+kursbezeichnung+"','"+kursstart+"','"+kursende+"','"+mitarbeiterID+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println(" neuer Kurs//SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static void newKursbezeichnung(String kursbezeichnung){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO kursbezeichnungen(Bezeichnung) VALUES(" +
                                "'"+kursbezeichnung+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("neuer Kursbezeichnung//SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static void newKrankheit(int mitarbeiterID, String krankbeginn, String krankende){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO krankheitstage(MitarbeiterID, Krankbeginn, Krankende) VALUES(" +
                                "'"+mitarbeiterID+"','"+krankbeginn+"','"+krankende+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("Set neuen Krankheit//SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static void newUni(int mitarbeiterID, String unibeginn, String uniende){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO unitage(MitarbeiterID, Unibeginn, Uniende) VALUES(" +
                                "'"+mitarbeiterID+"','"+unibeginn+"','"+uniende+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("Set neuen Unitage//SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static void newAusgleichtag(int mitarbeiterID, String unibeginn, String uniende){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO ausgleichtage(MitarbeiterID, Unibeginn, Uniende) VALUES(" +
                                "'"+mitarbeiterID+"','"+unibeginn+"','"+uniende+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("Set neuen ausgleichtage//SQLException: " + e.getMessage());
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
    public static List<Kurse> getKursList(){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<Kurse> fb= new ArrayList<Kurse>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM kurse " +
                                "INNER JOIN mitarbeiter ON kurse.MitarbeiterID = mitarbeiter.MitarbeiterID";

                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    Kurse temp = new Kurse();

                    String schichtbeginn=result.getString("Kursbeginn");
                    String schichtende=result.getString("Kursende");
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
                    temp.setKursID(result.getInt("KursID"));
                    temp.setKursstart(start);
                    temp.setKursende(end);
                    temp.setMitarbeiter(result.getString("vorname")+" "+result.getString("nachname"));
                    temp.setKursbezeichnung(result.getString("Kursbezeichnung"));
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
    public static List<Krankheit> getKranklist(int mitarbeiter){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<Krankheit> fb= new ArrayList<Krankheit>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM krankheitstage " +
                                "INNER JOIN mitarbeiter ON krankheitstage.MitarbeiterID = mitarbeiter.MitarbeiterID " +
                                "WHERE krankheitstage.MitarbeiterID = '"+mitarbeiter+"'";
                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    Krankheit temp = new Krankheit();

                    String schichtbeginn=result.getString("Krankbeginn");
                    String schichtende=result.getString("Krankende");
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
                    temp.setUrlaubsID(result.getInt("KrankID"));
                    temp.setUrlaubBeginn(start);
                    temp.setUrlaubEnde(end);
                    ;

                    fb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("Get Liste Krankheitstage //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return fb;
    }
    public static List<Uni> getUnilist(int mitarbeiter){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<Uni> fb= new ArrayList<Uni>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM unitage " +
                                "INNER JOIN mitarbeiter ON unitage.MitarbeiterID = mitarbeiter.MitarbeiterID " +
                                "WHERE unitage.MitarbeiterID = '"+mitarbeiter+"'";
                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    Uni temp = new Uni();

                    String schichtbeginn=result.getString("Unibeginn");
                    String schichtende=result.getString("Uniende");
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
                    temp.setUrlaubsID(result.getInt("UniID"));
                    temp.setUrlaubBeginn(start);
                    temp.setUrlaubEnde(end);
                    ;

                    fb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("Get Liste Unitage //SQLException: " + e.getMessage());
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
    public static void deleteKursbezeichnung(String kursbezeichnung){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "DELETE FROM kursbezeichnungen WHERE Bezeichnung='"+kursbezeichnung+"'";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("Delete Kurs//SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static void deleteKurs(int kursID){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "DELETE FROM kurse WHERE KursID='"+kursID+"'";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("Delete Kurs//SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static void deleteKrank(int krankID){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "DELETE FROM krankheitstage WHERE KrankID='"+krankID+"'";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("Krank lösche//SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static void deleteUni(int uniID){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "DELETE FROM unitage WHERE UniID='"+uniID+"'";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("Uni lösche//SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static void deleteAusgleichtag(int uniID){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "DELETE FROM ausgleichtage WHERE UniID='"+uniID+"'";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("Ausgleichtag lösche//SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }

    public static List<Arbeitszeit> getArbeitszeiten(int mitarbeiter){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<Arbeitszeit> fb= new ArrayList<Arbeitszeit>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM arbeitszeiten " +
                                "WHERE MitarbeiterID = '"+mitarbeiter+"'";
                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    Arbeitszeit temp = new Arbeitszeit();

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
                    temp.setArbeitszeitID(result.getInt("ArbeitszeitID"));
                    temp.setArbeitsstart(start);
                    temp.setArbeitsende(end);
                    temp.setSchichtart(result.getString("Schichtart"));


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
    public static List<Arbeitszeit> getArbeitszeiten(){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<Arbeitszeit> fb= new ArrayList<Arbeitszeit>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM arbeitszeiten";

                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    Arbeitszeit temp = new Arbeitszeit();

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
                    temp.setArbeitszeitID(result.getInt("ArbeitszeitID"));
                    temp.setArbeitsstart(start);
                    temp.setArbeitsende(end);
                    temp.setSchichtart(result.getString("Schichtart"));


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
    public static void updatearbeitszeit(int arbeitszeitID, String arbeitsstart, String arbeitsende){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "Update arbeitszeiten SET Schichtbeginn='"+arbeitsstart+"', Schichtende='"+arbeitsende+"' WHERE ArbeitszeitID='"+arbeitszeitID+"'";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }

    public static List<Termine> getKundeTermine(int kundenid){//hier sollen die Events geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();

        List<Termine> fb= new ArrayList<Termine>();

        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT *" +
                                "FROM termin " +
                                "WHERE KundenID = '"+kundenid+"'";
                ResultSet result = query.executeQuery(sql);
                String title;

                while (result.next()) {
                    Termine temp = new Termine();
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
                    temp.setTerminstart(start);
                    temp.setTerminende(end);
                    String terminart= result.getString("Terminart");

                    temp.setTerminart(terminart);
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

    public static List<Termine> getAllTermine(){//hier sollen die Events geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();

        List<Termine> fb= new ArrayList<Termine>();

        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT *" +
                                "FROM termin";

                ResultSet result = query.executeQuery(sql);
                String title;

                while (result.next()) {
                    Termine temp = new Termine();
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
                    temp.setTerminstart(start);
                    temp.setTerminende(end);
                    String terminart= result.getString("Terminart");

                    temp.setTerminart(terminart);
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

    public static List<Kunde> getKundeList(){//hier sollen die Events geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();

        List<Kunde> fb= new ArrayList<Kunde>();

        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT *" +
                                "FROM kunde ";

                ResultSet result = query.executeQuery(sql);
                String title;

                while (result.next()) {
                    Kunde temp = new Kunde();
                    temp.setVorname(result.getString("vorname"));
                    temp.setNachname(result.getString("nachname"));
                    temp.setKundeID(result.getInt("KundenID"));
                    temp.setTelefonnummer(result.getString("telefonnummer"));
                    temp.setEmail(result.getString("email"));
                    temp.setStrasse(result.getString("Strasse"));
                    temp.setPlz(result.getString("Plz"));
                    temp.setOrt(result.getString("Ort"));
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

    public static int getSchichtAnzahl(int kundenid, String schichtart){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        int counter=0;
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT COUNT(*) AS Schichtanzahl FROM arbeitszeiten " +
                                "WHERE MitarbeiterID = '"+kundenid+"' AND Schichtart = '"+schichtart+"' ";
                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    counter= result.getInt("Schichtanzahl");

                }
            } catch (SQLException e) {
                System.out.println("SET Counter //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return counter;
    }
    public static void newTerminErledigt(String ergebnis, String hinweis, int terminID){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO terminerledigt(Ergebnis, Hinweis, TerminID) VALUES(" +
                                "'"+ergebnis+"','"+hinweis+"','"+terminID+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static void updateTermin( int terminID){
        con = getInstance();
        String beschreibung= "Dieser Termin ist abgeschlossen";
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "Update termin SET Beschreibung='"+beschreibung+"' WHERE TerminID = '"+terminID+"'";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("Update Termin// SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static List<ErledigteTermine> getErledigteTermine(){//hier sollen die Events geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();

        List<ErledigteTermine> fb= new ArrayList<ErledigteTermine>();

        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM terminerledigt " +
                                "INNER JOIN termin " +
                                "ON terminerledigt.TerminID = termin.TerminID " +
                                "INNER JOIN kunde " +
                                "ON termin.KundenID = kunde.KundenID " +
                                "INNER JOIN mitarbeiter " +
                                "ON termin.MitarbeiterMacherID = mitarbeiter.MitarbeiterID " +
                                "";
                ResultSet result = query.executeQuery(sql);
                String title;

                while (result.next()) {
                    ErledigteTermine temp = new ErledigteTermine();
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
                    temp.setTerminstart(start);
                    temp.setTerminende(end);
                    String terminart= result.getString("Terminart");
                    temp.setTerminart(terminart);
                    temp.setErgebnis(result.getString("Ergebnis"));
                    temp.setHinweis(result.getString("Hinweis"));
                    temp.setKundeName(result.getString("kunde.vorname")+" "+result.getString("kunde.nachname"));
                    temp.setMitarbeiterName(result.getString("mitarbeiter.vorname")+" "+result.getString("mitarbeiter.nachname"));
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

    public static void updateKunde( int kundeID, String strasse, String plz, String ort, String tele, String mail){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "Update kunde SET Strasse='"+strasse+"',Plz='"+plz+"',Ort='"+ort+"',telefonnummer='"+tele+"',email='"+mail+"' WHERE KundenID = '"+kundeID+"'";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("Update Kunde// SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    public static void neuerKunde (String vorname, String nachname,String telefonnummer, String email, String strasse, String plz, String ort ){
        con = getInstance();
        if(con != null) {

            Statement query;
            try {
                query = con.createStatement();
                String sql=
                        "INSERT INTO kunde(vorname, nachname, telefonnummer, email, Strasse, Plz, Ort) VALUES(" +
                                "'"+vorname+"','"+nachname+"','"+telefonnummer+"','"+email+"','"+strasse+"','"+plz+"','"+ort+"')";
                query.executeUpdate(sql);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////
    public static List<FullCalendarEventBean> getAllEventsRes() {//hier sollen die Events geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();

        List<FullCalendarEventBean> fb = new ArrayList<FullCalendarEventBean>();

        if (con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT termin.TerminID, termin.Terminstart, termin.Terminende, termin.MitarbeiterMacherID AS id, M1.kalenderfarbe, termin.terminart, kunde.vorname, kunde.nachname, kunde.telefonnummer, termin.Beschreibung, M2.vorname AS eintrager " +
                                "FROM termin " +
                                "INNER JOIN mitarbeiter M1 ON termin.MitarbeiterMacherID = M1.MitarbeiterID " +
                                "INNER JOIN mitarbeiter M2 ON termin.MitarbeiterSchreiberID = M2.MitarbeiterID " +
                                "INNER JOIN kunde ON termin.KundenID = kunde.KundenID " +
                                "WHERE M1.kalendereintrag LIKE 'true'";

                ResultSet result = query.executeQuery(sql);
                String title;

                while (result.next()) {
                    FullCalendarEventBean temp = new FullCalendarEventBean();
                    String terminstart = result.getString("Terminstart");
                    String terminende = result.getString("Terminende");
                    DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    //DateFormat dateFormat=DateFormat.getDateTimeInstance();
                    Date start = null;
                    Date end = null;
                    try {
                        start = dateFormat.parse(terminstart);
                        end = dateFormat.parse(terminende);
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                    temp.setStart(start);
                    temp.setResourceId(result.getInt("id") + "");
                    temp.setEnd(end);
                    temp.setColor(result.getString("kalenderfarbe"));
                    String terminart = result.getString("Terminart");
                    String vorname = result.getString("vorname");
                    String nachname = result.getString("nachname");
                    String tele = result.getString("telefonnummer");
                    String beschreibung = result.getString("Beschreibung");
                    String eintrager = result.getString("eintrager");
                    int terminID = result.getInt("TerminID");
                    title = terminart + " ; " + vorname + " ; " + nachname + " ; " + tele + " ; " + beschreibung + " ; " + eintrager + " ; " + terminID;
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
    public static List<FullCalendarEventBean> getallFreieTermineRes() {//hier sollen die Events geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();

        List<FullCalendarEventBean> fb = new ArrayList<FullCalendarEventBean>();

        if (con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT freieTermine.TerminID, freieTermine.Terminstart, freieTermine.Terminende, freieTermine.MitarbeiterMacherID AS id, M1.kalenderfarbe, freieTermine.Beschreibung, M2.vorname AS eintrager " +
                                "FROM freieTermine " +
                                "INNER JOIN mitarbeiter M1 ON freieTermine.MitarbeiterMacherID = M1.MitarbeiterID " +
                                "INNER JOIN mitarbeiter M2 ON freieTermine.MitarbeiterSchreiberID = M2.MitarbeiterID " +
                                "WHERE M1.kalendereintrag LIKE 'true'";


                ResultSet result = query.executeQuery(sql);
                String title;

                while (result.next()) {
                    FullCalendarEventBean temp = new FullCalendarEventBean();
                    String terminstart = result.getString("Terminstart");
                    String terminende = result.getString("Terminende");
                    DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    //DateFormat dateFormat=DateFormat.getDateTimeInstance();
                    Date start = null;
                    Date end = null;
                    try {
                        start = dateFormat.parse(terminstart);
                        end = dateFormat.parse(terminende);
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                    temp.setStart(start);
                    temp.setResourceId(result.getInt("id") + "");
                    temp.setEnd(end);
                    temp.setColor(result.getString("kalenderfarbe"));
                    String beschreibung = result.getString("Beschreibung");
                    String eintrager = result.getString("eintrager");
                    int terminID = result.getInt("TerminID");
                    title = beschreibung + " ; " + eintrager + " ; " + terminID;
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
    public static List<FullCalendarEventBean> getAllArbeitszeitenRes(){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
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
                                "WHERE mitarbeiter.kalendereintrag LIKE 'true'";

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
                    temp.setResourceId(result.getInt("MitarbeiterID")+"");
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
    public static List<FullCalendarEventBean> getPlanerAllArbeitszeitenRes(){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
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
                                "WHERE mitarbeiter.kalendereintrag LIKE 'true'";

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
                    temp.setResourceId(result.getInt("MitarbeiterID")+"");
                    temp.setStart(start);
                    temp.setEnd(end);
                    temp.setColor(result.getString("kalenderfarbe"));
                    temp.setTitle(result.getInt("ArbeitszeitID")+ " ; " +"Arbeitszeit");
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
    public static List<FullCalendarEventBean> getUrlaubszeitenRes(){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
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
                                "WHERE mitarbeiter.kalendereintrag LIKE 'true'";

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

                    temp.setResourceId(result.getInt("MitarbeiterID")+"");
                    temp.setStart(start);
                    temp.setEnd(end);
                    temp.setColor(result.getString("kalenderfarbe"));
                    temp.setTitle(result.getInt("UrlaubszeitID")+ " ; " +"Urlaub");

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
    public static List<FullCalendarEventBean> getKurszeitenMitarbeiterRes(){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<FullCalendarEventBean> fb= new ArrayList<FullCalendarEventBean>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM kurse " +
                                "INNER JOIN mitarbeiter ON kurse.MitarbeiterID = mitarbeiter.MitarbeiterID " +
                                "WHERE mitarbeiter.kalendereintrag LIKE 'true'";

                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    FullCalendarEventBean temp = new FullCalendarEventBean();

                    String schichtbeginn=result.getString("Kursbeginn");
                    String schichtende=result.getString("Kursende");
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
                    temp.setResourceId(result.getInt("MitarbeiterID")+"");
                    temp.setStart(start);
                    temp.setEnd(end);
                    temp.setColor("red");
                    temp.setTitle(result.getInt("KursID")+ " ; " +result.getString("Kursbezeichnung"));

                    fb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("Get Kurse //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return fb;
    }
    public static List<FullCalendarEventBean> getKrankheitszeitenRes(){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<FullCalendarEventBean> fb= new ArrayList<FullCalendarEventBean>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM krankheitstage " +
                                "INNER JOIN mitarbeiter ON krankheitstage.MitarbeiterID = mitarbeiter.MitarbeiterID " +
                                "WHERE mitarbeiter.kalendereintrag LIKE 'true'";

                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    FullCalendarEventBean temp = new FullCalendarEventBean();

                    String schichtbeginn=result.getString("Krankbeginn");
                    String schichtende=result.getString("Krankende");
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
                    temp.setResourceId(result.getInt("MitarbeiterID")+"");
                    temp.setStart(start);
                    temp.setEnd(end);
                    temp.setColor(result.getString("kalenderfarbe"));
                    temp.setTitle(result.getInt("KrankID")+ " ; " +"Krank");

                    fb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("Get  Krankheitstage //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return fb;
    }
    public static List<FullCalendarEventBean> getUniRes(){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<FullCalendarEventBean> fb= new ArrayList<FullCalendarEventBean>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM unitage " +
                                "INNER JOIN mitarbeiter ON unitage.MitarbeiterID = mitarbeiter.MitarbeiterID " +
                                "WHERE mitarbeiter.kalendereintrag LIKE 'true'";

                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    FullCalendarEventBean temp = new FullCalendarEventBean();

                    String schichtbeginn=result.getString("Unibeginn");
                    String schichtende=result.getString("Uniende");
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
                    temp.setResourceId(result.getInt("MitarbeiterID")+"");
                    temp.setStart(start);
                    temp.setEnd(end);
                    temp.setColor(result.getString("kalenderfarbe"));
                    temp.setTitle(result.getInt("UniID")+ " ; " +"Uni");

                    fb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("Get  UnitageRes //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return fb;
    }
    public static List<FullCalendarEventBean> getAusgleichtageRes(){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<FullCalendarEventBean> fb= new ArrayList<FullCalendarEventBean>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM ausgleichtage " +
                                "INNER JOIN mitarbeiter ON ausgleichtage.MitarbeiterID = mitarbeiter.MitarbeiterID " +
                                "WHERE mitarbeiter.kalendereintrag LIKE 'true'";

                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    FullCalendarEventBean temp = new FullCalendarEventBean();

                    String schichtbeginn=result.getString("Unibeginn");
                    String schichtende=result.getString("Uniende");
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
                    temp.setResourceId(result.getInt("MitarbeiterID")+"");
                    temp.setStart(start);
                    temp.setEnd(end);
                    temp.setColor(result.getString("kalenderfarbe"));
                    temp.setTitle(result.getInt("UniID")+ " ; " +"Ausgleichtag");

                    fb.add(temp);

                }
            } catch (SQLException e) {
                System.out.println("Get  AusgleichtagRes //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return fb;
    }
    public static List<FullcalendarRessouceBean> getAllResources(){ //hier sollen die Arbeitszeiten geholt werden und am ende der Eventlist hinzugefügt werdern
        con = getInstance();
        List<FullcalendarRessouceBean> fb= new ArrayList<FullcalendarRessouceBean>();
        if(con != null) {
            // Abfrage-Statement erzeugen.
            Statement query;
            try {
                query = con.createStatement();


                String sql =
                        "SELECT * FROM mitarbeiter "+
                                "WHERE mitarbeiter.kalendereintrag LIKE 'true'";


                ResultSet result = query.executeQuery(sql);


                while (result.next()) {
                    FullcalendarRessouceBean temp = new FullcalendarRessouceBean();



                        temp.setId(result.getInt("MitarbeiterID")+"");


                        temp.setTitle(result.getString("vorname"));



                    fb.add(temp);


                }
            } catch (SQLException e) {
                System.out.println("Get  Resources //SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }

        return fb;
    }

}
