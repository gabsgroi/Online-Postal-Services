import java.io.*;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server extends UnicastRemoteObject implements Services {

    private UserList user_list; //=new UserList();
    private ListOrder util_list=new ListOrder();
    private CompletedList completed_list=new CompletedList();
    {
        try {
            user_list = readUser();
            readFile();
            readCompletedOrder();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String staff_code="sgroi20";

    protected Server() throws RemoteException {

        super(7500); //rmi remote 7500 1102 1103
    }

    private synchronized void listOrderCopy(){
        System.out.println("SERVER LOG: invoking listOrderCopy");
        for (String key : user_list.getMap().keySet()) {
            for (Order o: user_list.getMap().get(key).getListorder().getOrderlist()){
                util_list.addOrder(o);
            }
        }
        System.out.println("SERVER LOG: invoked listOrderCopy");
    }

    public synchronized ListOrder courierListOrder () throws RemoteException {
        this.listOrderCopy();
        return this.util_list;
    }
    public synchronized CompletedList completedList() throws RemoteException {
        return completed_list;
    }
    @Override
    public synchronized boolean removeOrder(UUID uuid) throws RemoteException {
        System.out.println("SERVER LOG: invoking removeOrder()");
        CompletedOrder tmporder=user_list.getOrderUser(uuid);
        completed_list.addOrder(tmporder.getUser_id(), tmporder.getOrder());

            if (user_list.removeOrder(uuid) && util_list.removeOrder(uuid)){
                System.out.println("SERVER LOG: invoked removeOrder()");

                try {
                    writeUserlist(user_list);
                    writeFile();
                    writeCompletedOrder();
                    System.out.println("SERVER LOG: Writting into Database");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        }

    @Override
    public Long getDate() throws RemoteException {
        System.out.println("SERVER LOG: invoking getDate()");
        Long date=System.currentTimeMillis();
        return date;
    }

    @Override
    public boolean staffVerify(String staff_code) throws RemoteException{
        return this.staff_code.equals(staff_code);
    }

    public synchronized boolean addUser(User p) throws RemoteException {
        System.out.println("SERVER LOG: invoking addUser");
        for (String key : user_list.getMap().keySet()) {
            if (key.equals(p.getUserid())) {
                return false;
            }
        }
            user_list.addUser(p);
        try {
            writeUserlist(user_list);
            System.out.println("SERVER LOG: invoked addUser");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public synchronized boolean addListOrder(String user_id , ListOrder list_order) throws RemoteException {
        System.out.println("SERVER LOG: invoking addListOrder");
        for (String key : user_list.getMap().keySet()) {
            if (key.equals(user_id)) {
                ListOrder tmp_server_listorder = new ListOrder();
                for (Order e: list_order.getOrderlist()){
                    tmp_server_listorder.addOrder(e);
                }
                for (Order e: tmp_server_listorder.getOrderlist()){
                    if (user_list.addOrder(e,user_id)){
                        System.out.println("SERVER LOG: invoked addListOrder");
                    }
                }
                try {
                    writeFile();
                    writeUserlist(user_list);
                    return true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return false;
    }

    @Override
    public User searchUser(String userid, String password) throws RemoteException {
        System.out.println("SERVER LOG: invoking searchUser");
        for (String key:user_list.getMap().keySet()){
            if(key.equals(userid) && user_list.getMap().get(key).getPassword().equals(password)){
                User user_copy = user_list.getMap().get(key);
                System.out.println("SERVER LOG: invoked searchUser");
                return user_copy;
            }
        }return null;
    }

    public static void main(String args[]) {

        try {
            System.setProperty("java.rmi.server.hostname","whitelodge.ns0.it");//to search the rmi registry in the Server host
            Registry registry = LocateRegistry.getRegistry();
            Services services = new Server();
            //Naming.rebind("shippingserver",services); it works in local statically
            registry.bind("shippingserver",services);

        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }

    }

    private synchronized void writeUserlist(UserList user_list) throws IOException {
        FileWriter writer= new FileWriter("users.txt");
        PrintWriter pw= new PrintWriter(writer);
        for(String key : user_list.getMap().keySet()){
            pw.print(user_list.getMap().get(key).getName());
            pw.print(";");
            pw.print(user_list.getMap().get(key).getAddress());
            pw.print(";");
            pw.print(user_list.getMap().get(key).getAge());
            pw.print(";");
            pw.print(user_list.getMap().get(key).getUserid());
            pw.print(";");
            pw.print(user_list.getMap().get(key).getPassword());
            pw.print(";");
            pw.print('\n');
            pw.flush();
        }
        writer.close();
    }

    private synchronized UserList readUser () throws IOException {

        FileReader reader = new FileReader("users.txt");
        Scanner in = new Scanner(reader);
        UserList tmp_userlist = new UserList();

        while (in.hasNext()) {
            String line = in.nextLine();
            String[] vectorline = line.split(";");
            String tmp_name = vectorline[0];
            String tmp_address = vectorline[1];
            int tmp_age = Integer.parseInt(vectorline[2]);
            String tmp_userid = vectorline[3];
            String tmp_password = vectorline[4];

            ArrayList<String> vector_orderid=new ArrayList<>();
            User u = new User (tmp_name,tmp_age,tmp_password,tmp_address,tmp_userid);
            tmp_userlist.addUser(u);
        }
        reader.close();
        return tmp_userlist;
    }

    private synchronized void readFile() throws IOException {

        FileReader reader =new FileReader("listorder.txt");
        Scanner in=new Scanner(reader);
        while(in.hasNext()){
            String line=in.nextLine();
            String [] vectorline=line.split(";");
            String tmp_userid=vectorline[0];
            String tmp_id=vectorline[1];
            Long tmp_date=Long.parseLong(vectorline[2]);
            String tmp_status=vectorline[3];
            String tmp_receivername=vectorline[4];
            String tmp_receiveraddress=vectorline[5];
            String tmp_sendername=vectorline[6];
            String tmp_senderaddress=vectorline[7];
            int index =Integer.parseInt(vectorline[8]);
            PackList tmp_packlist=new PackList();
            ArrayList <Float> tmp_pack = new ArrayList<>();
            for (int i=1;i<=index*4;i++) {
                tmp_pack.add(Float.parseFloat(vectorline[8+i]));
                if (i%4==0){
                    Pack p=new Pack(tmp_pack.get(i-4),tmp_pack.get(i-3),tmp_pack.get(i-2),tmp_pack.get(i-1));
                    tmp_packlist.addPack(p);
                }
            }
            Receiver tmp_receiver=new Receiver(tmp_receivername,tmp_receiveraddress);
            Sender tmp_sender=new Sender(tmp_sendername,tmp_senderaddress);
            Order o = new Order(UUID.fromString(tmp_id),tmp_date,tmp_status,tmp_receiver,tmp_sender,tmp_packlist);
            this.user_list.addOrder(o,tmp_userid);

        }
        reader.close();
    }

    private synchronized void writeFile() throws IOException {
        FileWriter writer= new FileWriter("listorder.txt");
        PrintWriter pw= new PrintWriter(writer);
        for (String key: user_list.getMap().keySet()){
            for(Order o:user_list.getMap().get(key).getListorder().getOrderlist()){
                pw.print(key);
                pw.print(";");
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
                pw.print('\n');
                pw.flush();
            }
        }

    }

    private synchronized void readCompletedOrder() throws IOException {

        FileReader reader =new FileReader("completedorder.txt");
        Scanner in=new Scanner(reader);
        while(in.hasNext()){
            String line=in.nextLine();
            String [] vectorline=line.split(";");
            String tmp_userid=vectorline[0];
            String tmp_id=vectorline[1];
            Long tmp_date=Long.parseLong(vectorline[2]);
            String tmp_status=vectorline[3];
            String tmp_receivername=vectorline[4];
            String tmp_receiveraddress=vectorline[5];
            String tmp_sendername=vectorline[6];
            String tmp_senderaddress=vectorline[7];
            int index =Integer.parseInt(vectorline[8]);
            PackList tmp_packlist=new PackList();
            ArrayList <Float> tmp_pack = new ArrayList<>();
            for (int i=1;i<=index*4;i++) {
                tmp_pack.add(Float.parseFloat(vectorline[8+i]));
                if (i%4==0){
                    Pack p=new Pack(tmp_pack.get(i-4),tmp_pack.get(i-3),tmp_pack.get(i-2),tmp_pack.get(i-1));
                    tmp_packlist.addPack(p);
                }
            }
            Receiver tmp_receiver=new Receiver(tmp_receivername,tmp_receiveraddress);
            Sender tmp_sender=new Sender(tmp_sendername,tmp_senderaddress);
            Order o = new Order(UUID.fromString(tmp_id),tmp_date,tmp_status,tmp_receiver,tmp_sender,tmp_packlist);
            completed_list.addOrder(tmp_userid,o);

        }
        reader.close();
    }

    private synchronized void writeCompletedOrder() throws IOException {
        FileWriter writer= new FileWriter("completedorder.txt");
        PrintWriter pw= new PrintWriter(writer);
        for (CompletedOrder co: completed_list.getOrderlist()){
                pw.print(co.getUser_id());
                pw.print(";");
                pw.print(co.getOrder().getOrder_id());
                pw.print(";");
                pw.print(co.getOrder().getStartdate());
                pw.print(";");
                pw.print(co.getOrder().getStatus());
                pw.print(";");
                pw.print(co.getOrder().getReceiver().getName());
                pw.print(";");
                pw.print(co.getOrder().getReceiver().getAddress());
                pw.print(";");
                pw.print(co.getOrder().getSender().getName());
                pw.print(";");
                pw.print(co.getOrder().getSender().getAddress());
                pw.print(";");
                pw.print(co.getOrder().getPacklist().getPacklist().size());
                pw.print(";");
                for (Pack p : co.getOrder().getPacklist().getPacklist()){
                    pw.print(p.getLenght());
                    pw.print(";");
                    pw.print(p.getWidth());
                    pw.print(";");
                    pw.print(p.getDepth());
                    pw.print(";");
                    pw.print(p.getWeight());
                    pw.print(";");
                }
                pw.print('\n');
                pw.flush();

        }

    }
}
