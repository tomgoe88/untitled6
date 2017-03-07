import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jutom on 07.03.2017.
 */
public class FullCalendarRessourceList implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<FullcalendarRessouceBean> list;

    public FullCalendarRessourceList() {
        super();
        this.list = new ArrayList<FullcalendarRessouceBean>();
    }

    public String toJson() {
        StringBuilder buf = new StringBuilder("[");
        Iterator<FullcalendarRessouceBean> iterator = list.iterator();
        while (iterator.hasNext()) {
            buf.append(iterator.next().toJson());
            if (iterator.hasNext()) {
                buf.append(",");
            }
        }
        buf.append("]");
        System.out.println(buf.toString());
        return buf.toString();

    }

    public List<FullcalendarRessouceBean> getList() {
        return this.list;
    }
}
