import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserList {

        private HashMap<String,User> usermap = new HashMap<>();

        public synchronized void addUser(User p) {
            usermap.put(p.getUserid(),p);
        }


        public synchronized HashMap<String,User> getMap(){

            HashMap<String,User> anothermap = new HashMap<>() ;
            for (String key: usermap.keySet()) {
                User x = new User(usermap.get(key).getName(),usermap.get(key).getAge(),usermap.get(key).getPassword(),usermap.get(key).getAddress(),usermap.get(key).getUserid(),usermap.get(key).getListorder());
                anothermap.put(key,x);
            }

            return anothermap;
        }




}
