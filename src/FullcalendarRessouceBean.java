import java.io.Serializable;

/**
 * Created by Jutom on 07.03.2017.
 */
public class FullcalendarRessouceBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;

    public FullcalendarRessouceBean(){
        super();
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String toJson() {
        StringBuilder buf = new StringBuilder("{");
        if(id!=null){
            buf.append("id:'").append(id).append("',");
        }
        if (title != null) {
            buf.append("title:'").append(title).append("'");
        }

        //addExtendedFields(buf);
        // this is the last element, we need no trailing ,

        buf.append("}");
        return buf.toString();
    }
}
