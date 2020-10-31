import java.io.Serializable;

public class CompletedOrder implements Serializable {
    private String user_id;
    private Order order;

    public CompletedOrder(String user_id, Order order) {
        this.user_id = user_id;
        this.order = order;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return order.toString();
    }
}
