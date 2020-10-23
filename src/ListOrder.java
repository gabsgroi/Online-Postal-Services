import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class ListOrder implements Serializable {
        private ArrayList<Order> orderlist = new ArrayList<>();


    public synchronized boolean addOrder(Order p) {
        int i = 0;
        if (orderlist.isEmpty()) {
            orderlist.add(p);
            return true;

        }
        for (Order o : orderlist) {
            if (o.compareTo(p) == 0) {
                i++;
            }
        }
        if (i == orderlist.size()) {
            orderlist.add(p);
            return true;
        }
        return false;
    }

    public synchronized boolean removeOrder(UUID uuid) {
        for (Order o: orderlist){
            if (o.getOrder_id().equals(uuid)){
                return orderlist.remove(o);
            }
        }
        return  false;
    }


        public ArrayList<Order> getOrderlist() {

            ArrayList<Order> anotherlist = new ArrayList<>();
            for (Order p: orderlist) {
                Order x = new Order(p.getOrder_id(),p.getStartdate(),p.getStatus(),p.getReceiver(), p.getSender(),p.getPacklist());
                anotherlist.add(x);
            }

            return anotherlist;
        }

    @Override
    public String toString() {
        return orderlist.toString();
    }
}
