import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Created by Jutom on 08.02.2017.
 */
@ViewScoped
@ManagedBean
public class PageChanger {
    private String page;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
