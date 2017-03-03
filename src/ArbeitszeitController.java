import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;

/**
 * Created by Jutom on 03.03.2017.
 */
@ManagedBean
@SessionScoped
public class ArbeitszeitController {
    private List<Arbeitszeit> arbeitszeitList;
    private List<Mitarbeiter> mitarbeiterList;
private String fruehSchicht;
private String spaetSchicht;
private  String mittelSchicht;
private String wochendendschicht;
private static Date defaultDate;

    public List<Arbeitszeit> getArbeitszeitList() {
        Date start;
        Date end;
        if(defaultDate==null){
            defaultDate=new Date();
        }
        arbeitszeitList= new ArrayList<Arbeitszeit>();
        List<Arbeitszeit> tempList= new ArrayList<Arbeitszeit>();
        tempList.addAll(SQLHelper.getArbeitszeiten());
        GregorianCalendar sta= new GregorianCalendar();
        sta.setTime(defaultDate);
        sta.set(Calendar.HOUR_OF_DAY,1);
        start= sta.getTime();
        GregorianCalendar en= new GregorianCalendar();
        en.setTime(defaultDate);
        en.set(Calendar.HOUR_OF_DAY,23);
        end= en.getTime();

        for(Arbeitszeit a:tempList){
            if(!a.getArbeitsstart().before(start)&& !a.getArbeitsstart().after(end)){
                arbeitszeitList.add(a);
            }
        }
        System.out.println(defaultDate);
        return arbeitszeitList;
    }

    public void setArbeitszeitList(List<Arbeitszeit> arbeitszeitList) {


        this.arbeitszeitList = arbeitszeitList;
    }

    public List<Mitarbeiter> getMitarbeiterList() {
        return mitarbeiterList;
    }

    public static Date getDefaultDate() {
        return defaultDate;
    }

    public static void setDefaultDate(Date defaultDate) {
        ArbeitszeitController.defaultDate = defaultDate;
    }

    public void setMitarbeiterList(List<Mitarbeiter> mitarbeiterList) {
        this.mitarbeiterList = mitarbeiterList;
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
