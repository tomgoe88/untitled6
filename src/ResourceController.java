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
    private String arbeitszeiten;
    private String kurse;
    private String resources;
    private String ausfallzeiten;

    public String getEvents() {
        FullCalendarEventList fl= new FullCalendarEventList();
        fl.getList().addAll(SQLHelper.getAllArbeitszeitenRes());
        fl.getList().addAll(SQLHelper.getAllEventsRes());
        fl.getList().addAll(SQLHelper.getallFreieTermineRes());
        fl.getList().addAll(SQLHelper.getUniRes());
        fl.getList().addAll(SQLHelper.getKrankheitszeitenRes());
        fl.getList().addAll(SQLHelper.getKurszeitenMitarbeiterRes());
        fl.getList().addAll(SQLHelper.getUrlaubszeitenRes());
        fl.getList().addAll(SQLHelper.getAusgleichtageRes());
        if(fl.getList().size()==0){
            fl.getList().addAll(new ArrayList<FullCalendarEventBean>());
            events=fl.toJson();
        } else {
            events=fl.toJson();
        }
        return events;
    }

    public String getAusfallzeiten() {
        FullCalendarEventList fl= new FullCalendarEventList();
        fl.getList().addAll(SQLHelper.getPlanerAllArbeitszeitenRes());
        fl.getList().addAll(SQLHelper.getUniRes());
        fl.getList().addAll(SQLHelper.getKrankheitszeitenRes());
        fl.getList().addAll(SQLHelper.getKurszeitenMitarbeiterRes());
        fl.getList().addAll(SQLHelper.getUrlaubszeitenRes());
        fl.getList().addAll(SQLHelper.getAusgleichtageRes());
        if(fl.getList().size()==0){
            fl.getList().addAll(new ArrayList<FullCalendarEventBean>());
            ausfallzeiten=fl.toJson();
        } else {
            ausfallzeiten=fl.toJson();
        }
        return ausfallzeiten;
    }

    public void setAusfallzeiten(String ausfallzeiten) {
        this.ausfallzeiten = ausfallzeiten;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getArbeitszeiten() {
        FullCalendarEventList fl= new FullCalendarEventList();
        fl.getList().addAll(SQLHelper.getPlanerAllArbeitszeitenRes());
        fl.getList().addAll(SQLHelper.getUniRes());
        fl.getList().addAll(SQLHelper.getKrankheitszeitenRes());
        fl.getList().addAll(SQLHelper.getUrlaubszeitenRes());
        fl.getList().addAll(SQLHelper.getAusgleichtageRes());
        if(fl.getList().size()==0){
            fl.getList().addAll(new ArrayList<FullCalendarEventBean>());
            arbeitszeiten=fl.toJson();
        } else {
            arbeitszeiten=fl.toJson();
        }
        return arbeitszeiten;
    }

    public void setArbeitszeiten(String arbeitszeiten) {
        this.arbeitszeiten = arbeitszeiten;
    }

    public String getKurse() {
        FullCalendarEventList fl= new FullCalendarEventList();
        fl.getList().addAll(SQLHelper.getKurszeitenMitarbeiterRes());
        fl.getList().addAll(SQLHelper.getAllArbeitszeitenRes());
        fl.getList().addAll(SQLHelper.getAllEventsRes());
        if(fl.getList().size()==0){
            fl.getList().addAll(new ArrayList<FullCalendarEventBean>());
            kurse=fl.toJson();
        } else {
            kurse=fl.toJson();
        }
        return kurse;
    }

    public void setKurse(String kurse) {
        this.kurse = kurse;
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
