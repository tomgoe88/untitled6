import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Jutom on 17.02.2017.
 */
@ManagedBean
@ViewScoped
public class Urlaub {
    private int urlaubsID;
    private Date urlaubBeginn;
    private Date urlaubEnde;
    private int urlaubstage;

    public int getUrlaubsID() {
        return urlaubsID;
    }

    public void setUrlaubsID(int urlaubsID) {
        this.urlaubsID = urlaubsID;
    }

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
        urlaubstage= (int)( (urlaubEnde.getTime() - urlaubBeginn.getTime()) / (1000 * 60 * 60 * 24))+1;
        Calendar start=Calendar.getInstance();
        start.setTime(urlaubBeginn);
        int i=0;
        int we=0;
        while(i<urlaubstage){
            i++;
            if(start.get(Calendar.DAY_OF_WEEK)== Calendar.SATURDAY || start.get(Calendar.DAY_OF_WEEK)== Calendar.SUNDAY ){
                we++;
            }
            start.add(Calendar.DAY_OF_WEEK,1);
            System.out.println(start.getTime().toString());
        }
        urlaubstage=urlaubstage-we;
        return urlaubstage;
    }

    public void setUrlaubstage(int urlaubstage) {
        this.urlaubstage = urlaubstage;
    }
}
