/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import sun.security.jca.GetInstance;

/**
 *
 * @author Jutom
 */
@ManagedBean
@ApplicationScoped
public class KalenderHelfer {
    private int currentWeek;
    private String tempColor;
    private String currentMonth;
    private String test;
    private String theDefaultDate;

    private Date defaultDate;

    private Date changeDate;
    private Date currentDate;
    private String changedDate;
    private Date date=null;
    private List<Mitarbeiter> mitarbeiter;
    private List<Termine> termine;
    private Date chooseDate;
    private FullCalendarEventBean calendarBean;
    private String ma;
    private String q;
    private Mitarbeiter mitarbeit;
    private Date start;
    private Date end;
    private static KalenderHelfer instance= new KalenderHelfer();
    private KalenderHelfer helfer;
    private String terminart;
    private String eintrager;
    private String text;
    private String eingetragenVorname;
    private String eingetragenTerminart;
    private String eingetragenNachname;
    private String eingetragenEintraeger;
    private String eingetrageneTelefonnummer;
    private String eignetrageneBeschreibung;
    private TimeZone timeZone;

    int month;
    int day;
    int jetday;
    private GregorianCalendar kalender= new GregorianCalendar();



    /**
     * Creates a new instance of KalenderHelfer
     */
    public KalenderHelfer() {
        mitarbeiter= new ArrayList<Mitarbeiter>();
        Mitarbeiter m= new Mitarbeiter("Otto","orange");
        mitarbeiter.add(m);
        mitarbeiter.add(new Mitarbeiter("Hannes", "grey"));
        mitarbeiter.add(new Mitarbeiter("Walter","green"));
        mitarbeiter.add(new Mitarbeiter("Gregor", "blue"));
    }

    public TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getText() {
        return text;
    }
    public void myEvent(String texten){
        String [] terminspilt= texten.split(" ; ");
        this.eingetragenTerminart= terminspilt[0];
        this.eingetragenVorname=terminspilt[1];
        this.eingetragenNachname=terminspilt[2];
        this.eingetrageneTelefonnummer=terminspilt[3];
        this.eignetrageneBeschreibung=terminspilt[4];
        this.eingetragenEintraeger=terminspilt[5];
        this.text= texten;
    }

    public String getEignetrageneBeschreibung() {
        return eignetrageneBeschreibung;
    }

    public void setEignetrageneBeschreibung(String eignetrageneBeschreibung) {
        this.eignetrageneBeschreibung = eignetrageneBeschreibung;
    }

    public String getTheDefaultDate() {
        String dd;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.GERMAN);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        if(defaultDate!=null) {
            dd=dateFormat.format(defaultDate);
        } else {
            dd=dateFormat.format(new Date());
        }

        return dd;
    }

    public void setTheDefaultDate(String theDefaultDate) {
        this.theDefaultDate = theDefaultDate;
    }

    public String getEingetragenVorname() {
        return eingetragenVorname;
    }

    public void setEingetragenVorname(String eingetragenVorname) {
        this.eingetragenVorname = eingetragenVorname;
    }

    public String getEingetragenTerminart() {
        return eingetragenTerminart;
    }

    public void setEingetragenTerminart(String eingetragenTerminart) {
        this.eingetragenTerminart = eingetragenTerminart;
    }

    public String getEingetrageneTelefonnummer() {
        return eingetrageneTelefonnummer;
    }

    public void setEingetrageneTelefonnummer(String eingetrageneTelefonnummer) {
        this.eingetrageneTelefonnummer = eingetrageneTelefonnummer;
    }

    public String getEingetragenNachname() {
        return eingetragenNachname;
    }

    public void setEingetragenNachname(String eingetragenNachname) {
        this.eingetragenNachname = eingetragenNachname;
    }

    public String getEingetragenEintraeger() {
        return eingetragenEintraeger;
    }

    public void setEingetragenEintraeger(String eingetragenEintraeger) {
        this.eingetragenEintraeger = eingetragenEintraeger;
    }

    public void setText(String text) {
        this.text = text;
    }

    public KalenderHelfer getHelfer() {
        return KalenderHelfer.getInstance();
    }

    public void setHelfer(KalenderHelfer helfer) {
        this.helfer = helfer;
    }

    public String getTerminart() {
        return terminart;
    }

    public void setTerminart(String terminart) {
        this.terminart = terminart;
    }

    public String getEintrager() {
        return eintrager;
    }

    public void setEintrager(String eintrager) {
        this.eintrager = eintrager;
    }

    public static KalenderHelfer getInstance() {
        return instance;
    }

    public static void setInstance(KalenderHelfer instance) {
        KalenderHelfer.instance = instance;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public Date getChooseDate() {
        return chooseDate;
    }

    public void setChooseDate(Date chooseDate) {
        this.chooseDate = chooseDate;
    }

    public Date getDefaultDate() {
        return defaultDate;
    }

    public void setDefaultDate(Date defaultDate) {
        this.defaultDate = defaultDate;
    }

    public void handleDateSelect(SelectEvent event) {
        defaultDate = (Date) event.getObject(); //die AUswahl stimmt nciht, Datum ist nicht correct, hier sollte geprüft werden, welches Datum hier raus kommt
       GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(defaultDate);
  /*      int day= gc.get(Calendar.DAY_OF_WEEK);
        if(day== Calendar.SUNDAY){
            gc.add(Calendar.DAY_OF_MONTH, 0);
        } else{*/
            gc.add(Calendar.HOUR_OF_DAY,1);
       // }
        //
        this.defaultDate= gc.getTime();// auch hier schauen, welches Datum raus komme

    }


    public Date getChangeDate() {


        return changeDate;
    }

    public Mitarbeiter getMitarbeit() {
        if(q != null){
            for(Mitarbeiter m: mitarbeiter){
                if(m.getName().equalsIgnoreCase(q)){
                    mitarbeit= m;
                    break;
                }
                else {
                    mitarbeit=null;

                }
            }
        } else {
            mitarbeit=mitarbeiter.get(0);
        }

        return mitarbeit;
    }

    public void setMitarbeit(Mitarbeiter mitarbeit) {
        this.mitarbeit = mitarbeit;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public List<Mitarbeiter> getMitarbeiter() {
        //hier muss eine Select-Abfrage für die Mitarbeiter an diesem Tag gemacht werden


        return mitarbeiter;
    }

    public void setMitarbeiter(List<Mitarbeiter> mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public List<Termine> getTermine() {
        //hier muss eine Select-Abfrage mit allen Terminen der Mitarbeiter und dem jeweiligem Datum gemacht werden
        termine= new ArrayList<Termine>();

        return SQLHelper.termine(chooseDate);
    }

    public void setTermine(List<Termine> termine) {
        this.termine = termine;
    }




    public Date getDate(){

        return date;
    }


    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }
    public String chooseDate(){
        String st= new SimpleDateFormat("DD.MM.yyyy", Locale.GERMANY).format(changeDate.getTime());
        return st;
    }




    public Date getCurrentDate() {
        currentDate = new Date();
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }




    public int getCurrentWeek() {

        int week = this.kalender.get(Calendar.WEEK_OF_YEAR);

        return week;
    }

    public void setCurrentWeek(int currentWeek) {
        this.currentWeek = currentWeek;
    }



    public String getCurrentMonth() {
        Calendar calend= Calendar.getInstance();
        Date date= calend.getTime();
        String sdf= new SimpleDateFormat("MMMM yyyy,\n EEEE kk:hh ", Locale.GERMANY).format(date.getTime());
        return sdf+"Uhr";
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }



    public Calendar getKalender() {
        return kalender;
    }

    public void setKalender(GregorianCalendar kalender) {
        this.kalender = kalender;
    }



    public int getJetday() {
        return jetday;
    }
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }




    public String getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(String changedDate) {
        this.changedDate = changedDate;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public FullCalendarEventBean getCalendarBean() {
        return calendarBean;
    }

    public void setCalendarBean(FullCalendarEventBean calendarBean) {
        this.calendarBean = calendarBean;
    }

    public Date getStart() {
        return start;
    }

    public void handleStartDate(SelectEvent event) {
        start = (Date) event.getObject();
    }

    public Date getEnd() {
        return end;
    }

    public void handleEndDate(SelectEvent event) {
        end = (Date) event.getObject();
    }

    //bei click auf neuer Termin
    public void newTermin(){
        calendarBean=null;
//TODO: hier müssen die Angaben direkt in der Datenbank landen
        String vorname =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("vorname"); //TODO: der Vorname und Nachnname, Tele wird in der Tabelle Kunde gespeichert
        String nachname =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nachname");
        String tele =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tele");
        String beschreibung =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("beschreibung"); //TODO Beschreibung landet im Termin
        String title= terminart+" ; "+vorname+" ; "+nachname+" ; "+tele+" ; "+beschreibung+" ; "+eintrager;
        for ( Mitarbeiter m: this.mitarbeiter){
            if(m.getName().equalsIgnoreCase(getMa())){
                tempColor=m.getFarbe();
            }
        } //TODO hinzufügen in di einzelnen Objekte passiert dann beim Aurfu des Kalenders, dies wird dann aus der SQLbank gezogen
        for ( Mitarbeiter m: this.mitarbeiter){
            if(m.getName().equalsIgnoreCase(getMa())){
                this.calendarBean= new FullCalendarEventBean(title, start);
                calendarBean.setEnd(end);
                calendarBean.setColor(m.getFarbe());
                m.getList().add(calendarBean);
            } else {
                FullCalendarEventBean kb= new FullCalendarEventBean("In dieser Zeit liegt ein Termin bei "+getMa(),start);
                kb.setEnd(end);
                kb.setColor(tempColor);
                kb.setRendering("background");
                m.getList().add(kb);
            }
        }

    }

    public String getTest() {
        if(calendarBean!=null){
            test = calendarBean.toJson();
        }
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    //beim Speicher des Termins
    public void addTermin(){
        for ( Mitarbeiter m: this.mitarbeiter){
            if(m.getName().equalsIgnoreCase(getMa())){
                m.getCalendarList().getList().add(calendarBean);
            }
        }
    }
    public void myDate(String date){
        Calendar calendar= javax.xml.bind.DatatypeConverter.parseDateTime(date);
        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ActionListener called",
                "Date: " + date);
        Calendar c2=javax.xml.bind.DatatypeConverter.parseDateTime(date);;
        c2.add(Calendar.HOUR_OF_DAY,1);
        start= calendar.getTime();
        end=c2.getTime();
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void handleDefaultDate(SelectEvent event) {
        defaultDate = (Date) event.getObject();
    }








}
