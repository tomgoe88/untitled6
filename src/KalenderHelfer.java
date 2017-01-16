/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
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

    private String currentMonth;
    private String test;

    private Date changeMontag;
    private Date changeDienstag;
    private Date changeMittwoch;
    private Date changeDonnerstag;
    private Date changeFreitag;
    private Date changeSamstag;
    private Date changeSonntag;

    private String montag;
    private String dienstag;
    private String mittwoch;
    private String donnerstag;
    private String freitag;
    private String samstag;
    private String sonntag;
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

    int month;
    int day;
    int jetday;
    private GregorianCalendar kalender= new GregorianCalendar();



    /**
     * Creates a new instance of KalenderHelfer
     */
    public KalenderHelfer() {
        mitarbeiter= new ArrayList<Mitarbeiter>();
        Mitarbeiter m= new Mitarbeiter("Otto");
        mitarbeiter.add(m);
        mitarbeiter.add(new Mitarbeiter("Hannes"));
        mitarbeiter.add(new Mitarbeiter("Walter"));
        mitarbeiter.add(new Mitarbeiter("Gregor"));
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

    public void handleDateSelect(SelectEvent event) {
        date = (Date) event.getObject();
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(this.getChangeDate());
        int day= gc.get(Calendar.DAY_OF_WEEK);
        if(day== Calendar.SUNDAY){
            gc.add(Calendar.DAY_OF_MONTH, 0);
        } else{
            gc.add(Calendar.DAY_OF_MONTH,1);
        }
        //
        this.date= gc.getTime();

    }


    public Date getChangeDate() {


        return changeDate;
    }

    public Mitarbeiter getMitarbeit() {
        if(q != null){
            for(Mitarbeiter m: mitarbeiter){
                if(m.getName().equalsIgnoreCase(q)){
                    mitarbeit= m;
                }
            }
        } else {
            mitarbeit=null;
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

    public String getMontag() {
        return "Montag";
    }

    public String getDienstag() {
        return "Dienstag";
    }

    public String getMittwoch() {
        return "Mittwoch";
    }

    public String getDonnerstag() {
        return "Donnerstag";
    }

    public String getFreitag() {
        return "Freitag";
    }

    public String getSamstag() {
        return "Samstag";
    }

    public String getSonntag() {
        return "Sonntag";
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
    public Date getChangeSonntag() {
        if(date!= null) {
            GregorianCalendar c= new GregorianCalendar();
            c.setTime(date);
            int f= Calendar.DAY_OF_WEEK;
            if(f== Calendar.SUNDAY){
                c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                c.add(Calendar.DAY_OF_WEEK, 7);
            } else {
                c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                c.add(Calendar.DAY_OF_WEEK, 1);
            }
            changeSonntag = c.getTime();
        }

        return changeSonntag;
    }

    public void setChangeSonntag(Date changeSonntag) {
        this.changeSonntag = changeSonntag;
    }

    public Date getChangeMontag() {
        if (date!=null) {
            GregorianCalendar c= new GregorianCalendar();
            c.setTime(date);
            //int f= c.getFirstDayOfWeek();
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            c.add(Calendar.DAY_OF_WEEK, 1);
            changeMontag = c.getTime();
        }

        return changeMontag;
    }

    public void setChangeMontag(Date changeMontag) {
        this.changeMontag = changeMontag;
    }

    public Date getChangeDienstag() {
        if (date!=null) {
            GregorianCalendar c= new GregorianCalendar();
            c.setTime(date);
            //int f= c.getFirstDayOfWeek();
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            c.add(Calendar.DAY_OF_WEEK, 1);
            changeDienstag = c.getTime();
        }

        return changeDienstag;
    }

    public void setChangeDienstag(Date changeDienstag) {
        this.changeDienstag = changeDienstag;
    }

    public Date getChangeMittwoch() {
        if(date!=null){
            GregorianCalendar c= new GregorianCalendar();
            c.setTime(date);
            //int f= c.getFirstDayOfWeek();
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            c.add(Calendar.DAY_OF_WEEK, 1);
            changeMittwoch = c.getTime();
        }

        return changeMittwoch;
    }

    public void setChangeMittwoch(Date changeMittwoch) {
        this.changeMittwoch = changeMittwoch;
    }

    public Date getChangeDonnerstag() {
        if (date!=null){
            GregorianCalendar c= new GregorianCalendar();
            c.setTime(date);
            //int f= c.getFirstDayOfWeek();
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
            c.add(Calendar.DAY_OF_WEEK, 1);
            changeDonnerstag = c.getTime();
        }

        return changeDonnerstag;
    }

    public void setChangeDonnerstag(Date changeDonnerstag) {
        this.changeDonnerstag = changeDonnerstag;
    }

    public Date getChangeFreitag() {
        if(date!=null){
            GregorianCalendar c= new GregorianCalendar();
            c.setTime(date);
            //int f= c.getFirstDayOfWeek();
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            c.add(Calendar.DAY_OF_WEEK, 1);
            changeFreitag = c.getTime();
        }

        return changeFreitag;
    }

    public void setChangeFreitag(Date changeFreitag) {
        this.changeFreitag = changeFreitag;
    }

    public Date getChangeSamstag() {
        if(date!=null){
            GregorianCalendar c= new GregorianCalendar();
            c.setTime(date);
            //int f= c.getFirstDayOfWeek();
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            c.add(Calendar.DAY_OF_WEEK, 1);
            changeSamstag = c.getTime();
        }

        return changeSamstag;
    }

    public void setChangeSamstag(Date changeSamstag) {
        this.changeSamstag = changeSamstag;
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
    ///TODO es wird noch kein Termin erstellt, danach muss geschaut werden
    //bei click auf neuer Termin
    public void newTermin(){
        calendarBean=null;

        String vorname =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("vorname");
        String nachname =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nachname");
        String tele =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tele");
        String beschreibung =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("beschreibung");

        String title= terminart+" | "+vorname+" "+nachname+" | "+tele+" | "+beschreibung+" | "+eintrager;
        this.calendarBean= new FullCalendarEventBean(title, start);
        calendarBean.setEnd(end);
        for ( Mitarbeiter m: this.mitarbeiter){
            if(m.getName().equalsIgnoreCase(getMa())){
                m.getList().add(calendarBean);
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








}
