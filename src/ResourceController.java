import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;

/**
 * Created by Jutom on 07.03.2017.
 */
@ManagedBean
@SessionScoped
public class ResourceController {
    private String events;
    private String resources;

    public String getEvents() {
        FullCalendarEventList fl= new FullCalendarEventList();
        fl.getList().addAll(SQLHelper.getAllArbeitszeitenRes());
        fl.getList().addAll(SQLHelper.getAllEventsRes());
        fl.getList().addAll(SQLHelper.getallFreieTermineRes());
        fl.getList().addAll(SQLHelper.getUniRes());
        fl.getList().addAll(SQLHelper.getKrankheitszeitenRes());
        fl.getList().addAll(SQLHelper.getKurszeitenMitarbeiterRes());
        fl.getList().addAll(SQLHelper.getUrlaubszeitenRes());
        if(fl.getList().size()==0){
            fl.getList().addAll(new ArrayList<FullCalendarEventBean>());
            events=fl.toJson();
        } else {
            events=fl.toJson();
        }
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getResources() {
        FullCalendarRessourceList fr= new FullCalendarRessourceList();
        fr.getList().addAll(SQLHelper.getAllResources());
        if(fr.getList().size()==0){
            fr.getList().addAll(new ArrayList<FullcalendarRessouceBean>());
            resources=fr.toJson();
        } else {
            resources=fr.toJson();
        }
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }
}
