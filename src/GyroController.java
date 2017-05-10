import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Created by Jutom on 23.04.2017.
 */
@ManagedBean
@ViewScoped
public class GyroController {
    private int alpha;
    private int beta;
    private int gamma;
    private int winkel;
    private int tempTerm;
    private boolean ok=false;
    private int calls = 0;

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
        calls++;
    }

    public int getBeta() {
        return beta;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public int getGamma() {
        return gamma;
    }

    public void setGamma(int gamma) {
        this.gamma = gamma;
    }

    public int getTempTerm() {
        return tempTerm;
    }

    public void setTempTerm(int tempTerm) {
        this.tempTerm = tempTerm;
    }

    public int getWinkel() {
        if(ok==true){
            winkel= alpha-tempTerm;
        }else {
            winkel=0;
        }
        return winkel;
    }

    public void setWinkel(int winkel) {
        this.winkel = winkel;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void onRotation() {
        System.out.println("Rotate! " + getWinkel());
    }

    public int getCalls() {
        return calls;
    }

    public void setCalls(int calls) {
        this.calls = calls;
    }
    public void onAlphaClick(){
        ok=true;
        tempTerm=alpha;

    }

}
