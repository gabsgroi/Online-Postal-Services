import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Order implements Serializable, Comparable<Order> {

    public UUID getOrder_id() {
        return order_id;
    }

    private UUID order_id;
    private Long startdate;
    private String status;
    private Receiver receiver;
    private Sender sender;
    private PackList packlist;

    public Order( Long startdate, Receiver receiver, Sender sender, PackList packlist) {
        this.order_id=UUID.randomUUID();;
        this.startdate = startdate;
        this.status = "In elaboration";
        this.receiver = receiver;
        this.sender = sender;
        this.packlist = packlist;
    }
    public Order(UUID order_id,Long startdate,String status, Receiver receiver, Sender sender, PackList packlist) {
        this.order_id=order_id;
        this.startdate = startdate;
        this.status = status;
        this.receiver = receiver;
        this.sender = sender;
        this.packlist = packlist;
    }

    public Long getStartdate() {
        return startdate;
    }

    public void setStartdate(Long startdate) {
        this.startdate = startdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }


    public PackList getPacklist() {
        return packlist;
    }

    public void setPacklist(PackList packlist) {
        this.packlist = packlist;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy HH mm");
        Date resultdate = new Date(startdate);
        return '\n'+"Order ID: "+order_id +'\n'+ "Stardate: "+resultdate  +"\n"+"Status: "+status+ "\n"+"Sender: "+sender+"\n"+"Receiver: "+receiver+"\n"+packlist;
    }

    @Override
    public int compareTo(Order o) {
        if(this.order_id.equals(o.order_id)) {
            return 1;
        }
        return 0;
    }

}
