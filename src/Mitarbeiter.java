/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import net.bootsfaces.component.fullCalendar.FullCalendar;
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
    private List<Krankheit> krankheitslist;
    private FullCalendarEventList calendarList;
    private int MitarbeiterID;
    private String arbeitszeiten;
    private String urlaubszeiten;
    private String password;
    private boolean admin;
    private Date staticStart=null;
    private Date staticEnd=null;
    private Date urlabuStart=null;
    private Date urlaubEnd=null;
    private List<Urlaub> urlaubList;
    private List<Arbeitszeit> arbeitszeitList;
    private static Date filteredDatestart;
    private static Date filterDateEnd;
    private static Date filterdUrlaubStart;
    private static Date filteredUrlaubEnd;
    private String arbeitsdauer;
    private static String tempArbeitsdauer;
    private String arbeitdauer;
    private String spaetSchicht;
    private String fruehSchicht;
    private String mittelSchicht;
    private String wochendendschicht;
    private String krankheitstage;
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

    public Date getUrlabuStart() {
        return urlabuStart;
    }

    public void setUrlabuStart(Date urlabuStart) {
        this.urlabuStart = urlabuStart;
    }

    public Date getUrlaubEnd() {
        return urlaubEnd;
    }

    public void setUrlaubEnd(Date urlaubEnd) {
        this.urlaubEnd = urlaubEnd;
    }

    public static Date getFilterdUrlaubStart() {
        return filterdUrlaubStart;
    }

    public static void setFilterdUrlaubStart(Date filterdUrlaubStart) {
        Mitarbeiter.filterdUrlaubStart = filterdUrlaubStart;
    }

    public static Date getFilteredUrlaubEnd() {
        return filteredUrlaubEnd;
    }

    public static void setFilteredUrlaubEnd(Date filteredUrlaubEnd) {
        Mitarbeiter.filteredUrlaubEnd = filteredUrlaubEnd;
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

    public  Date getStaticStart() {
        return staticStart;
    }

    public  void setStaticStart(Date staticStart) {
        this.staticStart = staticStart;
    }

    public Date getStaticEnd() {
        return staticEnd;
    }

    public  void setStaticEnd(Date staticEnd) {
        this.staticEnd = staticEnd;
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
        List<Urlaub> tempList= new ArrayList<Urlaub>();
        if(urlabuStart==null){
            if(filterdUrlaubStart!=null){
                if (filteredUrlaubEnd == null) {
                    filteredUrlaubEnd=new Date();
                }
                for(Urlaub a:urlaubList){
                    if(!a.getUrlaubBeginn().before(filterdUrlaubStart)&& !a.getUrlaubBeginn().after(filteredUrlaubEnd)){
                        tempList.add(a);
                    }
                }
                urlaubList=new ArrayList<Urlaub>();
                urlaubList.addAll(tempList);
                tempList=null;
            }
        } else {
            if (urlaubEnd == null) {
                urlaubEnd=new Date();
            }
            for(Urlaub a:urlaubList){
                if(!a.getUrlaubBeginn().before(urlabuStart)&& !a.getUrlaubBeginn().after(urlaubEnd)){
                    tempList.add(a);
                }
            }
            urlaubList=new ArrayList<Urlaub>();
            urlaubList.addAll(tempList);
            tempList=null;
        }


        Collections.sort(urlaubList, new Comparator<Urlaub>() {
            public int compare(Urlaub o1, Urlaub o2) {
                return o2.getUrlaubBeginn().compareTo(o1.getUrlaubBeginn());
            }
        });




        return urlaubList;
    }
    public List<Krankheit> getKrankheitslist() {
        krankheitslist= new ArrayList<Krankheit>();
        krankheitslist.addAll(SQLHelper.getKranklist(MitarbeiterID));
        List<Krankheit> tempList= new ArrayList<Krankheit>();
        if(urlabuStart==null){
            if(filterdUrlaubStart!=null){
                if (filteredUrlaubEnd == null) {
                    filteredUrlaubEnd=new Date();
                }
                for(Krankheit a:krankheitslist){
                    if(!a.getUrlaubBeginn().before(filterdUrlaubStart)&& !a.getUrlaubBeginn().after(filteredUrlaubEnd)){
                        tempList.add(a);
                    }
                }
                krankheitslist=new ArrayList<Krankheit>();
                krankheitslist.addAll(tempList);
                tempList=null;
            }
        } else {
            if (urlaubEnd == null) {
                urlaubEnd=new Date();
            }
            for(Krankheit a:krankheitslist){
                if(!a.getUrlaubBeginn().before(urlabuStart)&& !a.getUrlaubBeginn().after(urlaubEnd)){
                    tempList.add(a);
                }
            }
            krankheitslist=new ArrayList<Krankheit>();
            krankheitslist.addAll(tempList);
            tempList=null;
        }


        Collections.sort(krankheitslist, new Comparator<Krankheit>() {
            public int compare(Krankheit o1, Krankheit o2) {
                return o2.getUrlaubBeginn().compareTo(o1.getUrlaubBeginn());
            }
        });




        return krankheitslist;
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
    public void filterDateStartDateUrlaubStatic(SelectEvent event) {
        filterdUrlaubStart = (Date) event.getObject(); //die AUswahl stimmt nciht, Datum ist nicht correct, hier sollte geprüft werden, welches Datum hier raus kommt
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(filterdUrlaubStart);
  /*      int day= gc.get(Calendar.DAY_OF_WEEK);
        if(day== Calendar.SUNDAY){
            gc.add(Calendar.DAY_OF_MONTH, 0);
        } else{*/
        gc.add(Calendar.HOUR_OF_DAY,1);
        // }
        //
        this.filterdUrlaubStart= gc.getTime();// auch hier schauen, welches Datum raus komme

    }
    public void filterDateEndDateUrlaubStatic(SelectEvent event) {
        filteredUrlaubEnd = (Date) event.getObject(); //die AUswahl stimmt nciht, Datum ist nicht correct, hier sollte geprüft werden, welches Datum hier raus kommt
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(filteredUrlaubEnd);
  /*      int day= gc.get(Calendar.DAY_OF_WEEK);
        if(day== Calendar.SUNDAY){
            gc.add(Calendar.DAY_OF_MONTH, 0);
        } else{*/
        gc.add(Calendar.HOUR_OF_DAY,23);
        // }
        //
        this.filteredUrlaubEnd= gc.getTime();// auch hier schauen, welches Datum raus komme

    }
    public void filterDateStartDateMA(SelectEvent event) {
        staticStart = (Date) event.getObject(); //die AUswahl stimmt nciht, Datum ist nicht correct, hier sollte geprüft werden, welches Datum hier raus kommt
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(staticStart);
  /*      int day= gc.get(Calendar.DAY_OF_WEEK);
        if(day== Calendar.SUNDAY){
            gc.add(Calendar.DAY_OF_MONTH, 0);
        } else{*/
        gc.add(Calendar.HOUR_OF_DAY,1);
        // }
        //
        this.staticStart= gc.getTime();// auch hier schauen, welches Datum raus komme

    }
    public void filterDateEndDateMA(SelectEvent event) {
        staticEnd = (Date) event.getObject(); //die AUswahl stimmt nciht, Datum ist nicht correct, hier sollte geprüft werden, welches Datum hier raus kommt
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(staticEnd);
  /*      int day= gc.get(Calendar.DAY_OF_WEEK);
        if(day== Calendar.SUNDAY){
            gc.add(Calendar.DAY_OF_MONTH, 0);
        } else{*/
        gc.add(Calendar.HOUR_OF_DAY,23);
        // }
        //
        this.staticEnd= gc.getTime();// auch hier schauen, welches Datum raus komme

    }

    public void filterDateStartDateUrlaub(SelectEvent event) {
        urlabuStart = (Date) event.getObject(); //die AUswahl stimmt nciht, Datum ist nicht correct, hier sollte geprüft werden, welches Datum hier raus kommt
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(urlabuStart);
  /*      int day= gc.get(Calendar.DAY_OF_WEEK);
        if(day== Calendar.SUNDAY){
            gc.add(Calendar.DAY_OF_MONTH, 0);
        } else{*/
        gc.add(Calendar.HOUR_OF_DAY,1);
        // }
        //
        this.urlabuStart= gc.getTime();// auch hier schauen, welches Datum raus komme

    }
    public void filterDateEndDateUrlaub(SelectEvent event) {
        urlaubEnd = (Date) event.getObject(); //die AUswahl stimmt nciht, Datum ist nicht correct, hier sollte geprüft werden, welches Datum hier raus kommt
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(urlaubEnd);
  /*      int day= gc.get(Calendar.DAY_OF_WEEK);
        if(day== Calendar.SUNDAY){
            gc.add(Calendar.DAY_OF_MONTH, 0);
        } else{*/
        gc.add(Calendar.HOUR_OF_DAY,23);
        // }
        //
        this.urlaubEnd= gc.getTime();// auch hier schauen, welches Datum raus komme

    }


    public void setUrlaubList(List<Urlaub> urlaubList) {
        this.urlaubList = urlaubList;
    }

    public List<Arbeitszeit> getArbeitszeitList() {


        arbeitszeitList = new ArrayList<Arbeitszeit>();
        arbeitszeitList.addAll(SQLHelper.getArbeitszeiten(MitarbeiterID));
        List<Arbeitszeit> tempList= new ArrayList<Arbeitszeit>();
        if(staticStart==null){
            if(filteredDatestart!=null){
                if (filterDateEnd == null) {
                    filterDateEnd=new Date();
                }
                for(Arbeitszeit a:arbeitszeitList){
                    if(!a.getArbeitsstart().before(filteredDatestart)&& !a.getArbeitsstart().after(filterDateEnd)){
                        tempList.add(a);
                    }
                }
                arbeitszeitList=new ArrayList<Arbeitszeit>();
                arbeitszeitList.addAll(tempList);
                tempList=null;
            }
        } else {
            if (staticEnd == null) {
                staticEnd=new Date();
            }
            for(Arbeitszeit a:arbeitszeitList){
                if(!a.getArbeitsstart().before(staticStart)&& !a.getArbeitsstart().after(staticEnd)){
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
    public String getKrankheitstage() {
        FullCalendarEventList fk= new FullCalendarEventList();
        fk.getList().addAll(SQLHelper.getKrankheitszeiten(MitarbeiterID));
        if(fk.getList()!= null){
            krankheitstage=fk.toJson();
        } else{
            fk.getList().addAll(new ArrayList<FullCalendarEventBean>());
            krankheitstage=fk.toJson();
        }

        return krankheitstage;
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
        fk.getList().addAll(SQLHelper.getKrankheitszeiten(MitarbeiterID));
        fk.getList().addAll(SQLHelper.getKurse());
        if(fk.getList().size()==0){
            fk.getList().add(new FullCalendarEventBean());
            tem=fk.toJson();
        } else {
            tem=fk.toJson();
        }
        System.out.print(tem);
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



    public void setTermine(List<Termine> termine) {
        this.termine = termine;
    }

    public String getSpaetSchicht() {
        int i=0;
        spaetSchicht="Spätschicht";
        for(Arbeitszeit a:getArbeitszeitList()){
            if(a.getSchichtart().equals(spaetSchicht)){
                i++;
            }
        }
        return ""+i;
    }

    public void setSpaetSchicht(String spaetSchicht) {
        this.spaetSchicht = spaetSchicht;
    }

    public String getFruehSchicht() {

        int i=0;
        spaetSchicht="Frühschicht";
        for(Arbeitszeit a:getArbeitszeitList()){
            if(a.getSchichtart().equals(spaetSchicht)){
                i++;
            }
        }
        return ""+i;

    }

    public void setFruehSchicht(String fruehSchicht) {
        this.fruehSchicht = fruehSchicht;
    }

    public String getMittelSchicht() {

        int i=0;
        spaetSchicht="Mittelschicht";
        for(Arbeitszeit a:getArbeitszeitList()){
            if(a.getSchichtart().equals(spaetSchicht)){
                i++;
            }
        }
        return ""+i;
    }

    public void setMittelSchicht(String mittelSchicht) {
        this.mittelSchicht = mittelSchicht;
    }

    public String getWochendendschicht() {

        int i=0;
        spaetSchicht="Wochenendschicht";
        for(Arbeitszeit a:getArbeitszeitList()){
            if(a.getSchichtart().equals(spaetSchicht)){
                i++;
            }
        }
        return ""+i;
    }

    public void setWochendendschicht(String wochendendschicht) {
        this.wochendendschicht = wochendendschicht;
    }

}
