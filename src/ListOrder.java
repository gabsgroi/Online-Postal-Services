import java.io.Serializable;
import java.util.ArrayList;

public class ListOrder implements Serializable {
        private ArrayList<Order> orderlist = new ArrayList<>();


    public synchronized boolean addOrder(Order p) {
        int i=0;
        if (orderlist.isEmpty()){
            orderlist.add(p);
            return true;
        }
        for (Order o: orderlist){
            if (o.compareTo(p)==0){
                i++;
            }
        }
        if (i==orderlist.size()){
            orderlist.add(p);
            return true;
        }
        return false;
    }
    public synchronized boolean removeOrder(Order p) {
        if (orderlist.isEmpty()){
            System.out.println("There are not orders");
            return false;
        }
        for (Order o: orderlist){
            if (o.compareTo(p)==1){
                if(orderlist.remove(o)){
                    System.out.println("rimosso");
                }
                System.out.println("Order "+p.getOrder_id()+" succeffully removed");
                return true;
            }
        }
        System.out.println("The order that you want to consume does not exist");
        return false;

    }
    public void removeAllOrder() {
        for(Order o: orderlist){
            orderlist.remove(o);
        }
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
