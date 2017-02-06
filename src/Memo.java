import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Created by Jutom on 06.02.2017.
 */
@ManagedBean
@ViewScoped
public class Memo {
    private int memoID;
    private String beschreibung;

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
}
