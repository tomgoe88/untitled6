import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Date;

/**
 * Created by Jutom on 17.02.2017.
 */
@ManagedBean
@ViewScoped
public class Urlaub {
    private Date urlaubBeginn;
    private Date urlaubEnde;
    private int urlaubstage;

    public Date getUrlaubBeginn() {
        return urlaubBeginn;
    }

    public void setUrlaubBeginn(Date urlaubBeginn) {
        this.urlaubBeginn = urlaubBeginn;
    }

    public Date getUrlaubEnde() {
        return urlaubEnde;
    }

    public void setUrlaubEnde(Date urlaubEnde) {
        this.urlaubEnde = urlaubEnde;
    }

    public int getUrlaubstage() {
        return urlaubstage;
    }

    public void setUrlaubstage(int urlaubstage) {
        this.urlaubstage = urlaubstage;
    }
}
