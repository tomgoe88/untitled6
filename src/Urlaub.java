import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Date;

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
        urlaubstage= (int)( (urlaubEnde.getTime() - urlaubBeginn.getTime()) / (1000 * 60 * 60 * 24));
        int i=0;
        int woche=0;
        while(i<urlaubstage){
            i++;
            woche++;
            if(woche==6){
                urlaubstage--;
            }
            else if (woche==7){
                urlaubstage--;
                woche=0;
            }

        }
        return urlaubstage;
    }

    public void setUrlaubstage(int urlaubstage) {
        this.urlaubstage = urlaubstage;
    }
}
