import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface Services extends Remote {
    public boolean staffVerify(String staff_code) throws RemoteException;
    public String getDate() throws RemoteException;
    public boolean addUser(User p) throws RemoteException;
    public boolean addListOrder(String user_id, ListOrder list_order) throws RemoteException;
    public User searchUser(String userid,String  password) throws RemoteException;
    public HashMap<String, User> UserMap() throws RemoteException;
    //public boolean removerOrder(String )
}
