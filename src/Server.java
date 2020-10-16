import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

// devo modificare il menu', sistemare l'ordine con un order id e fare un metodo che elimina l'ordine.
public class Server extends UnicastRemoteObject implements Services {
    private UserList user_list= new UserList();
    private ListOrder util_list= new ListOrder();
    private String staff_code="sgroi20";


    protected Server() throws RemoteException {}

    private void listOrderCopy(){
        for (String key : user_list.getMap().keySet()) {
            System.out.println("SCORRO LA MAPPA OK");
            for (Order o: user_list.getMap().get(key).getListorder().getOrderlist()){
                    util_list.addOrder(o);
                System.out.println("ADD completed");
            }
        }
        System.out.println("FINE listOrderCopy");

    }
    public ListOrder courierListOrder () throws RemoteException {
        System.out.println("PASSO LA LISTA AL CLIENT");
        this.listOrderCopy();
        return this.util_list;

    }

    @Override
    public boolean removeOrder(Order o) throws RemoteException {
        for (String key : user_list.getMap().keySet()) {
            util_list.removeOrder(o);
            user_list.removeOrder(o);
            System.out.println(util_list);
            System.out.println(user_list);
            return true;
            }
        return false;
        }



    @Override
    public String getDate() throws RemoteException {
        System.out.println("SERVER LOG: invoked getDate()");
        return new Date().toString();
    }

    @Override
    public boolean staffVerify(String staff_code) throws RemoteException{
        if (this.staff_code.equals(staff_code)) return true;
        else return false;
    }
    public boolean addUser(User p) throws RemoteException {
        System.out.println("LOG SERVER: invoking addUser");
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

        System.out.println("LOG SERVER: invoking addListOrder");
        for (String key : user_list.getMap().keySet()) {
            if (key.equals(user_id)) {
                ListOrder tmp_server_listorder = new ListOrder();
                for (Order e: list_order.getOrderlist()){
                    tmp_server_listorder.addOrder(e);
                    System.out.println("l'ordine e "+e);
                    System.out.println(tmp_server_listorder+" sono dentro il for");

                }
                System.out.println(tmp_server_listorder+" questa è la tmp_server_listorder");
                for (Order e: tmp_server_listorder.getOrderlist()){
                    System.out.println("ciao");
                    if (user_list.addOrder(e,user_id)){

                        System.out.println("LOG SERVER: addListOrder OK");
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
}
