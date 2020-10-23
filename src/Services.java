import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface Services extends Remote {
    public boolean staffVerify(String staff_code) throws RemoteException;
    public Long getDate() throws RemoteException;
    public boolean addUser(User p) throws RemoteException;
    public boolean addListOrder(String user_id, ListOrder list_order) throws RemoteException;
    public User searchUser(String userid,String  password) throws RemoteException;
    public ListOrder courierListOrder() throws RemoteException ;
    public boolean removeOrder (UUID uuid) throws RemoteException;
}
