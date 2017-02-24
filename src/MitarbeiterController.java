import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Created by Jutom on 24.02.2017.
 */
@ManagedBean
@SessionScoped
public class MitarbeiterController {
    private String newPassword;
    private String oldPassword;

    public void changePassword(){
        newPassword=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("neuP");
        oldPassword=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("altP");
        if(oldPassword.equals(SQLHelper.getPassword(PageChanger.getTempMitarbeiter().getMitarbeiterID()))){
            SQLHelper.updatePassword(PageChanger.getTempMitarbeiter().getMitarbeiterID(),newPassword);
            info();
        }
    }
    public void info() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Ihr Passwort wurde erfolgreich geändert"));
    }

}
