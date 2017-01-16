/**
 * Created by Jutom on 06.01.2017.
 */
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author jottyfan
 *
 */
@ManagedBean
@SessionScoped
public class FullCalendarEventList implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<FullCalendarEventBean> list;

    public FullCalendarEventList() {
        super();
        this.list = new ArrayList<FullCalendarEventBean>();
    }

    public String toJson() {
        StringBuilder buf = new StringBuilder("[");
        Iterator<FullCalendarEventBean> iterator = list.iterator();
        while (iterator.hasNext()) {
            buf.append(iterator.next().toJson());
            if (iterator.hasNext()) {
                buf.append(",");
            }
        }
        buf.append("]");
        return buf.toString();
    }

    public List<FullCalendarEventBean> getList() {
        return this.list;
    }
}
