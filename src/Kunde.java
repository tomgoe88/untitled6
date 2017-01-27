/**
 * Created by Jutom on 27.01.2017.
 */
public class Kunde {
    int KundeID;
    private String vorname;
    private String nachname;
    private String telefonnummer;


    public int getKundeID() {
        return KundeID;
    }

    public void setKundeID(int kundeID) {
        KundeID = kundeID;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }
}
