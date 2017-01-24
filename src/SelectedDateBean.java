import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jutom on 24.01.2017.
 */
@ManagedBean
@RequestScoped
public class SelectedDateBean {
    private Date slectedDate;
    private Date shownDate;
    private KalenderHelfer kalenderHelfer;
    public Date getSlectedDate() {
        return slectedDate;
    }

    public void setSlectedDate(Date slectedDate) {
        this.slectedDate = slectedDate;
    }

    public Date getShownDate() {
        return shownDate;
    }

    public void setShownDate(Date shownDate) {
        this.shownDate = shownDate;
    }



}
