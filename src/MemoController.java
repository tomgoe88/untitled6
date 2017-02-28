import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jutom on 28.02.2017.
 */
@ManagedBean
@SessionScoped
public class MemoController {
    private List<Memo> memoList;
    private int q=0;
    private Mitarbeiter mitarbeiter;
    private List<Mitarbeiter> mitarbeiterList;

    public MemoController(){
        mitarbeiterList= new ArrayList<Mitarbeiter>();
    }


    public List<Memo> getMemoList() {
        memoList= new ArrayList<Memo>();
        memoList.addAll(SQLHelper.getMemo());
        return memoList;
    }

    public void setMemoList(List<Memo> memoList) {
        this.memoList = memoList;
    }
    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }



    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public List<Mitarbeiter> getMitarbeiterList() {

        mitarbeiterList.addAll(SQLHelper.getMitarbeiterListe());
        return mitarbeiterList;
    }




    public Mitarbeiter getMitarbeiter() {
        if(q != 0){
            for(Mitarbeiter m: getMitarbeiterList()){
                //  if(m.getName().equalsIgnoreCase(q)){
                if(m.getMitarbeiterID()==q){
                    mitarbeiter= m;
                    break;
                }
                else {
                    mitarbeiter=null;

                }
            }
        } else {
            mitarbeiter=null;//getMitarbeiter().get(0);
        }
        return mitarbeiter;
    }

    public void neueMemo(){
        String besschreibung = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("memobeschreibung");
        Date date= new Date();
        SQLHelper.newMemo(besschreibung,date.toString(),q);
    }
}
