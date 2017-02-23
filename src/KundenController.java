import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jutom on 23.02.2017.
 */
@ManagedBean
@SessionScoped
public class KundenController {
    private int q=0;
    private Kunde kunde;
    private List<Kunde> kundeList;

    public KundenController(){
        this.kundeList= new ArrayList<>();;
    }


    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }



    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public List<Kunde> getKundeList() {

        kundeList.addAll(SQLHelper.getKundeList());
        return kundeList;
    }

    public void setKundeList(List<Kunde> kundeList) {
        this.kundeList = kundeList;
    }



    public Kunde getKunde() {
        if(q != 0){
            for(Kunde m: getKundeList()){
                //  if(m.getName().equalsIgnoreCase(q)){
                if(m.getKundeID()==q){
                    kunde= m;
                    break;
                }
                else {
                    kunde=null;

                }
            }
        } else {
            kunde=null;//getMitarbeiter().get(0);
        }
        return kunde;
    }
}
