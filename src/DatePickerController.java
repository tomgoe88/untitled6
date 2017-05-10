import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jutom on 04.05.2017.
 */
@ManagedBean
@ViewScoped
public class DatePickerController {
    private Date pickerDate;
    private String javaScriptDate;

    public Date getPickerDate() {

        return pickerDate;
    }

    public void setPickerDate(Date pickerDate) {
        this.pickerDate = pickerDate;
    }

    public String getJavaScriptDate() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        if(pickerDate!= null){
            javaScriptDate = "'"+simpleDateFormat.format(pickerDate)+"'";
        }
        else{
            javaScriptDate = "'"+simpleDateFormat.format(new Date())+"'";
        }





        //  javaScriptDate= buf.toString();
        System.out.println("JavaScriptDate=   "+ javaScriptDate);
        return javaScriptDate;
    }

    public void setJavaScriptDate(String javaScriptDate) {
        this.javaScriptDate = javaScriptDate;
    }
}

