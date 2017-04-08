import org.primefaces.event.SelectEvent;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Jutom on 02.03.2017.
 */
@ManagedBean
@SessionScoped
public class KursController {
    private List<Kurse> kurseList;
    private List<Mitarbeiter> mitarbeiterList;
    private String kurplanKurse;
    private Mitarbeiter mitarbeit;
    private int q=0;
    private String kursbezeichnung;
    private List<String> kursbezeichnungen;
    private Date kursstart;
    private Date kursende;
    private String kursID;
    private String eingetragenerKurs;
    private String eingetrageneMA;
    private int kundenID;
    private String javaScriptDate;
    private KundenController kundenController;

    public KursController(){
        kundenController = new KundenController();
    }


    public List<Kurse> getKurseList() {
        kurseList= new ArrayList<Kurse>();
        kurseList.addAll(SQLHelper.getKursList());
        return kurseList;
    }

    public void setKurseList(List<Kurse> kurseList) {
        this.kurseList = kurseList;
    }

    public int getKundenID() {
        return kundenID;
    }

    public void setKundenID(int kundenID) {
        this.kundenID = kundenID;
    }

    public KundenController getKundenController() {
        return kundenController;
    }

    public void setKundenController(KundenController kundenController) {
        this.kundenController = kundenController;
    }

    public List<Mitarbeiter> getMitarbeiterList() {
        mitarbeiterList = new ArrayList<Mitarbeiter>();
        mitarbeiterList.addAll(SQLHelper.getMitarbeiterListe());
        return mitarbeiterList;
    }

    public void setMitarbeiterList(List<Mitarbeiter> mitarbeiterList) {
        this.mitarbeiterList = mitarbeiterList;
    }

    public String getKurplanKurse() {
        String tem;
        FullCalendarEventList fk= new FullCalendarEventList();
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

    public void setKurplanKurse(String kurplanKurse) {
        this.kurplanKurse = kurplanKurse;
    }

    public String getKursID() {
        return kursID;
    }

    public void setKursID(String kursID) {
        this.kursID = kursID;
    }

    public String getEingetragenerKurs() {
        return eingetragenerKurs;
    }

    public void setEingetragenerKurs(String eingetragenerKurs) {
        this.eingetragenerKurs = eingetragenerKurs;
    }

    public String getEingetrageneMA() {
        return eingetrageneMA;
    }

    public void setEingetrageneMA(String eingetrageneMA) {
        this.eingetrageneMA = eingetrageneMA;
    }

    public void setMitarbeit(Mitarbeiter mitarbeit) {
        this.mitarbeit = mitarbeit;
    }

    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public String getKursbezeichnung() {
        return kursbezeichnung;
    }

    public void setKursbezeichnung(String kursbezeichnung) {
        this.kursbezeichnung = kursbezeichnung;
    }

    public List<String> getKursbezeichnungen() {
        kursbezeichnungen= new ArrayList<String>();
        kursbezeichnungen.addAll(SQLHelper.getKursbezeichnungen());
        return kursbezeichnungen;
    }

    public void setKursbezeichnungen(List<String> kursbezeichnungen) {
        this.kursbezeichnungen = kursbezeichnungen;
    }

    public Mitarbeiter getMitarbeit() {
        if(q != 0){
            for(Mitarbeiter m: getMitarbeiterList()){
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
    public void newKurs(){

        SQLHelper.newKurs(kursbezeichnung,kursstart.toString(),kursende.toString(),q);
    }
    public void resourceGetMaId(){
        String resID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("initialValue");
        q= Integer.parseInt(resID);

        System.out.println(resID+"   So ist die ResId");
    }

    public Date getKursstart() {
        return kursstart;
    }

    public void setKursstart(Date kursstart) {
        this.kursstart = kursstart;
    }

    public Date getKursende() {
        return kursende;
    }

    public void setKursende(Date kursende) {
        this.kursende = kursende;
    }

    public void setKursEndeDate(SelectEvent event) {
        kursende = (Date) event.getObject(); //die AUswahl stimmt nciht, Datum ist nicht correct, hier sollte geprüft werden, welches Datum hier raus kommt
/*        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(kursende);
  *//*      int day= gc.get(Calendar.DAY_OF_WEEK);
        if(day== Calendar.SUNDAY){
            gc.add(Calendar.DAY_OF_MONTH, 0);
        } else{*//*
        gc.add(Calendar.HOUR_OF_DAY,1);
        // }
        //
        this.kursende= gc.getTime();// auch hier schauen, welches Datum raus komme*/

    }
    public void setKursstartDate(SelectEvent event) {
        kursstart = (Date) event.getObject(); //die AUswahl stimmt nciht, Datum ist nicht correct, hier sollte geprüft werden, welches Datum hier raus kommt
/*        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(kursstart);
  *//*      int day= gc.get(Calendar.DAY_OF_WEEK);
        if(day== Calendar.SUNDAY){
            gc.add(Calendar.DAY_OF_MONTH, 0);
        } else{*//*
        gc.add(Calendar.HOUR_OF_DAY,1);
        // }
        //
        this.kursstart= gc.getTime();// auch hier schauen, welches Datum raus komme*/

    }
    public void myKursDate(String date){
        Calendar calendar= javax.xml.bind.DatatypeConverter.parseDateTime(date);
        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ActionListener called",
                "Date: " + date);
        Calendar c2=javax.xml.bind.DatatypeConverter.parseDateTime(date);;
        c2.add(Calendar.HOUR_OF_DAY,1);
        kursstart= calendar.getTime();
        kursende=c2.getTime();
    }
    public void resGetKursDate(){
        String date = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("initialValue");
        Calendar calendar= javax.xml.bind.DatatypeConverter.parseDateTime(date);
        final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "ActionListener called",
                "Date: " + date);
        Calendar c2=javax.xml.bind.DatatypeConverter.parseDateTime(date);;
        c2.add(Calendar.HOUR_OF_DAY,1);
        kursstart= calendar.getTime();
        kursende=c2.getTime();

        System.out.println(kursstart+"   So ist das Start-Datum");
    }
    public void newKursbezeichnung(){
        String vorname = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("kursBZ");
        SQLHelper.newKursbezeichnung(vorname);
    }
    public void deleteKurs(String bezeichnung){
        SQLHelper.deleteKursbezeichnung(bezeichnung);
    }
    public void deleteKursTermin(){
        SQLHelper.deleteKurs(Integer.parseInt(kursID));
    }
    public void myEvent(String texten){

        String [] terminspilt= texten.split(" ; ");

        this.eingetragenerKurs=terminspilt[0];
        this.eingetrageneMA=terminspilt[1];
        this.kursID= terminspilt[2];

    }
    public void resourceGetEvent(){
        String texten = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("initialValue");
        String [] terminspilt= texten.split(" ; ");

        this.kursID=terminspilt[0];
        this.eingetragenerKurs=terminspilt[1];

        System.out.println(texten);

    }
    public void neuerTeilnehmer(){
        SQLHelper.neuerTeilnehmer(Integer.parseInt(kursID),kundenID);
    }
    public String getJavaScriptDate() {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        if(kursstart!=null){
            javaScriptDate = "'"+simpleDateFormat.format(kursstart)+"'";
        }else{
            javaScriptDate = "'"+simpleDateFormat.format(new Date())+"'";
        }


        //  javaScriptDate= buf.toString();
        System.out.println("JavaScriptDate=   "+ javaScriptDate);
        return javaScriptDate;
    }
}
