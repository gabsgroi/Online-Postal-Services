public class Order {

    private String startdate;
    private String withdrawaldate;
    private Receiver receiver;
    private Sender sender;
    private PackList packlist;

    public Order(String startdate, String withdrawaldate, Receiver receiver, Sender sender) {
        this.startdate = startdate;
        this.withdrawaldate = withdrawaldate;
        this.receiver = receiver;
        this.sender = sender;

    }

    public Order(String startdate, String withdrawaldate, Receiver receiver, Sender sender, PackList packlist) {        this.startdate = startdate;
        this.withdrawaldate = withdrawaldate;
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

    public String getWithdrawaldate() {
        return withdrawaldate;
    }

    public void setWithdrawaldate(String withdrawaldate) {
        this.withdrawaldate = withdrawaldate;
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
        return "Order{" +
                "startdate='" + startdate + '\'' +
                ", withdrawaldate='" + withdrawaldate + '\'' +
                ", receiver=" + receiver +
                ", sender=" + sender +
                ", packlist=" + packlist +
                '}';
    }
}
