import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Created by Jutom on 08.02.2017.
 */
@ViewScoped
@ManagedBean
public class PageChanger {
    private String page;
    private String ifMobile ="true";
    private String pageIndex;
    private String pageMitarbeiter;
    private String pageArbeitszeiten;
    private String pageHauptseite;
    private String pageUrlaubszeiten;
    private String pageKursplaner;


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
        if(ifMobile.equalsIgnoreCase("true")|| ifMobile==null){
            page="ArbeitszeitenMobile";
        } else {
            page="Arbeitszeiten";
        }
        return "/"+page+".xhtml";
    }

    public String getPageMitarbeiter() {
        return "/"+page+".xhtml";

    }


        public String getPageIndex(){
            if(ifMobile.equalsIgnoreCase("true")|| ifMobile==null){
                page="indexMobile";
            } else {
                page="index";
            }
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

    public void setPage(String page) {
        this.page = page;
    }
}
