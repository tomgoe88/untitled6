import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Jutom on 23.02.2017.
 */
@ManagedBean
@SessionScoped
public class KundenController {
    private int q=-1;
    private Kunde kunde;
    private List<Kunde> kundeList;

    public KundenController(){

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
        this.kundeList= new ArrayList<>();
        kundeList.addAll(SQLHelper.getKundeList());
        Collections.sort(kundeList, new Comparator<Kunde>() {
            public int compare(Kunde o1, Kunde o2) {
                return o2.getNachname().compareTo(o1.getNachname());
            }
        });
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
                    kunde= new Kunde();
                    kunde.setVorname("neuer Eintrag");
                    kunde.setNachname("neuer Eintrag");
                    kunde.setTelefonnummer("neuer Eintrag");
                    kunde.setEmail("neuer Eintrag");
                }
            }
        }
        else if(q==-1){
            kunde= new Kunde();
            kunde.setVorname("neuer Eintrag");
            kunde.setNachname("neuer Eintrag");
            kunde.setTelefonnummer("neuer Eintrag");
            kunde.setEmail("neuer Eintrag");
        }
        else {
            kunde=null;//getMitarbeiter().get(0);
        }
        System.out.print(q+"");
        return kunde;
    }
    public void updateKunde(){


        String tele =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("teleK");
        String email=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("emailInputK");
        String strassee =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("strasse");
        String plzz =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("emailplzInput");
        String ortt =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ort");
        SQLHelper.updateKunde(kunde.getKundeID(),strassee,plzz,ortt,tele,email);

    }
    public void newKunde(){
        String vorname =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("vorname");
        String nachname=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nachname");

        String tele =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("teleKNeu");
        String email=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("emailInputKNeu");
        String strassee =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("strasseNeu");
        String plzz =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("emailplzInputNeu");
        String ortt =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ortNeu");
        SQLHelper.neuerKunde(vorname,nachname,tele, email,strassee,plzz,ortt);

    }
}
