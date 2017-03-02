import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Created by Jutom on 02.03.2017.
 */
@ManagedBean
@SessionScoped
public class PageResizer {
    private boolean plusMinus;
    private int size=940;

    public boolean isPlusMinus() {
        return plusMinus;
    }

    public void setPlusMinus(boolean plusMinus) {
        this.plusMinus = plusMinus;
    }

    public int getSize() {

        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public void changingPlus(){

            size=940;

    }
    public void changingMinus(){

        size=10;

    }

}
