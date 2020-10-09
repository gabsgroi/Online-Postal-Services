import java.util.ArrayList;

public class ListOrder {
        private ArrayList<Order> orderlist = new ArrayList<>();

        public synchronized void addOrder(Order p) {
            orderlist.add(p);
        }

        public ArrayList<Order> getOrderlist() {

            ArrayList<Order> anotherlist = new ArrayList<>();
            for (Order p: orderlist) {
                Order x = new Order(p.getStartdate(),p.getReceiver(),p.getSender(),p.getPacklist(),p.getWithdrawaldate());
                anotherlist.add(x);
            }

            return anotherlist;
        }

}
