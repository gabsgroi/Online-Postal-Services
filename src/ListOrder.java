import java.io.Serializable;
import java.util.ArrayList;

public class ListOrder implements Serializable {
        private ArrayList<Order> orderlist = new ArrayList<>();


    public synchronized void addOrder(Order p) {
        orderlist.add(p);
    }
    public void removeOrder(Order p) {

        orderlist.remove(p);
    }
    public void removeAllOrder() {
        for(Order o: orderlist){
            orderlist.remove(o);
        }
    }


        public ArrayList<Order> getOrderlist() {

            ArrayList<Order> anotherlist = new ArrayList<>();
            for (Order p: orderlist) {
                Order x = new Order(p.getStartdate(),p.getStatus(),p.getReceiver(), p.getSender(),p.getPacklist());
                anotherlist.add(x);
            }

            return anotherlist;
        }

    @Override
    public String toString() {
        return "Order list: "+orderlist;
    }
}
