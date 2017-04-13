import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Created by Jutom on 12.04.2017.
 */
@ManagedBean
@ViewScoped

public class TerminEintragController {
    private int wert =0;

    public int getWert() {
        return wert;
    }

    public void setWert(int wert) {
        this.wert = wert;
    }

    public void wertNull(){
        wert=0;
    }
}
