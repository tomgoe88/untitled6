import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Calendar;


/**
 * Created by Jutom on 22.05.2017.
 */
@ManagedBean
@ViewScoped
public class EventChangeController {
    private String dateChange;
    private String endDateChange;
    private int resourceChange;
    private int id;
    private boolean frei=false;
    private boolean kursIs=false;

    public String getDateChange() {
        return dateChange;
    }

    public void setDateChange(String dateChange) {
        this.dateChange = dateChange;
    }

    public String getEndDateChange() {
        return endDateChange;
    }

    public void setEndDateChange(String endDateChange) {
        this.endDateChange = endDateChange;
    }

    public boolean isFrei() {
        return frei;
    }

    public void setFrei(boolean frei) {
        this.frei = frei;
    }

    public int getResourceChange() {
        return resourceChange;
    }

    public void setResourceChange(int resourceChange) {
        this.resourceChange = resourceChange;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isKursIs() {
        return kursIs;
    }

    public void setKursIs(boolean kursIs) {
        this.kursIs = kursIs;
    }

    public void changeResourceId(){
        String resource = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("initialValue");
        resourceChange=Integer.parseInt(resource);
        System.out.println("Resource = "+resourceChange);
    }
    public void getTerminId(){
        String title = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("initialValue");
        String[] terminspilt= title.split(" ; ");
     if(terminspilt.length==3){


        this.id=Integer.parseInt(terminspilt[2]);
        frei=true;
     }  else if(terminspilt.length==2){
         this.id=Integer.parseInt(terminspilt[0]);
         kursIs=true;
        } else {
         id=Integer.parseInt(terminspilt[6]);
         System.out.println("Termin ID = "+id);
     }

    }
    public void changeEndDateEvent(){
        String date = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("initialValue");
        Calendar calendar= javax.xml.bind.DatatypeConverter.parseDateTime(date);
        endDateChange=calendar.getTime().toString();
        System.out.println("Termin ID = "+endDateChange);
    }
    public void changeDateEvent(){
        String date = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("initialValue");
        Calendar calendar= javax.xml.bind.DatatypeConverter.parseDateTime(date);
        dateChange=calendar.getTime().toString();

        System.out.println("Termin ID = "+dateChange);
        changeEventComplete();
    }
    public void changeEventComplete(){
        System.out.println("Funktion wird ausgeführt");
        if(frei==true){
            SQLHelper.updateFreierTerminEvent(dateChange, endDateChange,resourceChange,id);
            frei=false;

        } else if(kursIs==true){
           SQLHelper.updateKursEvent(dateChange, endDateChange,resourceChange,id);
           kursIs=false;
        } else {
            SQLHelper.updateTerminEvent(dateChange, endDateChange,resourceChange,id);
        }

    }
}
