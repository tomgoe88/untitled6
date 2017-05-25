import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * Created by Jutom on 25.05.2017.
 */
@ManagedBean
@ViewScoped
public class ArbeitszeitChangeCorntroller extends EventChangeController {


    @Override
    public void getTerminId() {
        String title = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("initialValue");
        String[] terminspilt= title.split(" ; ");




            super.setId(Integer.parseInt(terminspilt[0]));

    }

    @Override
    public void changeEventComplete() {
        SQLHelper.updateArbeitszeitEvent(super.getDateChange(),super.getEndDateChange(),super.getResourceChange(),super.getId());
    }
}
