import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Jutom on 20.02.2017.
 */
@ManagedBean
@ViewScoped
public class Arbeitszeit {
    private int arbeitszeitID;
    private Date arbeitsstart;
    private Date arbeitsende;
    private int arbeitsdauer;


    public int getArbeitszeitID() {
        return arbeitszeitID;
    }

    public void setArbeitszeitID(int arbeitszeitID) {
        this.arbeitszeitID = arbeitszeitID;
    }

    public Date getArbeitsstart() {
        return arbeitsstart;
    }

    public void setArbeitsstart(Date arbeitsstart) {
        this.arbeitsstart = arbeitsstart;
    }

    public Date getArbeitsende() {
        return arbeitsende;
    }

    public void setArbeitsende(Date arbeitsende) {
        this.arbeitsende = arbeitsende;
    }

    public int getArbeitsdauer() {
        return arbeitsdauer;
    }

    public void setArbeitsdauer(int arbeitsdauer) {
        this.arbeitsdauer = arbeitsdauer;
    }


    //TODO funktionen in der Tabelle hinzufügen

    public void addStundeStart(){
        Calendar gc = Calendar.getInstance();
        gc.setTime(arbeitsstart);
        gc.add(Calendar.HOUR_OF_DAY, 1);
        arbeitsstart=gc.getTime();
    }
    public void addStundeEnde(){
        Calendar gc = Calendar.getInstance();
        gc.setTime(arbeitsende);
        gc.add(Calendar.HOUR_OF_DAY, 1);
        arbeitsende=gc.getTime();
    }
    public void deleteStundeStart(){
        Calendar gc = Calendar.getInstance();
        gc.setTime(arbeitsstart);
        gc.add(Calendar.HOUR_OF_DAY, -1);
        arbeitsstart=gc.getTime();
    }
    public void deleteStundeEnde(){
        Calendar gc = Calendar.getInstance();
        gc.setTime(arbeitsende);
        gc.add(Calendar.HOUR_OF_DAY, -1);
        arbeitsende=gc.getTime();
    }
    public void updateArbeitszeit(){
        SQLHelper.updatearbeitszeit(arbeitszeitID,arbeitsstart.toString(),arbeitsende.toString());
    }
}
