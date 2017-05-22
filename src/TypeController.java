import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 * Created by Jutom on 22.05.2017.
 */
@ManagedBean
@ViewScoped
public class TypeController {
    private boolean teleType=false;

    public boolean isTeleType() {
        return teleType;
    }

    public void setTeleType(boolean teleType) {
        this.teleType = teleType;
    }

    public void testType(){

            teleType=true;

    }
}
