/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Mitarbeiter(String name, String farbe) {
        super();
        this.name= name;
        this.farbe= farbe;

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
