import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Services extends Remote {

    public String getDate() throws RemoteException;
    public String toUP(String s) throws RemoteException;
    //public ArrayList<Person> getList() throws RemoteException;
    public void addUser(User p) throws RemoteException;
    public void doIntensiveTask() throws RemoteException;
    public User searchUser(String userid,String  password) throws RemoteException;
}