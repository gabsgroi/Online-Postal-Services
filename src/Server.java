import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Server extends UnicastRemoteObject implements Services {
    private UserList user_list= new UserList();
    private String staff_code="sgroi20";



    protected Server() throws RemoteException {
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
                }
                for (Order e: tmp_server_listorder.getOrderlist()){
                    user_list.getMap().get(key).getListorder().addOrder(e);
                }
                System.out.println("LOG SERVER: addListOrder OK");
                    return true;
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

    @Override
    public HashMap<String, User> UserMap() throws RemoteException {
        System.out.println("LOG SERVER: invoking printUserList");
        return this.user_list.getMap();
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
