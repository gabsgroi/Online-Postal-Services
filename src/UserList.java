import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class UserList implements Serializable {


    private HashMap<String,User> usermap = new HashMap<>();

     public synchronized void addUser(User p) {
            usermap.put(p.getUserid(),p);
        }

     public synchronized boolean addOrder(Order o, String userid) {
        for (String key: usermap.keySet()) {
            if (key.equals(userid)){
                if(usermap.get(key).getListorder().addOrder(o)){
                    return true;
                }
            }
        }
        return false;
        }

    public synchronized boolean removeOrder(UUID uuid){
        for (String key: usermap.keySet()) {
            if(usermap.get(key).getListorder().removeOrder(uuid)) return true;
        }
        return false;
    }

    public synchronized HashMap<String,User> getMap(){

            HashMap<String,User> anothermap = new HashMap<>() ;
            for (String key: usermap.keySet()) {
                User x = new User(usermap.get(key).getName(),usermap.get(key).getAge(),usermap.get(key).getPassword(),usermap.get(key).getAddress(),usermap.get(key).getUserid(),usermap.get(key).getListorder());
                anothermap.put(key,x);
            }
            return anothermap;
        }

    public synchronized CompletedOrder getOrderUser (UUID uuid){
         CompletedOrder tmporder ;
            for (String key: usermap.keySet()) {
                Order tmp=usermap.get(key).getListorder().getOrder(uuid);
                if (tmp!=null){
                    tmporder=new CompletedOrder(key,tmp);
                    return tmporder;
                }
            }
            return null;
     }

    @Override
    public String toString() {
        return "UserList{" +
                "usermap=" + usermap +
                '}';
    }
}
