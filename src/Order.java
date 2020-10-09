public class Order {

    private String startdate;
    private String withdrawaldate;
    private Receiver receiver;
    private User sender;
    private PackList packlist;

    public Order(String startdate, Receiver receiver, User sender, PackList packlist, String withdrawaldate) {
        this.startdate = startdate;
        this.receiver = receiver;
        this.sender = sender;
        this.packlist = packlist;
        this.withdrawaldate=withdrawaldate;
    }

    public String getWithdrawaldate() {
        return withdrawaldate;
    }

    public void setWithdrawaldate(String withdrawaldate) {
        this.withdrawaldate = withdrawaldate;
    }
    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public PackList getPacklist() {
        return packlist;
    }

    public void setPacklist(PackList packlist) {
        this.packlist = packlist;
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
