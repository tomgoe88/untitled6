import org.primefaces.event.SelectEvent;

import java.util.*;

/**
 * Created by Jutom on 27.01.2017.
 */
public class Kunde {
    int KundeID;
    private String vorname;
    private String nachname;
    private String telefonnummer;
    private Date staticStart=null;
    private Date staticEnd=null;
    private List<Termine> termineList;


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

   public List<Termine> getTermineList() {
        termineList = new ArrayList<Termine>();
        termineList.addAll(SQLHelper.getKundeTermine(KundeID));
        List<Termine> tempList= new ArrayList<Termine>();

            if(staticStart!=null) {
                if (staticEnd == null) {
                    staticEnd = new Date();
                }

                for (Termine a : termineList) {
                    if (!a.getTerminstart().before(staticStart) && !a.getTerminstart().after(staticEnd)) {
                        tempList.add(a);
                    }
                }
                termineList = new ArrayList<Termine>();
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

    public void setTermineList(List<Termine> termineList) {
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
}
