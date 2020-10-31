import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class CompletedList implements Serializable {

    private ArrayList<CompletedOrder> orderlist = new ArrayList<>();
    CompletedOrder tmp_comp;
        public synchronized void addOrder(String user_id, Order p) {
          p.setStatus("Delivered");
          tmp_comp=new CompletedOrder(user_id,p);
          orderlist.add(tmp_comp) ;
        }
    public synchronized ArrayList<CompletedOrder> getOrderlist() {

        ArrayList<CompletedOrder> anotherlist = new ArrayList<>();
        for (CompletedOrder p: orderlist) {
            CompletedOrder x = new CompletedOrder(p.getUser_id(),p.getOrder());
            anotherlist.add(x);
        }

        return anotherlist;
    }
        @Override
        public String toString() {
            return orderlist.toString();
        }
}


