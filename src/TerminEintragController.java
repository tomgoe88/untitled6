import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * Created by Jutom on 12.04.2017.
 */
@ManagedBean
@ViewScoped

public class TerminEintragController {
    private int wert =0;
    private String terminID;
    private String terminBezeichnung;

    public int getWert() {
        return wert;
    }

    public void setWert(int wert) {
        this.wert = wert;
    }

    public void wertNull(){
        wert=0;
    }

    public String getTerminID() {
        return terminID;
    }

    public void setTerminID(String terminID) {
        this.terminID = terminID;
    }

    public String getTerminBezeichnung() {
        return terminBezeichnung;
    }

    public void setTerminBezeichnung(String terminBezeichnung) {
        this.terminBezeichnung = terminBezeichnung;
    }

    public void resourceGetEvent(){
        String texten = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("initialValue");
        String [] terminspilt= texten.split(" ; ");

        this.terminID=terminspilt[0];
        this.terminBezeichnung=terminspilt[1];

        System.out.println(texten);

    }
    public void deleteTermin(){
        if(terminBezeichnung.equals("Uni")){
            SQLHelper.deleteUni(Integer.parseInt(terminID));
        } else if(terminBezeichnung.equals("Krank")){
            SQLHelper.deleteKrank(Integer.parseInt(terminID));
        } else if(terminBezeichnung.equals("Ausgleichtag")) {
            SQLHelper.deleteAusgleichtag(Integer.parseInt(terminID));
        } else {
            SQLHelper.deleteUrlaub(Integer.parseInt(terminID));
        }
    }
}
