import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jutom on 02.03.2017.
 */
@ManagedBean
@SessionScoped
public class Kurse {
    private int KursID;
    private String kursbezeichnung;
    private String mitarbeiter;
    private Date kursstart;
    private Date kursende;
    private String teilnehmeranzahl;
    private List<Kunde> kundeList;


    public int getKursID() {
        return KursID;
    }

    public void setKursID(int kursID) {
        KursID = kursID;
    }

    public String getKursbezeichnung() {
        return kursbezeichnung;
    }

    public void setKursbezeichnung(String kursbezeichnung) {
        this.kursbezeichnung = kursbezeichnung;
    }

    public String getMitarbeiter() {
        return mitarbeiter;
    }

    public void setMitarbeiter(String mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public Date getKursstart() {
        return kursstart;
    }

    public void setKursstart(Date kursstart) {
        this.kursstart = kursstart;
    }

    public Date getKursende() {
        return kursende;
    }

    public void setKursende(Date kursende) {
        this.kursende = kursende;
    }

    public List<Kunde> getKundeList() {
        kundeList= new ArrayList<Kunde>();
        kundeList.addAll(SQLHelper.teilnehmerListe(KursID));
        return kundeList;
    }

    public void setKundeList(List<Kunde> kundeList) {
        this.kundeList = kundeList;
    }

    public String getTeilnehmeranzahl() {
        int anzahl=0;
        for(Kunde k:getKundeList()){
            anzahl++;
        }
        teilnehmeranzahl=anzahl+"";
        return teilnehmeranzahl;
    }

    public void setTeilnehmeranzahl(String teilnehmeranzahl) {
        this.teilnehmeranzahl = teilnehmeranzahl;
    }
}
