import org.primefaces.event.SelectEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.*;

/**
 * Created by Jutom on 27.01.2017.
 */
@ManagedBean
@ViewScoped
public class Kunde {
   private int KundeID;
    private String vorname;
    private String nachname;
    private String telefonnummer;
    private String email;
    private String strasse;
    private String plz;
    private String ort;
    private Date staticStart;
    private Date staticEnd;

    private List<ErledigteTermine> termineList;


    public int getKundeID() {
        return KundeID;
    }

    public void setKundeID(int kundeID) {
        KundeID = kundeID;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }
    public void setTermineList(List<ErledigteTermine> termineList) {
        this.termineList = termineList;
    }

    public Date getStaticStart() {
        return staticStart;
    }

    public void setStaticStart(Date staticStart) {
        this.staticStart = staticStart;
    }

    public Date getStaticEnd() {
        return staticEnd;
    }

    public void setStaticEnd(Date staticEnd) {
        this.staticEnd = staticEnd;
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

    public List<ErledigteTermine> getTermineList() {
        termineList = new ArrayList<ErledigteTermine>();
        termineList.addAll(SQLHelper.getKundeTermine(KundeID));
        List<ErledigteTermine> tempList= new ArrayList<ErledigteTermine>();

            if(this.staticStart!=null) {
                if (staticEnd == null) {
                    staticEnd = new Date();
                }

                for (ErledigteTermine a : termineList) {
                    if (!a.getTerminstart().before(this.staticStart) && !a.getTerminstart().after(staticEnd)) {
                        tempList.add(a);
                    }
                }
                termineList = new ArrayList<ErledigteTermine>();
                termineList.addAll(tempList);
                tempList = null;
            }


        Collections.sort(termineList, new Comparator<Termine>() {
            public int compare(Termine o1, Termine o2) {
                return o2.getTerminstart().compareTo(o1.getTerminstart());
            }
        });

        return termineList;
    }




    @Override
    public String toString(){
return this.getEmail()+" "+this.getOrt();    }

}
