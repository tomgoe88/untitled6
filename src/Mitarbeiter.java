/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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

    public Mitarbeiter(){

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
        return fk.toJson();
    }

    public void setArbeitszeiten(String arbeitszeiten) {
        this.arbeitszeiten = arbeitszeiten;
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
        //TODO: hier wird alles über die Datenbank bezogen und dann befüllt
        //TODO: die liste wir hier mit allen Events, also auch den arbeitszeiten befüllt
        this.getList().addAll(SQLHelper.getAllArbeitszeiten(MitarbeiterID));
        this.getList().addAll( SQLHelper.getAllEvents(MitarbeiterID));
        tem= this.toJson();
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
