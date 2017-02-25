import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Created by Jutom on 25.02.2017.
 */
@ManagedBean
@SessionScoped
public class ErledigteTermine extends Termine{
    private int erldigtID;
    private String ergebnis;
    private String Hinweis;
    private String kundeName;
    private String mitarbeiterName;


    public int getErldigtID() {
        return erldigtID;
    }

    public void setErldigtID(int erldigtID) {
        this.erldigtID = erldigtID;
    }

    public String getErgebnis() {
        return ergebnis;
    }

    public void setErgebnis(String ergebnis) {
        this.ergebnis = ergebnis;
    }

    public String getHinweis() {
        return Hinweis;
    }

    public void setHinweis(String hinweis) {
        Hinweis = hinweis;
    }

    public String getKundeName() {
        return kundeName;
    }

    public void setKundeName(String kundeName) {
        this.kundeName = kundeName;
    }

    public String getMitarbeiterName() {
        return mitarbeiterName;
    }

    public void setMitarbeiterName(String mitarbeiterName) {
        this.mitarbeiterName = mitarbeiterName;
    }
}
