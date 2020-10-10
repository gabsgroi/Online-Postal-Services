public class User extends Person{

    public User(String name, int age, String password, String address, String userid) {
        super(name, age, password, address, userid);

    }

    private ListOrder listorder;

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
        return "User{" + super.toString() +
                "listorder=" + listorder +
                '}';
    }
}
