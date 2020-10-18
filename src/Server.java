import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

// devo modificare il menu', sistemare l'ordine con un order id e fare un metodo che elimina l'ordine.
public class Server extends UnicastRemoteObject implements Services {

    private UserList user_list= new UserList();
    private ListOrder util_list;
    {
        try {
            util_list = readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    User asd=new User("asd",33,"asd","asd","asd");


    private String staff_code="sgroi20";

    protected Server() throws RemoteException {}

    private void listOrderCopy(){
        System.out.println("SERVER LOG: invoked listOrderCopy");
        for (String key : user_list.getMap().keySet()) {
            for (Order o: user_list.getMap().get(key).getListorder().getOrderlist()){
                util_list.addOrder(o);
            }
        }
    }

    public ListOrder courierListOrder () throws RemoteException {
        this.listOrderCopy();
        return this.util_list;
    }

    @Override
    public boolean removeOrder(Order o) throws RemoteException {
        System.out.println("SERVER LOG: invoked removeOrder()");
        for (String key : user_list.getMap().keySet()) {
            util_list.removeOrder(o);
            user_list.removeOrder(o);
            return true;
            }
        return false;
        }



    @Override
    public Long getDate() throws RemoteException {
        System.out.println("SERVER LOG: invoked getDate()");
        Long date=System.currentTimeMillis();
        user_list.addUser(asd);
        return date;
    }

    @Override
    public boolean staffVerify(String staff_code) throws RemoteException{
        if (this.staff_code.equals(staff_code)) return true;
        else return false;
    }
    public boolean addUser(User p) throws RemoteException {
        System.out.println("SERVER LOG: invoking addUser");
        for (String key : user_list.getMap().keySet()) {
            if (key.equals(p.getUserid())) {
                return false;
            }
        }
            user_list.addUser(p);
            return true;
    }

    @Override
    public boolean addListOrder(String user_id , ListOrder list_order) throws RemoteException {
        System.out.println("SERVER LOG: invoking addListOrder");
        for (String key : user_list.getMap().keySet()) {
            if (key.equals(user_id)) {
                ListOrder tmp_server_listorder = new ListOrder();
                for (Order e: list_order.getOrderlist()){
                    tmp_server_listorder.addOrder(e);
                }
                for (Order e: tmp_server_listorder.getOrderlist()){
                    if (user_list.addOrder(e,user_id)){
                        System.out.println("SERVER LOG: addListOrder OK");
                        System.out.println(user_list);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public User searchUser(String userid, String password) throws RemoteException {
        System.out.println("LOG SERVER: invoking searchUser");
        for (String key:user_list.getMap().keySet()){
            if(key.equals(userid) && user_list.getMap().get(key).getPassword().equals(password)){
                User user_copy = user_list.getMap().get(key);
                return user_copy;
            }
        }return null;
    }

    public static void main(String args[]) {

        try {
            Services services = new Server();
            Naming.rebind("shippingserver",services);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    public synchronized static ListOrder readFile() throws IOException {
            FileReader reader =new FileReader("ciao.txt");
            Scanner in=new Scanner(reader);
            ListOrder tmp_order=new ListOrder();
            while(in.hasNext()){
                String line=in.nextLine();
                String [] vectorline=line.split(";");
                String tmp_id=vectorline[0];
                Long tmp_date=Long.parseLong(vectorline[1]);
                String tmp_status=vectorline[2];
                String tmp_receivername=vectorline[3];
                String tmp_receiveraddress=vectorline[4];
                String tmp_sendername=vectorline[5];
                String tmp_senderaddress=vectorline[6];
                Integer index =Integer.parseInt(vectorline[7]);
                PackList tmp_packlist=new PackList();
                ArrayList <Float> tmp_pack = new ArrayList<>();
                ArrayList<Pack> packlist=new ArrayList<>();
                for (int i=1;i<=index*4;i++) {
                    tmp_pack.add(Float.parseFloat(vectorline[7+i]));
                    if (i%4==0){
                        Pack p=new Pack(tmp_pack.get(i-4),tmp_pack.get(i-3),tmp_pack.get(i-2),tmp_pack.get(i-1));
                        tmp_packlist.addPack(p);
                    }
                }
                Receiver tmp_receiver=new Receiver(tmp_receivername,tmp_receiveraddress);
                Sender tmp_sender=new Sender(tmp_sendername,tmp_senderaddress);
                Order o = new Order(UUID.fromString(tmp_id),tmp_date,tmp_status,tmp_receiver,tmp_sender,tmp_packlist);
                tmp_order.addOrder(o);
            }
            reader.close();
            return tmp_order;
    }

    public synchronized static void writeFile(ListOrder listorder) throws IOException {
        FileWriter writer= new FileWriter("ciaone.txt"); //mettere append true
        PrintWriter pw=new PrintWriter(writer);
        for(Order o:listorder.getOrderlist()){
            pw.print(o.getOrder_id());
            pw.print(";");
            pw.print(o.getStartdate());
            pw.print(";");
            pw.print(o.getStatus());
            pw.print(";");
            pw.print(o.getReceiver().getName());
            pw.print(";");
            pw.print(o.getReceiver().getAddress());
            pw.print(";");
            pw.print(o.getSender().getName());
            pw.print(";");
            pw.print(o.getSender().getAddress());
            pw.print(";");
            pw.print(o.getPacklist().getPacklist().size());
            pw.print(";");
            for (Pack p : o.getPacklist().getPacklist()){
                pw.print(p.getLenght());
                pw.print(";");
                pw.print(p.getWidth());
                pw.print(";");
                pw.print(p.getDepth());
                pw.print(";");
                pw.print(p.getWeight());
                pw.print(";");
            }
            pw.flush();
            pw.print('\n');
        }
    }
}
