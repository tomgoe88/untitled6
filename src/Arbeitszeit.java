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
    private String arbeitsdauer;
    private Date filteredDate;


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

    public String getArbeitsdauer() {
        int zeit= (int)( (arbeitsende.getTime() - arbeitsstart.getTime()) / (1000));
        int std=(int)zeit/3600;
        int min=(int)(zeit-std*3600)/60;

        arbeitsdauer=std+":"+min;
        return arbeitsdauer;
    }

    public Date getFilteredDate() {
        return filteredDate;
    }

    public void setFilteredDate(Date filteredDate) {
        this.filteredDate = filteredDate;
    }

    public void setArbeitsdauer(String arbeitsdauer) {
        this.arbeitsdauer = arbeitsdauer;
    }


    //TODO funktionen in der Tabelle hinzufügen

    public void addStundeStart(){
        Calendar gc = Calendar.getInstance();
        gc.setTime(arbeitsstart);
        gc.add(Calendar.MINUTE, 10);
        arbeitsstart=gc.getTime();
        updateArbeitszeit();
    }
    public void addStundeEnde(){
        Calendar gc = Calendar.getInstance();
        gc.setTime(arbeitsende);
        gc.add(Calendar.MINUTE, 10);
        arbeitsende=gc.getTime();
        updateArbeitszeit();
    }
    public void deleteStundeStart(){
        Calendar gc = Calendar.getInstance();
        gc.setTime(arbeitsstart);
        gc.add(Calendar.MINUTE, -10);
        arbeitsstart=gc.getTime();
        updateArbeitszeit();
    }
    public void deleteStundeEnde(){
        Calendar gc = Calendar.getInstance();
        gc.setTime(arbeitsende);
        gc.add(Calendar.MINUTE, -10);
        arbeitsende=gc.getTime();
        updateArbeitszeit();
    }
    public void updateArbeitszeit(){
        SQLHelper.updatearbeitszeit(arbeitszeitID,arbeitsstart.toString(),arbeitsende.toString());
    }

}
