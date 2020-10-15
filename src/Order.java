import java.io.Serializable;

public class Order implements Serializable {

    //private String order_id;
    private String startdate;
    private String status;
    private Receiver receiver;
    private Sender sender;
    private PackList packlist;


    public Order(String startdate,Receiver receiver, Sender sender) {

        this.startdate = startdate;
        this.status = "In elaboration";
        this.receiver = receiver;
        this.sender = sender;

    }

    public Order(String startdate, Receiver receiver, Sender sender, PackList packlist) {
        this.startdate = startdate;
        this.status = "In elaboration";
        this.receiver = receiver;
        this.sender = sender;
        this.packlist = packlist;
    }
    public Order(String startdate,String status, Receiver receiver, Sender sender, PackList packlist) {
        this.startdate = startdate;
        this.status = status;
        this.receiver = receiver;
        this.sender = sender;
        this.packlist = packlist;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
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
        return "\n"+"Startdate: " + startdate + ' ' +"\n"+"Status: "+status+ "\n"+"Sender: "+sender+"\n"+"Receiver: "+receiver+"\n"+packlist;

    }
}
