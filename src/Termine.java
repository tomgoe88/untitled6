/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Jutom
 */
@ManagedBean
@SessionScoped
public class Termine {

    private Date terminstart;
    private Date terminende;
    private String terminart;
    private String termindauer;

    /**
     * Creates a new instance of Termine
     */
    public Termine() {
    }


    public Date getTerminstart() {
        return terminstart;
    }

    public void setTerminstart(Date terminstart) {
        this.terminstart = terminstart;
    }

    public Date getTerminende() {
        return terminende;
    }

    public void setTerminende(Date terminende) {
        this.terminende = terminende;
    }

    public String getTerminart() {
        return terminart;
    }

    public void setTerminart(String terminart) {
        this.terminart = terminart;
    }

    public String getTermindauer() {
        int zeit= (int)( (terminende.getTime() - terminstart.getTime()) / (1000));
        int std=(int)zeit/3600;
        int min=(int)(zeit-std*3600)/60;

        termindauer=std+":"+min;
        return termindauer;
    }

    public void setTermindauer(String termindauer) {
        this.termindauer = termindauer;
    }
}


