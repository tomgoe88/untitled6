/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import net.bootsfaces.C;
import org.primefaces.component.calendar.*;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.mobile.component.page.Page;
import sun.security.jca.GetInstance;

/**
 *
 * @author Jutom
 */
@ManagedBean
@ViewScoped
public class KalenderHelfer {
    private boolean wocheTag;
    private int temp=0;
    private int currentWeek;
    private String tempColor;
    private String currentMonth;
    private String test;
    private String theDefaultDate;
    private int kundenID;
    private  int sEintrager;
    private Date defaultDate = new Date();
    private int terminID;
    private Date changeDate;
    private Date currentDate;
    private String changedDate;
    private Date date=null;
    private List<Mitarbeiter> mitarbeiter;
    private Date chooseDate;
    private FullCalendarEventBean calendarBean;
    private int ma;
    private int q=0;
    private Mitarbeiter mitarbeit;
    private Date start;
    private Date end;
    private static KalenderHelfer instance= new KalenderHelfer();
    private KalenderHelfer helfer;
    private String terminart;
    private String schichtart;
    private int eintrager;
    private String text;
    private String eingetragenVorname;
    private String eingetragenTerminart;
    private String eingetragenNachname;
    private String eingetragenEintraeger;
    private String eingetrageneTelefonnummer;
    private String eignetrageneBeschreibung;
    private TimeZone timeZone;
    private String termine;
    private String ergebnis;
    int month;
    int day;
    int jetday;
    private GregorianCalendar kalender= new GregorianCalendar();
    private int aufgabeId;
    private int aufgabenErledigerID;
    private int wochenArbeitszeit;
    private int wochenAufgababen;
    private boolean woechentlich;
    private Calendar work1;
    private Calendar work2;
    private boolean adminbool;
    private boolean terminErledigtTest= false;

    /**
     * Creates a new instance of KalenderHelfer
     */
    public KalenderHelfer() {

    }

    public String getSchichtart() {
        return schichtart;
    }

    public void setSchichtart(String schichtart) {
        this.schichtart = schichtart;
    }

    public boolean isAdminbool() {
        return adminbool;
    }

    public void setAdminbool(boolean adminbool) {
        this.adminbool = adminbool;
    }

    public int getWochenAufgababen() {
        return wochenAufgababen;
    }

    public void setWochenAufgababen(int wochenAufgababen) {
        this.wochenAufgababen = wochenAufgababen;
    }

    public TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isWoechentlich() {
        return woechentlich;
    }

    public void setWoechentlich(boolean woechentlich) {
        this.woechentlich = woechentlich;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getAufgabeId() {
        return aufgabeId;
    }

    public void setAufgabeId(int aufgabeId) {
        this.aufgabeId = aufgabeId;
    }

    public boolean isWocheTag() {
        return wocheTag;
    }

    public boolean isTerminErledigtTest() {
        return terminErledigtTest;
    }

    public void setTerminErledigtTest(boolean terminErledigt) {
        this.terminErledigtTest = terminErledigt;
    }

    public void setWocheTag(boolean wocheTag) {
        this.wocheTag = wocheTag;
    }
    public String getChangeAgenda(){
        String change;
        if(wocheTag == true){
            change = "agendaWeek";
        } else {
            change="agendaDay";
        }
        return change;
    }

    public String getErgebnis() {
        return ergebnis;
    }

    public void setErgebnis(String ergebnis) {
        this.ergebnis = ergebnis;
    }

    public String getText() {
        return text;
    }
    public void myEvent(String texten){
        terminErledigtTest=false;
        String [] terminspilt= texten.split(" ; ");
        this.eingetragenTerminart= terminspilt[0];
        this.eingetragenVorname=terminspilt[1];
        this.eingetragenNachname=terminspilt[2];
        this.eingetrageneTelefonnummer=terminspilt[3];
        this.eignetrageneBeschreibung=terminspilt[4];
        this.eingetragenEintraeger=terminspilt[5];
        this.terminID= Integer.parseInt(terminspilt[6]);
        this.text= texten;
        if(eignetrageneBeschreibung.equals("Dieser Termin ist abgeschlossen")){
            this.terminErledigtTest=true;
        }
    }
    public void deleteAppointment(){
        SQLHelper.deleteTermin(terminID);
    }
    public void terminErledigt(){
        String hinweis=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hinweistext");

        SQLHelper.newTerminErledigt(ergebnis,hinweis,terminID);
        SQLHelper.updateTermin(terminID);
    }
    public void onChange(){
        wochenArbeitszeit=temp;
    }
    public void onChangeAufgabenlaenge(){
        wochenAufgababen=temp;
    }

    public int getTerminID() {
        return terminID;
    }

    public void setTerminID(int terminID) {
        this.terminID = terminID;
    }

    public int getWochenArbeitszeit() {
        return wochenArbeitszeit;
    }

    public void setWochenArbeitszeit(int wochenArbeitszeit) {
        this.wochenArbeitszeit = wochenArbeitszeit;
    }

    public int getsEintrager() {
        return sEintrager;
    }

    public void setsEintrager(int sEintrager) {
        this.sEintrager = sEintrager;
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

    public int getEintrager() {
        return eintrager;
    }

    public void setEintrager(int eintrager) {
        this.eintrager = eintrager;
    }

    public static KalenderHelfer getInstance() {
        return instance;
    }

    public static void setInstance(KalenderHelfer instance) {
        KalenderHelfer.instance = instance;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
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
        ArbeitszeitController.setDefaultDate(defaultDate);

    }
    public void nextDay(){
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(defaultDate);
        gc.add(Calendar.DAY_OF_MONTH,1);
        this.defaultDate=gc.getTime();
        ArbeitszeitController.setDefaultDate(defaultDate);


    }
    public void preDay(){
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(defaultDate);
        gc.add(Calendar.DAY_OF_MONTH,-1);
        this.defaultDate=gc.getTime();
        ArbeitszeitController.setDefaultDate(defaultDate);

    }
    public void nextWeek(){
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(defaultDate);
        gc.add(Calendar.WEEK_OF_MONTH,1);
        this.defaultDate=gc.getTime();
        ArbeitszeitController.setDefaultDate(defaultDate);


    }
    public void preWeek(){
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(defaultDate);
        gc.add(Calendar.WEEK_OF_MONTH,-1);
        this.defaultDate=gc.getTime();
        ArbeitszeitController.setDefaultDate(defaultDate);

    }


    public Date getChangeDate() {


        return changeDate;
    }

    public Mitarbeiter getMitarbeit() {
        if(q != 0){
            for(Mitarbeiter m: getMitarbeiter()){
              //  if(m.getName().equalsIgnoreCase(q)){
                if(m.getMitarbeiterID()==q){
                    mitarbeit= m;
                    break;
                }
                else {
                    mitarbeit=null;

                }
            }
        } else {
            mitarbeit=null;//getMitarbeiter().get(0);
        }
        return mitarbeit;
    }
    //Das war die alte Idee
/*    public String getTerminplanerEvents(){ //TODO: diese Rückgabe wird den Kalender im Terminplaner hinzugefügt
        FullCalendarEventList event= new FullCalendarEventList();
        if(q != null) {

           event.getList().addAll( SQLHelper.getAllArbeitszeiten(q));
            event.getList().addAll( SQLHelper.getAllEvents(q));

        }
        return event.toJson();
    }*/

    public void setMitarbeit(Mitarbeiter mitarbeit) {
        this.mitarbeit = mitarbeit;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public List<Mitarbeiter> getMitarbeiter() {
        //hier muss eine Select-Abfrage für die Mitarbeiter an diesem Tag gemacht werden
        mitarbeiter = new ArrayList<Mitarbeiter>();
        mitarbeiter.addAll(SQLHelper.getMitarbeiterListe());
        return mitarbeiter;
    }

    public void setMitarbeiter(List<Mitarbeiter> mitarbeiter) {
        this.mitarbeiter = SQLHelper.getMitarbeiterListe();
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

        String vorname =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("vorname");
        String nachname =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nachname");
        String tele =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tele");
        String email=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("emailInput");;
        String beschreibung =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("beschreibung");
        String title= terminart+" ; "+vorname+" ; "+nachname+" ; "+tele+" ; "+beschreibung+" ; "+eintrager;
        Kunde temoKunde=null;
        if(SQLHelper.getKundeList().size()!=0){
            for(Kunde k: SQLHelper.getKundeList()){
                if (vorname.equalsIgnoreCase(k.getVorname()) && nachname.equalsIgnoreCase(k.getNachname())){
                    temoKunde=null;
                    kundenID=k.getKundeID();
                    break;
                }
                else{
                    kundenID=SQLHelper.getMaxKundenID()+1;
                    temoKunde= new Kunde();
                    temoKunde.setVorname(vorname);
                    temoKunde.setNachname(nachname);
                    temoKunde.setTelefonnummer(tele);
                    temoKunde.setEmail(email);
                }
            }
            if(temoKunde!=null){
                SQLHelper.neuerKunde(temoKunde.getVorname(),temoKunde.getNachname(),temoKunde.getTelefonnummer(),temoKunde.getEmail());
            }
        }else {
            SQLHelper.neuerKunde(vorname,nachname,tele, email);
            for(Kunde k: SQLHelper.getKundeList()) {
                if (vorname.equalsIgnoreCase(k.getVorname()) && nachname.equalsIgnoreCase(k.getNachname())) {
                    kundenID = k.getKundeID();
                    break;
                }
            }
        }

        SQLHelper.neuerTermin(mitarbeit.getMitarbeiterID(),kundenID,beschreibung,terminart,start.toString(),end.toString(), eintrager);
        int terminidmax= SQLHelper.getMaxTerminID();



        for ( Mitarbeiter m: this.mitarbeiter){
            if(m.getMitarbeiterID()!=mitarbeit.getMitarbeiterID()){
                SQLHelper.newSperrzeit(m.getMitarbeiterID(),start.toString(),end.toString(),mitarbeit.getMitarbeiterID(),terminidmax);
            }

        }
        vorname=null;
        nachname=null;
        tele=null;
        beschreibung=null;

    }
    public void newWorktime(){
        Date workstart;
        Date workend;
        if(woechentlich==false){
            SQLHelper.newArbeitszeit(mitarbeit.getMitarbeiterID(),start.toString(),end.toString(), schichtart);
        } else {
            int i=0;
            while(i<wochenArbeitszeit){
                SQLHelper.newArbeitszeit(mitarbeit.getMitarbeiterID(),start.toString(),end.toString(), schichtart);
                work1.add(Calendar.DAY_OF_WEEK, 7);
                start= work1.getTime();
                work2.add(Calendar.DAY_OF_WEEK,7);
                end=work2.getTime();
                i++;
            }
        }



    }
    public void newUrlaub(){
        GregorianCalendar startDate= new GregorianCalendar();
        GregorianCalendar endDate= new GregorianCalendar();
        startDate.setTime(start);
        endDate.setTime(end);
        startDate.set(Calendar.HOUR_OF_DAY, 1);
        endDate.set(Calendar.HOUR_OF_DAY,23);
        Date starting= startDate.getTime();
        Date ending=endDate.getTime();
        SQLHelper.newUrlaubszeit(mitarbeit.getMitarbeiterID(),starting.toString(),ending.toString());

    }
    public void newKrankheit(){
        GregorianCalendar startDate= new GregorianCalendar();
        GregorianCalendar endDate= new GregorianCalendar();
        startDate.setTime(start);
        endDate.setTime(end);
        startDate.set(Calendar.HOUR_OF_DAY, 1);
        endDate.set(Calendar.HOUR_OF_DAY,23);
        Date starting= startDate.getTime();
        Date ending=endDate.getTime();
        SQLHelper.newKrankheit(mitarbeit.getMitarbeiterID(),starting.toString(),ending.toString());

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
            if(m.getMitarbeiterID()==getMa()){
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
    public void myWorkDate(String date){
        work1= javax.xml.bind.DatatypeConverter.parseDateTime(date);
        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ActionListener called",
                "Date: " + date);
        work2=javax.xml.bind.DatatypeConverter.parseDateTime(date);;
        work2.add(Calendar.HOUR_OF_DAY,8);
        work2.add(Calendar.MINUTE,30);
        start= work1.getTime();
        end=work2.getTime();
    }
    public void myUrlaubDate(String date){
        Calendar calendar= javax.xml.bind.DatatypeConverter.parseDateTime(date);
        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ActionListener called",
                "Date: " + date);
        Calendar c2=javax.xml.bind.DatatypeConverter.parseDateTime(date);;
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
        ArbeitszeitController.setDefaultDate(defaultDate);
    }

    public String getTempColor() {
        return tempColor;
    }

    public void setTempColor(String tempColor) {
        this.tempColor = tempColor;
    }

    public void newWorker(){
        int macidd=0;
        String vorname =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("mitarbeitervorname");
        String nachname =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("mitarbeiternachname");
        SQLHelper.newMitarbeiter(vorname, nachname, tempColor);
        macidd= SQLHelper.getMaxMitarbeiterID();
        SQLHelper.newPassword(macidd,null,Boolean.toString(adminbool));
    }



    public int getAufgabenErledigerID() {
        return aufgabenErledigerID;
    }

    public void setAufgabenErledigerID(int aufgabenErledigerID) {
        this.aufgabenErledigerID = aufgabenErledigerID;
    }

    //TODO Methoden erstellen um aufgaben zu erledigen mit  public static void newAufgabe(int aufgabenID, int mitarbeiterID){ in der xHtml muss dann mit einem SelectItem der Mitarbeiter ausgesucht werden
   public void aufgabeErledigen(int aufgabenid){
        this.aufgabeId= aufgabenid;  //diese Methode kommt beim Klick in der tabelle, danahch wird ein Dialog geöffnet
   }

    public void aufgabeErledigt(){
       SQLHelper.updateAufgabe(aufgabeId);
       SQLHelper.newAufgabeErledigt(aufgabeId,aufgabenErledigerID);

    }
    public void neueAufgabe(){

        String aufgabena =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("aufgabenname");
        Date date;
        Calendar c= Calendar.getInstance();
        if(defaultDate!= null){
            c.setTime(defaultDate);
            c.set(Calendar.HOUR_OF_DAY, 1);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);

            date= c.getTime();
        } else {
            c.setTime(new Date());
            c.set(Calendar.HOUR_OF_DAY, 1);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            date= c.getTime();
        }
        if(woechentlich==false){
            SQLHelper.newAufgabe(aufgabena, date.toString());
        } else {
            int i=0;
            while(i<wochenAufgababen){
                SQLHelper.newAufgabe(aufgabena, date.toString());
                c.add(Calendar.DAY_OF_WEEK, 7);
                date= c.getTime();
                i++;
            }
        }

    }
    public void deleteMitarbeiter(int mitarbeiterID){
        SQLHelper.deleteMitarbeiter(mitarbeiterID);
    }
    public List<String> getKundenNames(){
        List<String> kundenanee= new ArrayList<String>();
        for(Kunde k: SQLHelper.getKundeList()){
            kundenanee.add(k.getVorname());
        }
        return kundenanee;
    }
    public List<String> getKundeNachname(){
        List<String> kundenanee= new ArrayList<String>();
        for(Kunde k: SQLHelper.getKundeList()){
            kundenanee.add(k.getNachname());
        }
        return kundenanee;
    }
    public void deleteUrlaub(int urlaubsID){
        SQLHelper.deleteUrlaub(urlaubsID);
    }
    public void deleteKrankheit(int krankID){
        SQLHelper.deleteKrank(krankID);
    }
    public List<Aufgabe> getAufgaben(){
        List<Aufgabe> aufgaben = new ArrayList<Aufgabe>();
        String date;
        if(defaultDate!= null){
            Calendar c= Calendar.getInstance();
            c.setTime(defaultDate);
            c.set(Calendar.HOUR_OF_DAY, 1);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);

            date= c.getTime().toString();
        } else {
            Calendar c= Calendar.getInstance();
            c.setTime(new Date());
            c.set(Calendar.HOUR_OF_DAY, 1);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);

            date= c.getTime().toString();
        }
        aufgaben =SQLHelper.getAufgaben(date);
        if(aufgaben.size()==0){
            Aufgabe temp= new Aufgabe();
            temp.setBeschreibung("-");
            aufgaben.add(temp);
        }
        return aufgaben;
    }

    public void deleteArbeitszeit(int arbeitszeitID){
        SQLHelper.deleteArbeitszeit(arbeitszeitID);
    }





}
