import org.primefaces.event.SelectEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.*;

/**
 * Created by Jutom on 25.02.2017.
 */
@ManagedBean
@SessionScoped
public class TerminController {
    private List<ErledigteTermine> termineList;
    private Date filteredDatestart;
    private Date filterDateEnd;

    public Date getFilteredDatestart() {
        return filteredDatestart;
    }

    public void setFilteredDatestart(Date filteredDatestart) {
        this.filteredDatestart = filteredDatestart;
    }

    public Date getFilterDateEnd() {
        return filterDateEnd;
    }

    public void setFilterDateEnd(Date filterDateEnd) {
        this.filterDateEnd = filterDateEnd;
    }

    public void filterDateStartDate(SelectEvent event) {
        filteredDatestart = (Date) event.getObject(); //die AUswahl stimmt nciht, Datum ist nicht correct, hier sollte geprüft werden, welches Datum hier raus kommt
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(filteredDatestart);
  /*      int day= gc.get(Calendar.DAY_OF_WEEK);
        if(day== Calendar.SUNDAY){
            gc.add(Calendar.DAY_OF_MONTH, 0);
        } else{*/
        gc.add(Calendar.HOUR_OF_DAY,1);
        // }
        //
        this.filteredDatestart= gc.getTime();// auch hier schauen, welches Datum raus komme

    }
    public void filterDateEndDate(SelectEvent event) {
        filterDateEnd = (Date) event.getObject(); //die AUswahl stimmt nciht, Datum ist nicht correct, hier sollte geprüft werden, welches Datum hier raus kommt
        GregorianCalendar gc= new GregorianCalendar();
        gc.setTime(filterDateEnd);
  /*      int day= gc.get(Calendar.DAY_OF_WEEK);
        if(day== Calendar.SUNDAY){
            gc.add(Calendar.DAY_OF_MONTH, 0);
        } else{*/
        gc.add(Calendar.HOUR_OF_DAY,23);
        // }
        //
        this.filterDateEnd= gc.getTime();// auch hier schauen, welches Datum raus komme

    }

    public List<ErledigteTermine> getTermineList() {
        termineList = new ArrayList<ErledigteTermine>();
        termineList.addAll(SQLHelper.getErledigteTermine());
        List<ErledigteTermine> tempList= new ArrayList<ErledigteTermine>();

        if(filteredDatestart!=null) {
            if (filterDateEnd == null) {
                filterDateEnd = new Date();
            }

            for (ErledigteTermine a : termineList) {
                if (!a.getTerminstart().before(filteredDatestart) && !a.getTerminstart().after(filterDateEnd)) {
                    tempList.add(a);
                }
            }
            termineList = new ArrayList<ErledigteTermine>();
            termineList.addAll(tempList);
            tempList = null;
        }


        Collections.sort(termineList, new Comparator<Termine>() {
            public int compare(Termine o1, Termine o2) {
                return o2.getTerminstart().compareTo(o1.getTerminstart());
            }
        });

        return termineList;
    }

}
