import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Date;

/**
 * Created by Jutom on 06.02.2017.
 */
@ManagedBean
@ViewScoped
public class Memo {
    private int memoID;
    private String mitarbeiterM;
    private String beschreibung;
    private Date eintragDatum;

    public int getMemoID() {
        return memoID;
    }

    public void setMemoID(int memoID) {
        this.memoID = memoID;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getMitarbeiterM() {
        return mitarbeiterM;
    }

    public void setMitarbeiterM(String mitarbeiterM) {
        this.mitarbeiterM = mitarbeiterM;
    }

    public Date getEintragDatum() {
        return eintragDatum;
    }

    public void setEintragDatum(Date eintragDatum) {
        this.eintragDatum = eintragDatum;
    }
}
