/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import java.util.*;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Jutom
 */
@ManagedBean
@SessionScoped
public class Mitarbeiter extends FullCalendarEventList {
    List<String> test;
    private String name;
    private List<Termine> termine;
    private String tem;
    private String farbe;
    private FullCalendarEventList calendarList;
    private int MitarbeiterID;
    private String arbeitszeiten;
    private String urlaubszeiten;
    private String password;
    private boolean admin;
    private static Date staticStart;
    private static Date staticEnd;
    private List<Urlaub> urlaubList;
    private List<Arbeitszeit> arbeitszeitList;
    private static Date filteredDatestart;
    private static Date filterDateEnd;
    private String arbeitsdauer;
    private static String tempArbeitsdauer;
    private String arbeitdauer;
    public Mitarbeiter(){

    }

    public String getPassword() {
        password=SQLHelper.getPassword(MitarbeiterID);
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        admin=SQLHelper.admin(MitarbeiterID);
        return admin;
    }

    public static Date getStaticStart() {
        return staticStart;
    }

    public static String getTempArbeitsdauer() {
        return tempArbeitsdauer;
    }

    public static void setTempArbeitsdauer(String tempArbeitsdauer) {
        Mitarbeiter.tempArbeitsdauer = tempArbeitsdauer;
    }

    public String getArbeitdauer() {
        arbeitdauer=tempArbeitsdauer;
        return arbeitdauer;
    }

    public void setArbeitdauer(String arbeitdauer) {
        this.arbeitdauer = arbeitdauer;
    }

    public static void setStaticStart(Date staticStart) {
        Mitarbeiter.staticStart = staticStart;
    }

    public static Date getStaticEnd() {
        return staticEnd;
    }

    public static void setStaticEnd(Date staticEnd) {
        Mitarbeiter.staticEnd = staticEnd;
    }

    public static Date getFilteredDatestart() {
        return filteredDatestart;
    }

    public static void setFilteredDatestart(Date filteredDatestart) {
        filteredDatestart = filteredDatestart;
    }

    public static Date getFilterDateEnd() {
        return filterDateEnd;
    }

    public static void setFilterDateEnd(Date filterDateEnd) {
        filterDateEnd = filterDateEnd;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Urlaub> getUrlaubList() {
        urlaubList= new ArrayList<Urlaub>();
        urlaubList.addAll(SQLHelper.getUrlaubsList(MitarbeiterID));
        return urlaubList;
    }
    public void filterDateStartDate(SelectEvent event) {
        filteredDatestart = (Date) event.getObject(); //die AUswahl stimmt nciht, Datum ist nicht correct, hier sollte geprüft werden, welches Datum hier raus kommt
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(filteredDatestart);
  /*      int day= gc.get(Calendar.DAY_OF_WEEK);
        if(day== Calendar.SUNDAY){
            gc.add(Calendar.DAY_OF_MONTH, 0);
        } else{*/
        gc.add(Calendar.HOUR_OF_DAY,1);
        // }
        //
        this.filteredDatestart= gc.getTime();// auch hier schauen, welches Datum raus komme

    }
    public void filterDateEndDate(SelectEvent event) {
        filterDateEnd = (Date) event.getObject(); //die AUswahl stimmt nciht, Datum ist nicht correct, hier sollte geprüft werden, welches Datum hier raus kommt
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(filterDateEnd);
  /*      int day= gc.get(Calendar.DAY_OF_WEEK);
        if(day== Calendar.SUNDAY){
            gc.add(Calendar.DAY_OF_MONTH, 0);
        } else{*/
        gc.add(Calendar.HOUR_OF_DAY,23);
        // }
        //
        this.filterDateEnd= gc.getTime();// auch hier schauen, welches Datum raus komme

    }


    public void setUrlaubList(List<Urlaub> urlaubList) {
        this.urlaubList = urlaubList;
    }

    public List<Arbeitszeit> getArbeitszeitList() {


        arbeitszeitList = new ArrayList<Arbeitszeit>();
        arbeitszeitList.addAll(SQLHelper.getArbeitszeiten(MitarbeiterID));
        if(filteredDatestart!=null){
            List<Arbeitszeit> tempList= new ArrayList<Arbeitszeit>();

            for(Arbeitszeit a:arbeitszeitList){
                if(!a.getArbeitsstart().before(filteredDatestart)&& !a.getArbeitsstart().after(filterDateEnd)){
                    tempList.add(a);
                }
            }
            arbeitszeitList=new ArrayList<Arbeitszeit>();
            arbeitszeitList.addAll(tempList);
            tempList=null;
        }
        Collections.sort(arbeitszeitList, new Comparator<Arbeitszeit>() {
            public int compare(Arbeitszeit o1, Arbeitszeit o2) {
                return o2.getArbeitsstart().compareTo(o1.getArbeitsstart());
            }
        });



        return arbeitszeitList;
    }
    public void setArbeitsdauer(String arbeitsdauer){
        this.arbeitsdauer=arbeitsdauer;
    }
    public String getArbeitsdauer() {
        int zeit=0;
        for(Arbeitszeit az: getArbeitszeitList()){
            int tempz=(int)( (az.getArbeitsende().getTime() - az.getArbeitsstart().getTime()) / (1000));
            zeit=zeit+tempz;
        }
        int std=(int)zeit/3600;
        int min=(int)(zeit-std*3600)/60;
        arbeitsdauer=std+":"+min;

        return arbeitsdauer;
    }

    public void setArbeitszeitList(List<Arbeitszeit> arbeitszeitList) {
        this.arbeitszeitList = arbeitszeitList;
    }

    public Mitarbeiter(String name, String farbe) {
        super();
        this.name= name;
        this.farbe= farbe;

    }
    @Override
    public String toString(){
        return this.getName()+" Passwort:  "+getPassword();
    }


    public List<String> getTest(){
        test = new ArrayList<String>();
        test.add("OH");
        test.add("beeeeee");
        test.add("uuuuuu");
        return test;


    }

    public String getArbeitszeiten() {
        FullCalendarEventList fk= new FullCalendarEventList();
        fk.getList().addAll(SQLHelper.getAllArbeitszeiten(MitarbeiterID));
        fk.getList().addAll(SQLHelper.getUrlaubszeiten(MitarbeiterID));
        if(fk.getList()!= null){
            arbeitszeiten=fk.toJson();
        } else{
            fk.getList().addAll(new ArrayList<FullCalendarEventBean>());
            arbeitszeiten=fk.toJson();
        }
        return arbeitszeiten;
    }


    public void setArbeitszeiten(String arbeitszeiten) {
        this.arbeitszeiten = arbeitszeiten;
    }

    public String getUrlaubszeiten() {
        FullCalendarEventList fk= new FullCalendarEventList();
        fk.getList().addAll(SQLHelper.getUrlaubszeiten(MitarbeiterID));
        if(fk.getList()!= null){
            urlaubszeiten=fk.toJson();
        } else{
            fk.getList().addAll(new ArrayList<FullCalendarEventBean>());
            urlaubszeiten=fk.toJson();
        }

        return urlaubszeiten;
    }

    public void setUrlaubszeiten(String urlaubszeiten) {
        this.urlaubszeiten = urlaubszeiten;
    }

    public int getMitarbeiterID() {
        return MitarbeiterID;
    }

    public void setMitarbeiterID(int mitarbeiterID) {
        MitarbeiterID = mitarbeiterID;
    }

    public String getName() {
        return name;
    }

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }

    public void setTest(List<String> test) {
        this.test = test;
    }

    public String getTem() {
        FullCalendarEventList fk= new FullCalendarEventList();
        fk.getList().addAll(SQLHelper.getAllArbeitszeiten(MitarbeiterID));
        fk.getList().addAll(SQLHelper.getSperrZeiten(MitarbeiterID));
        fk.getList().addAll( SQLHelper.getAllEvents(MitarbeiterID));
        fk.getList().addAll( SQLHelper.getUrlaubszeiten(MitarbeiterID));
        if(fk.getList()!=null){
            tem= fk.toJson();
        }else{
            fk.getList().addAll(new ArrayList<FullCalendarEventBean>());
            tem=fk.toJson();
        }
        return tem;
    }


    public void setTem(String tem) {
        this.tem = tem;
    }

    public FullCalendarEventList getCalendarList() {
        return calendarList;
    }

    public void setCalendarList(FullCalendarEventList calendarList) {
        this.calendarList = calendarList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Termine> getTermine() {
        this.termine = new ArrayList<Termine>();
        Termine t = new Termine();
        t.setTermininfo("dies ist ein");
        t.setUhrzeit(new Date());
        return termine;
    }

    public void setTermine(List<Termine> termine) {
        this.termine = termine;
    }

}
