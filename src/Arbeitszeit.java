import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Date;

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
}
