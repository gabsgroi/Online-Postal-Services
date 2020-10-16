import java.util.Comparator;

public class User extends Person implements Comparable<User> {
    ListOrder listorder;
    public User(String name, int age, String password, String address, String userid) {
        super(name, age, password, address, userid);
        this.listorder= new ListOrder();
    }



    public User(String name, int age, String password, String address, String userid, ListOrder listorder) {
        super(name, age, password, address, userid);
        this.listorder = listorder;
    }

    public ListOrder getListorder() {
        return listorder;
    }

    public void setListorder(ListOrder listorder) {
        this.listorder = listorder;
    }

    @Override
    public String toString() {
        return super.toString() + listorder;
    }

    @Override
    public int compareTo(User o) {
        if (this.getUserid().equals(o.getUserid()) && this.getPassword().equals(o.getPassword())){
            return 1;
        }
        return 0;
    }
}

