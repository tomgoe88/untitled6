import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jutom on 08.02.2017.
 */
@ViewScoped
@ManagedBean
public class PageChanger {
    private String page;
    private String ifMobile;
    private String pageIndex;
    private String pageMitarbeiter;
    private String pageArbeitszeiten;
    private String pageHauptseite;
    private String pageUrlaubszeiten;
    private String pageKursplaner;
    private static boolean tempBool= false;
    private boolean passwordBool;
    private Mitarbeiter angemeldet;
    private  static Mitarbeiter tempMitarbeiter;
    private List<Mitarbeiter> mitarbeiter;


    public String getIfMobile() {
        return ifMobile;
    }

    public void setIfMobile(String ifMobile) {
        this.ifMobile = ifMobile;
    }
    public void checkMobile(String mobile){
        this.ifMobile= mobile;
    }

    public String getPageArbeitszeiten(){
        if(ifMobile!=null){
            if(ifMobile.equalsIgnoreCase("true")|| ifMobile==null){
                page="ArbeitszeitenMobile";
            } else {
                page="Arbeitszeiten";
            }
        }
//        if(page==null){
//            page="Arbeitszeiten";
//        }
        return "/"+page+".xhtml";
    }

    public String getPageMitarbeiter() {
        page= "mitarbeiter";
        return "/"+page+".xhtml";

    }

    public static boolean isTempBool() {
        return tempBool;
    }

    public static void setTempBool(boolean tempBool) {
        PageChanger.tempBool = tempBool;
    }

    public boolean isPasswordBool() {
        passwordBool= tempBool;
        return passwordBool;
    }

    public void setPasswordBool(boolean passwordBool) {
        this.passwordBool = passwordBool;
    }

    public Mitarbeiter getAngemeldet() {
        angemeldet=tempMitarbeiter;
        return angemeldet;
    }

    public void setAngemeldet(Mitarbeiter angemeldet) {
        this.angemeldet = angemeldet;
    }

    public static Mitarbeiter getTempMitarbeiter() {
        return tempMitarbeiter;
    }

    public static void setTempMitarbeiter(Mitarbeiter tempMitarbeiter) {
        PageChanger.tempMitarbeiter = tempMitarbeiter;
    }

    public List<Mitarbeiter> getMitarbeiter() {
        //hier muss eine Select-Abfrage für die Mitarbeiter an diesem Tag gemacht werden
        mitarbeiter = new ArrayList<Mitarbeiter>();
        mitarbeiter.addAll(SQLHelper.getMitarbeiterListe());
        return mitarbeiter;
    }
    public String checkPassword(){
        String login=null;
        String vorname = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("benutzername");
        String password =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("secret");
        for(Mitarbeiter m:getMitarbeiter()){
            if(m.getName().equalsIgnoreCase(vorname)){
                String temp= m.getPassword();
                if(temp.equalsIgnoreCase("null")){
                    tempBool=true;
                    login="login";
                    break;
                } else {
                    if(m.getPassword().equalsIgnoreCase(password)){
                        tempMitarbeiter= m;
                        login="hauptseite";
                        break;
                    }
                    else {
                        FacesContext.getCurrentInstance().addMessage("Achtung", new FacesMessage("Bitte geben sie das korrekte Passwort ein"));
                        login="login";
                        break;
                    }
                }
            }
        }
        return login;
    }


        public String getPageIndex(){
        if(ifMobile!=null){
            if(ifMobile.equalsIgnoreCase("true")|| ifMobile==null){
                page="indexMobile";
            } else {
                page="index";
            }
        }

//            if(page==null){
//                page="index";
//            }
            return "/"+page+".xhtml";
        }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageHauptseite(){
        page= "hauptseite";
        return "/"+page+".xhtml";
    }

    public String getPageUrlaubszeiten(){
        page= "Urlaubszeiten";
        return "/"+page+".xhtml";
    }

    public String getPageKursplaner(){
        page= "Kursplaner";
        return "/"+page+".xhtml";
    }

    public String getPage() {

        return "/"+page+".xhtml";
    }
    public String updatePassword(){
        String login="login";
        String vorname = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("loginname");
        String password =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("loginKennwort");
        for(Mitarbeiter m: getMitarbeiter()){
            if(m.getName().equalsIgnoreCase(vorname)){
                SQLHelper.updatePassword(m.getMitarbeiterID(),password);
                tempBool=false;
                login="hauptseite";
                break;
            }
        }
        return login;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
