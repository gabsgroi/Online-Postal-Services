import java.util.ArrayList;

public class ListOrder {
        private ArrayList<Order> orderlist = new ArrayList<>();

        public synchronized void addOrder(Order p) {
            orderlist.add(p);
        }

        public ArrayList<Order> getOrderlist() {

            ArrayList<Order> anotherlist = new ArrayList<>();
            for (Order p: orderlist) {
                Order x = new Order(p.getStartdate(),p.getWithdrawaldate(),p.getReceiver(), p.getSender(),p.getPacklist());
                anotherlist.add(x);
            }

            return anotherlist;
        }

    @Override
    public String toString() {
        return "ListOrder{" +
                "orderlist=" + orderlist +
                '}';
    }
}
