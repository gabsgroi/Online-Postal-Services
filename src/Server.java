import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
// i metodi di Services sono già sincronizzati?
// devo modificare il menu',.
// con l'eliminazione con numero , potrebbe esserci il problema di un'altro corriere che elimina prima di me quell'ordine ma io vedo la lista non
// aggiornata e quindi elimino un altro ordine, potrei aggiungere un ulteriore controllo. Ho implementato il controllo ma non so se funzionerà
public class Server extends UnicastRemoteObject implements Services {

    private UserList user_list; //=new UserList();

    {
        try {
            user_list = readUser();
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ListOrder util_list=new ListOrder();

    private String staff_code="sgroi20";

    protected Server() throws RemoteException {}

    private void listOrderCopy(){
        System.out.println("SERVER LOG: invoking listOrderCopy");
        for (String key : user_list.getMap().keySet()) {
            for (Order o: user_list.getMap().get(key).getListorder().getOrderlist()){
                util_list.addOrder(o);
            }
        }
        System.out.println("SERVER LOG: invoked listOrderCopy");
    }

    public ListOrder courierListOrder () throws RemoteException {
        this.listOrderCopy();
        return this.util_list;
    }

    @Override
    public boolean removeOrder(UUID uuid) throws RemoteException {
        System.out.println("SERVER LOG: invoking removeOrder()");

            if (user_list.removeOrder(uuid) && util_list.removeOrder(uuid)){
                System.out.println("SERVER LOG: invoked removeOrder()");

                try {
                    writeUserlist(user_list);
                    writeFile();
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

    public boolean addUser(User p) throws RemoteException {
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
            Services services = new Server();
            Naming.rebind("shippingserver",services);

        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public synchronized void writeUserlist(UserList user_list) throws IOException {
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
    public synchronized UserList readUser () throws IOException {

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
    public synchronized void readFile() throws IOException {

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
    public synchronized void writeFile() throws IOException {
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

}
