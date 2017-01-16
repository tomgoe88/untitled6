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

    private Date uhrzeit;
    private String termininfo;
    /**
     * Creates a new instance of Termine
     */
    public Termine() {
    }



    public Date getUhrzeit() {
        return uhrzeit;
    }

    public void setUhrzeit(Date uhrzeit) {
        this.uhrzeit = uhrzeit;
    }

    public String getTermininfo() {
        return termininfo;
    }

    public void setTermininfo(String termininfo) {
        this.termininfo = termininfo;
    }



}


